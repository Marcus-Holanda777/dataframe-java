# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## ⚠️ Mentorship rule — read before touching any code

This is a **learning project**. The developer is teaching themselves Java from the ground up by building this library, using Claude as a mentor, not an implementer.

- **Do not write or modify project source/test code on the developer's behalf** (i.e., never use Edit/Write on files under `src/`), even if asked to "just implement X," unless they explicitly and unambiguously override this for a specific task.
- Instead: explain the underlying concept (OOP, generics, design pattern, Java stdlib behavior), then show a **complete, runnable code snippet in the conversation** (not just a conceptual skeleton) for the developer to read, adapt, and type/paste into the file themselves.
- Guide debugging by asking questions and pointing at the likely cause, rather than pasting the fix.
- The pattern that works well in this repo: list a checklist of cases/possibilities, let the developer write the code, then review the file afterward and point out gaps/nitpicks (semantically meaningless names, `@DisplayName` typos, tests doing too much at once, style inconsistencies) without editing the file yourself.
- Non-code assistance (running `mvn test`, reading files, explaining stack traces, updating this memory file or `PROJECT_MEMORY.md`) is fine.

## Commands

```bash
mvn compile          # compile the project
mvn test             # run all unit tests (JUnit 5 / Jupiter)
mvn test -Dtest=IntColumnTest          # run a single test class
mvn test -Dtest=IntColumnTest#testAddFromStringValido   # run a single test method
```

Java 21, no other runtime dependencies beyond JUnit 5 (test scope only). There is no `mvnw` wrapper — `mvn` must be resolved from the environment. If `mvn` is not on `PATH`, IntelliJ's bundled Maven binary is a fallback:
`~/.local/share/JetBrains/Toolbox/apps/intellij-idea/plugins/maven-plugin/lib/maven3/bin/mvn`

## Architecture

The library centers on a typed column abstraction, following this hierarchy:

```
Column<T> (interface)  →  AbstractColumn<T> (abstract base)  →  StringColumn / IntColumn / DoubleColumn / BooleanColumn / DateColumn / DateTimeColumn
```

- **`com.dataframe.type.DataType`** — enum of supported logical types (`INTEGER`, `DOUBLE`, `BOOLEAN`, `DATE`, `DATETIME`, `STRING`), used for runtime type tagging independent of the Java generic type.
- **`com.dataframe.column.Column<T>`** — public contract: `getName()`, `size()`, `get(int)`, `add(T)`, `values()`, `getType()` (the `Class<T>`), `getDataType()` (the `DataType` enum), and `addFromString(String)`.
- **`com.dataframe.column.AbstractColumn<T>`** — implements the shared bookkeeping (name, `DataType`, `Class<T>`, backing `ArrayList<T>`). `values()` returns `Collections.unmodifiableList(...)` — column data is never externally mutable through that accessor. Leaves `addFromString` abstract.
- **Concrete columns** (`StringColumn`, `IntColumn`, `DoubleColumn`, `BooleanColumn`, `DateColumn`, `DateTimeColumn`) each implement `addFromString` with their own parsing logic — this is the seam where raw CSV text becomes a typed Java value (e.g. `IntColumn` uses `Integer.parseInt`). All of them treat blank/null input as a null entry. `DateColumn` (`LocalDate`) and `DateTimeColumn` (`LocalDateTime`) both follow the same constructor-chaining pattern: a no-arg-formatter constructor delegates via `this(...)` to an overloaded constructor accepting a custom `DateTimeFormatter`, defaulting to `DateTimeFormatter.ISO_LOCAL_DATE` / `ISO_LOCAL_DATE_TIME` respectively. New column types follow this same overall pattern.
- **`com.dataframe.DataFrame`** — wraps a `LinkedHashMap<String, Column<?>>` (insertion order matters for later tabular display). `addColumn(Column<?>)` rejects a duplicate column name and a row-count mismatch against existing columns, both via `IllegalArgumentException`. `shape()` returns `int[]{rows, columns}` (not a record/getters — a closed design decision). `getColumn(String)` throws `NoSuchElementException` when the name isn't found. Duplicate column names are currently rejected outright, not auto-renamed (e.g. with a `_1`/`_2` suffix) — that idea was considered and deliberately deferred. Because of that, the map key always equals `column.getName()` for now; if auto-rename is revisited, any future table-display code must iterate `columns.entrySet()` and use the map key, not `column.getName()`, to stay consistent.

Since a `DataFrame` holds heterogeneous `Column<?>` instances together, `getDataType()` and `getType()` exist specifically to allow runtime type dispatch where Java generics alone can't (type erasure).

Tests mirror the main package layout (`src/test/java/com/dataframe/column/...ColumnTest.java`), using JUnit 5 with `@DisplayName` annotations in Portuguese describing each case, following the pattern: happy path parse, invalid-input exception, immutability of `values()`. `DateTimeColumnTest` additionally covers a case with no `DataFrameTest` equivalent yet: a date string lacking a time component (e.g. `"2025-01-01"`) throws `DateTimeParseException` under the default ISO formatter, since `ISO_LOCAL_DATE_TIME` requires the literal `T` separator and a time — this failure mode doesn't exist for `DateColumn`.

## Roadmap context

Current state: the full planned column hierarchy (String/Int/Double/Boolean/Date/DateTime) is implemented and tested (26 tests passing). `DataFrame` has a first version (`addColumn`, `shape`, `getColumn`) but **no test file yet** — `DataFrameTest.java` is the next thing to write, covering: empty `shape()`, happy-path `addColumn`, duplicate-name exception, incompatible-row-count exception, `getColumn` with a missing name. Not yet built: formatted table display (`toString()`/`show()`) and CSV ingestion (`CsvReader`/`DataFrameReader`) with automatic type inference. See `PROJECT_MEMORY.md` for the fuller session-by-session log.
