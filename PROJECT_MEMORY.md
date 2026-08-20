# 🧠 Memória do Projeto e Diário de Bordo (Session Context)

> **Este arquivo serve como contexto persistente de memória para o assistente AI (Mentor) e o desenvolvedor para manter o alinhamento total entre sessões.**

---

## 👤 Perfil do Desenvolvedor e Regras de Ouro de Mentoria

- **Objetivo**: Construir a biblioteca `DataFrame` em Java 21 e aprender Java do zero ao avançado.
- **Papel da AI**: Professor / Mentor de Java.
- **REGRA SUPREMA DE MENTORIA**:
  - 🛑 **NUNCA escrever ou modificar arquivos de código do projeto em nome do usuário.**
  - 💡 **SEMPRE explicar o *porquê* (teoria, design, conceitos de Java)**.
  - 📐 **Exibir snippets estruturados/exemplos conceituais na conversa para o usuário aprender e codificar ele próprio.**
  - 🤝 **Fornecer feedback e guiar na depuração.**

---

## 📊 Estado Atual do Código

### Pacote `com.dataframe.type`
- **`DataType.java`**: Enum com `INTEGER`, `DOUBLE`, `BOOLEAN`, `DATE`, `DATETIME`, `STRING`.

### Pacote `com.dataframe.column`
- **`Column<T>.java`**: Interface genérica.
- **`AbstractColumn<T>.java`**: Classe abstrata genérica com suporte a imutabilidade em `values()`.
- **Colunas Concretas** (pacote completo, todos os tipos planejados implementados):
  - `StringColumn.java`
  - `IntColumn.java`
  - `DoubleColumn.java`
  - `BooleanColumn.java`
  - `DateColumn.java` (`LocalDate`, construtor padrão com `DateTimeFormatter.ISO_LOCAL_DATE` + construtor sobrecarregado com `DateTimeFormatter` customizado via encadeamento `this(...)`)
  - `DateTimeColumn.java` (`LocalDateTime`, mesmo padrão do `DateColumn`: construtor padrão com `DateTimeFormatter.ISO_LOCAL_DATE_TIME` + construtor sobrecarregado via `this(...)`)
- **Testes Unitários**:
  - `StringColumnTest.java` (4 testes)
  - `IntColumnTest.java` (3 testes)
  - `BooleanColumnTest.java` (3 testes)
  - `DoubleColumnTest.java` (4 testes)
  - `DateColumnTest.java` (6 testes: inicialização, parsing válido com trim, null/vazio, `DateTimeParseException` em formato inválido, imutabilidade de `values()`, formatter customizado `dd/MM/yyyy`)
  - `DateTimeColumnTest.java` (6 testes: mesma cobertura do `DateColumnTest`, incluindo caso de data sem componente de hora — ex. `"2025-01-01"` — lançando `DateTimeParseException` com o formatter ISO padrão, já que `ISO_LOCAL_DATE_TIME` exige o separador `T` e a hora)
  - 🟢 **26 testes executados e aprovados com sucesso!** (`BUILD SUCCESS`).

---

---

## 📥 Arquitetura e Origem dos Dados (CSV Ingestion)

- **Entrada Principal de Dados**: Leitura de arquivos **CSV** (`CsvReader` / `DataFrameReader`).
- **Papel Vital do `addFromString(String value)`**:
  - Como dados de CSV chegam como `String`, cada tipo de coluna é responsável por converter a string bruta no seu objeto Java correspondente (`Integer`, `Double`, `Boolean`, `LocalDate`, `LocalDateTime`).
  - Suporte a múltiplos formatos de datas (`DateTimeFormatter`) para garantir leitura sem falhas em CSVs de diferentes localidades (`dd/MM/yyyy`, ISO, etc).

---

## 🎯 Próximos Passos (Próxima Sessão / Próxima Lição)

1. **`DataFrameTest.java`** (próximo passo imediato): cobrir `shape()` vazio, `addColumn` caminho feliz, exceção de nome duplicado, exceção de linhas incompatíveis, `getColumn` com nome inexistente.
2. **Exibição formatada no terminal** (`toString()` ou `show()`): atenção — a chave do `LinkedHashMap` é a fonte de verdade do nome da coluna, não necessariamente `column.getName()` (relevante se o tratamento de nome duplicado evoluir pra auto-rename no futuro).
3. **Leitor de CSV (`CsvReader` / `DataFrameReader`)**:
   - Leitura de arquivos `.csv` e instanciação automática do `DataFrame`.

