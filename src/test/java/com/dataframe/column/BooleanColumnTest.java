package com.dataframe.column;

import com.dataframe.type.DataType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class BooleanColumnTest {

    @Test
    @DisplayName("Deve inicializar metadados corretamente")
    void testInicializacao() {
        BooleanColumn col = new BooleanColumn("ativo");
        assertEquals("ativo", col.getName());
        assertEquals(DataType.BOOLEAN, col.getDataType());
        assertEquals(Boolean.class, col.getType());
    }

    @Test
    @DisplayName("Deve Converter String para Boolean via addFromString")
    void testAddFromString() {
        BooleanColumn col = new BooleanColumn("ativo");

        col.addFromString("true");
        col.addFromString(" TRUE ");
        col.addFromString("false");
        col.addFromString("textoQualquer"); // deve ser convertido para False
        col.addFromString(null);
        col.addFromString("   ");

        assertEquals(6, col.size());
        assertTrue(col.get(0));
        assertTrue(col.get(1));
        assertFalse(col.get(2));
        assertFalse(col.get(3));
        assertNull(col.get(4));
        assertNull(col.get(5));
    }

    @Test
    @DisplayName("Deve garantir que a lista retornada por values seja imutavel")
    void testValuesImutavel() {
        BooleanColumn col = new BooleanColumn("ativo");
        col.add(true);

        assertThrows(UnsupportedOperationException.class, () -> {
            col.values().add(false);
        });
    }
}

