package com.dataframe;

import com.dataframe.column.DoubleColumn;
import com.dataframe.column.IntColumn;
import com.dataframe.column.StringColumn;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

public class DataFrameTest {
    @Test
    @DisplayName("Deve retornar {0, 0} quando o DataFrame estiver vazio")
    void testShape() {
        DataFrame df = new DataFrame();
        assertArrayEquals(new int[]{0, 0}, df.shape());
    }

    @Test
    @DisplayName("Deve adicionar colunas de tipos diferentes e refletir shape corretamente")
    void testAddColumnValido() {
        DataFrame df = new DataFrame();
        IntColumn col = new IntColumn("idade");
        col.addFromString("30");
        col.addFromString("25");
        col.addFromString("17");

        StringColumn st = new StringColumn("nome");
        st.addFromString("Marcus");
        st.addFromString("Vinicius");
        st.addFromString("Holanda");

        df.addColumn(col);
        df.addColumn(st);

        assertArrayEquals(new int[]{3, 2}, df.shape());
        assertSame(col, df.getColumn("idade"));
        assertSame(st, df.getColumn("nome"));
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException ao adicionar coluna com nome já existente")
    void testColunasDuplicadas() {
        DataFrame df = new DataFrame();

        IntColumn col = new IntColumn("idade");
        DoubleColumn colDuplicate = new DoubleColumn("idade");

        df.addColumn(col);
        assertThrows(IllegalArgumentException.class, () -> df.addColumn(colDuplicate));
    }

    @Test
    @DisplayName("Deve lançar IllegalArgumentException caso as linhas inseridas sejam diferentes")
    void testLinhasDiferente() {
        DataFrame df = new DataFrame();

        IntColumn col = new IntColumn("idade");
        col.addFromString("25");
        col.addFromString("2");
        col.addFromString("25");

        StringColumn st = new StringColumn("nome");
        st.addFromString("Marcus");
        st.addFromString("Logan");

        df.addColumn(col);
        assertThrows(IllegalArgumentException.class, () -> df.addColumn(st));
        assertEquals(1, df.shape()[1]);
        assertThrows(NoSuchElementException.class, () -> df.getColumn("nome"));
    }

    @Test
    @DisplayName("Deve lançar NoSuchElementException ao tentar buscar uma coluna que nao existe")
    void testGetColumn() {
        DataFrame df = new DataFrame();
        IntColumn col = new IntColumn("idade");
        col.add(25);
        col.add(30);

        df.addColumn(col);
        assertThrows(NoSuchElementException.class, () -> df.getColumn("ColunaNaoExiste"));
    }
}
