package com.dataframe.column;

import com.dataframe.type.DataType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class StringColumnTest {
    @Test
    @DisplayName("Deve inicializar a coluna com o nome e tipo corretos")
    void testInicializacao() {
        StringColumn col = new StringColumn("nome");
        assertEquals("nome", col.getName());
        assertEquals(DataType.STRING, col.getDataType());
        assertEquals(String.class, col.getType());
        assertEquals(0, col.size());
    }

    @Test
    @DisplayName("Deve adicionar elementos e retornar o tamanho correto")
    void testAdicionarElementos() {
        StringColumn col = new StringColumn("cidade");
        col.add("Ceara");
        col.add("Sao Paulo");

        assertEquals(2, col.size());
        assertEquals("Ceara", col.get(0));
        assertEquals("Sao Paulo", col.get(1));
    }

    @Test
    @DisplayName("Deve converter texto via addFromString e tratar nulos")
    void testAddFromString() {
        StringColumn col = new StringColumn("fruta");
        col.addFromString("Maca");
        col.addFromString("");
        col.addFromString(null);

        assertEquals(3, col.size());
        assertEquals("Maca", col.get(0));
        assertNull(col.get(1));
        assertNull(col.get(2));
    }

    @Test
    @DisplayName("Deve garantir que a lista retornada por values() seja imutável")
    void testValuesImutavel() {
        StringColumn col = new StringColumn("cidade");
        col.add("Fortaleza");

        assertThrows(UnsupportedOperationException.class, () -> col.values().add("Salvador"));
    }
}
