package com.dataframe.column;

import com.dataframe.type.DataType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DoubleColumnTest {

    @Test
    @DisplayName("Deve inicializar a coluna com nome e tipo corretos")
    void testInicializacao() {
        DoubleColumn col = new DoubleColumn("salario");
        assertEquals("salario", col.getName());
        assertEquals(DataType.DOUBLE, col.getDataType());
        assertEquals(Double.class, col.getType());
        assertEquals(0, col.size());
    }

    @Test
    @DisplayName("Deve converter String numérica em Double via addFromString")
    void testAddFromStringValido() {
        DoubleColumn col = new DoubleColumn("salario");
        col.addFromString("1500.50");
        col.addFromString(" 2300.75 ");
        col.addFromString(null);
        col.addFromString("  ");

        assertEquals(4, col.size());
        assertEquals(1500.50, col.get(0));
        assertEquals(2300.75, col.get(1));
        assertNull(col.get(2));
        assertNull(col.get(3));
    }

    @Test
    @DisplayName("Deve lançar NumberFormatException ao passar String não numérica para Double")
    void testAddFromStringInvalido() {
        DoubleColumn col = new DoubleColumn("salario");

        assertThrows(NumberFormatException.class, () -> col.addFromString("mil e quinhentos"));
    }

    @Test
    @DisplayName("Deve garantir que a lista retornada por values() seja imutável")
    void testValuesImutavel() {
        DoubleColumn col = new DoubleColumn("salario");
        col.add(1500.50);

        assertThrows(UnsupportedOperationException.class, () -> col.values().add(2000.00));
    }
}
