package com.dataframe.column;

import com.dataframe.type.DataType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class DateColumnTest {
    @Test
    @DisplayName("Deve inicializar a coluna com nome e tipo corretos")
    void testInicializacao() {
        DateColumn col = new DateColumn("nascimento");

        assertEquals("nascimento", col.getName());
        assertEquals(DataType.DATE, col.getDataType());
        assertEquals(LocalDate.class, col.getType());
        assertEquals(0, col.size());
    }

    @Test
    @DisplayName("Deve converter String em LocalDate via addFromString valido")
    void testAddFromStringValido() {
        DateColumn col = new DateColumn("nascimento");

        LocalDate inpRef = LocalDate.of(2025, 1, 1);
        LocalDate inpRefTwo = LocalDate.of(2026, 2, 5);
        LocalDate inpRefThre = LocalDate.of(2023, 8, 9);

        col.add(inpRef);
        col.addFromString("2026-02-05");
        col.addFromString(" 2023-08-09  ");

        LocalDate ref = col.get(0);
        LocalDate refTwo = col.get(1);
        LocalDate refThre = col.get(2);

        assertEquals(inpRef, ref);
        assertEquals(inpRefTwo, refTwo);
        assertEquals(inpRefThre, refThre);
        assertEquals(3, col.size());
    }

    @Test
    @DisplayName("Deve tratar null e String vazia como entrada nula")
    void testAddFromStringNuloOuVazio() {
        DateColumn col = new DateColumn("nascimento");

        col.addFromString(null);
        col.addFromString("  ");

        assertNull(col.get(0));
        assertNull(col.get(1));
    }

    @Test
    @DisplayName("Deve lancar DateTimeParseException ao passar String em formato invalido")
    void testAddFromStringInvalido() {
        DateColumn col = new DateColumn("nascimento");

        assertThrows(DateTimeParseException.class, () -> col.addFromString("dia 25 de maio de 2026"));
    }

    @Test
    @DisplayName("Deve garantir que a lista retornada por values() seja imutavel")
    void testValuesImutavel() {
        DateColumn col = new DateColumn("nascimento");
        col.add(LocalDate.of(2026, 2, 1));

        assertThrows(UnsupportedOperationException.class, () -> col.values().add(LocalDate.of(2025, 1, 1)));
    }

    @Test
    @DisplayName("Deve parsear data usando formatter customizado dd/MM/yyyy")
    void testFormatterCustomizado() {
        DateColumn col =  new DateColumn("nascimento", DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        col.addFromString("01/12/2027");

        assertEquals(LocalDate.of(2027, 12, 1), col.get(0));
    }
}