---

## 📝 Notas da Sessão de 2026-08-09

- `DateColumn` já existia implementado, mas sem testes — sessão focou em escrever `DateColumnTest.java` do zero, com o desenvolvedor digitando o código e a IA revisando/apontando gaps (sem escrever o arquivo).
- Conceito estudado em profundidade: encadeamento de construtores com `this(...)` vs `super(...)` (por que `DateColumn(String)` delega para `DateColumn(String, DateTimeFormatter)` em vez de duplicar a chamada a `super(...)`).
- Padrão de revisão que funcionou bem: a IA lista possibilidades de teste (checklist), o desenvolvedor escreve, a IA revisa o arquivo e aponta gaps/nitpicks (nomes de coluna sem sentido semântico, typos em `@DisplayName`, testes fazendo coisa demais de uma vez, inconsistência de estilo de lambda) sem tocar no arquivo.
- Maven não está no PATH do sistema — usar o Maven embutido do IntelliJ para rodar `mvn test` via terminal:
  `/home/holanda777/.local/share/JetBrains/Toolbox/apps/intellij-idea/plugins/maven-plugin/lib/maven3/bin/mvn`
- Estado final da sessão: `DateColumnTest.java` completo e revisado, 20/20 testes passando (`BUILD SUCCESS`).

## 📝 Notas da Sessão de 2026-08-10

- Desenvolvedor implementou `DateTimeColumn.java` por conta própria (primeira vez implementando uma coluna nova sozinho, não só testes). Bug real encontrado na revisão: `addFromString` checava `value.trim().isEmpty()` mas chamava `LocalDateTime.parse(value, formatter)` sem trim no valor — corrigido para `value.trim()`, consistente com `DateColumn`.
- Conceito estudado: por que `DateTimeFormatter.ISO_LOCAL_DATE_TIME` exige o separador literal `'T'` entre data e hora (ISO-8601), e como isso torna "data sem componente de hora" (ex. `"2025-01-01"`) um caso de `DateTimeParseException` interessante e específico de `DateTimeColumn` (não existe em `DateColumn`).
- `DateTimeColumnTest.java` escrito pelo desenvolvedor seguindo checklist de 6 casos (mesmo padrão de `DateColumnTest`). Gap encontrado na revisão: `@DisplayName` do teste de formatter customizado ainda dizia `"dd/MM/yyyy"` mas o formatter testado era `"dd/MM/yyyy HH:mm:ss"` (resquício de copy-paste) — corrigido pelo desenvolvedor.
- Estado final da sessão: pacote `com.dataframe.column` completo (todos os 6 tipos de coluna planejados implementados e testados), 26/26 testes passando (`BUILD SUCCESS`).
- Próxima sessão: começar a classe `DataFrame` (`LinkedHashMap<String, Column<?>>`, shape, adição de coluna, exibição formatada).

## 📝 Notas da Sessão de 2026-08-11

