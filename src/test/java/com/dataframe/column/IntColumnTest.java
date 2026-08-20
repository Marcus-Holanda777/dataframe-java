package com.dataframe.column;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class IntColumnTest {
    @Test
    @DisplayName("Deve conventer String Numerica em Integer via addFrom String")
    void testAddFromStringValido() {
        IntColumn col = new IntColumn("idade");
        col.addFromString("25");
        col.addFromString(" 30 ");

        assertEquals(2, col.size());
        assertEquals(25, col.get(0));
        assertEquals(30, col.get(1));
    }

    @Test
    @DisplayName("Deve lançar NumberFormatException ao passar String nao numerica")
    void testAddFromStringInvalido() {
        IntColumn col = new IntColumn("idade");

        assertThrows(NumberFormatException.class, () -> col.addFromString("vinte e cinco"));
    }

    @Test
    @DisplayName("Deve garantir que a lista retornada por values() seja imutável")
    void testValuesImutavel() {
        IntColumn col = new IntColumn("idade");
        col.add(25);

        assertThrows(UnsupportedOperationException.class, () -> col.values().add(30));
    }
}
