package com.dataframe;

import com.dataframe.column.Column;

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;
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

    @Override
    public String toString() {
        return render(shape()[0]);
    }

    public String show() {
        return show(20);
    }

    public String show(int limit) {
        if (limit <= 0) {
            throw new IllegalArgumentException("limit deve ser maior que zero, recebido: %d".formatted(limit));
        }
        return render(limit);
    }

    private String render(int maxRows) {
        int totalRows = shape()[0];
        int totalCols = shape()[1];

        if (totalCols == 0) {
            return "DataFrame vazio (sem colunas)";
        }

        int rowsToShow = Math.min(maxRows, totalRows);

        // 1 - Calcular a largura de todas as colunas sobre todas as linhas
        Map<String, Integer> widths = new LinkedHashMap<>();
        for (Map.Entry<String, Column<?>> entry : columns.entrySet()) {
            String name = entry.getKey();
            Column<?> col = entry.getValue();

            int maxWidth = name.length();
            for (int i = 0; i < col.size(); i++) {
                String cell = String.valueOf(col.get(i));
                maxWidth = Math.max(maxWidth, cell.length());
            }
            widths.put(name, maxWidth);
        }

        // 2 - Montar Cabecalho
        StringBuilder sb = new StringBuilder();
        List<String> headerCells = new ArrayList<>();
        for (Map.Entry<String, Integer> w : widths.entrySet()) {
            headerCells.add(String.format("%-" + w.getValue() + "s", w.getKey()));
        }
        String headerLine = String.join(" | ", headerCells);
        sb.append(headerLine).append("\n");
        sb.append("-".repeat(headerLine.length())).append("\n");

        // 3 - Montar as linhas de dados limitado a (rowsToShow)
        for (int i = 0; i < rowsToShow; i++) {
            List<String> rowCells = new ArrayList<>();
            for (Map.Entry<String, Column<?>> entry : columns.entrySet()) {
                String colName = entry.getKey();
                Column<?> col = entry.getValue();
                int width = widths.get(colName);
                String cell = String.valueOf(col.get(i));
                rowCells.add(String.format("%-" + width + "s", cell));
            }
            sb.append(String.join(" | ", rowCells)).append("\n");
        }

        // 4 - Rodape
        if (rowsToShow < totalRows) {
            sb.append("... (mostrando %d de %d linhas)".formatted(rowsToShow, totalRows));
        }

        return sb.toString().stripTrailing();
    }
}