- Desenvolvedor implementou a primeira versão de `DataFrame.java` (`src/main/java/com/dataframe/DataFrame.java`), com a IA guiando por conceitos e mostrando snippets conceituais em conversa (sem escrever no arquivo). Campo único: `LinkedHashMap<String, Column<?>> columns`.
- Métodos implementados: construtor sem args; `addColumn(Column<?>)` (valida nome duplicado via `containsKey` e tamanho de linhas incompatível, ambos lançando `IllegalArgumentException`); `shape()` retornando `int[]{linhas, colunas}`; `getColumn(String)` lançando `NoSuchElementException` se não encontrado.
- Decisões de design discutidas e fechadas: `int[]` escolhido para `shape()` em vez de um record `Shape` ou getters separados; nome de coluna duplicado é **rejeitado com exceção**, não auto-renomeado com sufixo `_1`/`_2` (ideia levantada e conscientemente adiada — ver nota abaixo).
- Gaps encontrados e corrigidos em duas rodadas de revisão: (1) mensagem de exceção sendo montada com `String.format` mesmo no caminho feliz de `addColumn`, sem necessidade — movida para dentro do `if` que lança a exceção, usando `.formatted()`; (2) espaço faltando em `"Coluna não encontrada:" + name`.
- Conceitos estudados em profundidade: `LinkedHashMap` (tabela hash + lista duplamente encadeada mantendo ordem de inserção, por que isso importa pra exibição em tabela ordenada); por que `columns.values().iterator().next()` é necessário (`Map.values()` retorna `Collection`, não `List` — sem `get(int)`); `String.format`/`.formatted()` como alternativa ao `+` de concatenação (e por que Java não tem f-strings estáveis ainda — String Templates é preview feature, não usar); a palavra-chave `final` em campo/variável/método/classe, com ênfase na armadilha de que `final` trava a *referência*, não o conteúdo de um objeto mutável (paralelo direto entre `columns` no `DataFrame` e `values` em `AbstractColumn`).
- Nota de design pra próxima sessão: como nome duplicado é rejeitado (não renomeado), a chave do `LinkedHashMap` sempre é igual a `column.getName()` por enquanto — mas se o auto-rename for revisitado no futuro, a exibição em tabela deve iterar `columns.entrySet()` e usar a chave, não `column.getName()`, pra não ficar inconsistente.
- `mvn compile` limpo (`BUILD SUCCESS`), sem testes ainda para `DataFrame`.
- Próxima sessão: escrever `DataFrameTest.java` (checklist: `shape()` vazio, `addColumn` feliz, nome duplicado, linhas incompatíveis, `getColumn` ausente) antes de avançar para exibição em tabela.

## 📝 Notas da Sessão de 2026-08-13

- `DataFrameTest.java` escrito do zero pelo desenvolvedor, cobrindo os 5 casos do checklist definido na sessão anterior: `shape()` vazio (`testShape`), `addColumn` com colunas de tipos diferentes (`testAddColumnValido`), nome duplicado entre tipos diferentes de coluna (`testColunasDuplicadas`), linhas incompatíveis com verificação extra de invariante pós-exceção (`testLinhasDiferente`), e `getColumn` com nome inexistente (dedicado, após inicialmente só coberto como efeito colateral em outro teste).
- Ambiente: `mvn` não está no PATH deste sistema — usar o binário do IntelliJ (`~/.local/share/JetBrains/Toolbox/apps/intellij-idea/plugins/maven-plugin/lib/maven3/bin/mvn`) confirmado funcional para `mvn test -Dtest=DataFrameTest` e `mvn test`.
- Conceito estudado em profundidade: `assertEquals` vs `assertSame` quando `Column`/`AbstractColumn` não sobrescrevem `equals()` — `assertEquals` "funciona" só por cair no `Object.equals()` padrão (identidade), mas não comunica a intenção nem seria seguro se um `equals()` por valor fosse adicionado depois. Desenvolvedor optou por `assertSame` conscientemente, por ser isso que queria testar (mesma instância retornada por `getColumn`).
- Padrão de gaps recorrente nessa sessão de revisão: `@DisplayName` deixado como placeholder (`"..."`, `"...."`) na primeira escrita de um teste novo, corrigido a cada rodada — vale reforçar preencher já na primeira versão. Também apareceu nomenclatura enganosa por reuso de variável entre testes (`st` de `StringColumn` reaproveitado como nome de uma `DoubleColumn` num teste seguinte) e nome de método não correspondente ao cenário (`testGetName` testando na verdade `getColumn` ausente, renomeado após revisão).
- Linha em branco dupla entre imports e a declaração da classe apareceu em duas rodadas seguidas e depois sumiu sozinha (possivelmente resolvida ao reorganizar imports no IntelliJ) — sem necessidade de investigar mais.
- Estado final: suite completa rodando `mvn test` = **31/31 testes passando** (`BUILD SUCCESS`), cobrindo os 6 tipos de coluna + `DataFrame`.
- Próxima sessão: exibição formatada em terminal para `DataFrame` (`toString()` ou `show()`) — lembrar que a chave do `LinkedHashMap` é a fonte de verdade do nome da coluna, não necessariamente `column.getName()` (relevante caso o auto-rename de nomes duplicados seja revisitado).
