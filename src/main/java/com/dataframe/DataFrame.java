package com.dataframe;

import com.dataframe.column.Column;

import java.util.LinkedHashMap;
import java.util.NoSuchElementException;

public class DataFrame {

    private final LinkedHashMap<String, Column<?>> columns;

    public DataFrame() {
        this.columns = new LinkedHashMap<>();
    }

    public void addColumn(Column<?> column) {
        if (columns.containsKey(column.getName())) {
            throw new IllegalArgumentException("Já existe uma coluna chamada '" + column.getName() + "'");
        }

        if (!columns.isEmpty()) {
            int expectedRows = shape()[0];
            if (column.size() != expectedRows) {
                throw new IllegalArgumentException(
                        "Coluna '%s' tem %d linhas, mas o DataFrame já tem %d"
                                .formatted(column.getName(), column.size(), expectedRows)
                );
            }
        }
        columns.put(column.getName(), column);
    }

    public int[] shape() {
        int rows = columns.isEmpty() ? 0 : columns.values().iterator().next().size();
        return new int[]{rows, columns.size()};
    }

    public Column<?> getColumn(String name) {
        Column<?> column = columns.get(name);
        if (column == null) {
            throw new NoSuchElementException("Coluna não encontrada: " + name);
        }
        return column;
    }

}
