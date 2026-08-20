package com.dataframe.column;

import com.dataframe.type.DataType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

import static org.junit.jupiter.api.Assertions.*;

public class DateTimeColumnTest {
    @Test
    @DisplayName("Deve inicializar a coluna com nome e tipo corretos")
    void testInicializacao() {
        DateTimeColumn col = new DateTimeColumn("nascimento");

        assertEquals("nascimento", col.getName());
        assertEquals(DataType.DATETIME, col.getDataType());
        assertEquals(LocalDateTime.class, col.getType());
        assertEquals(0, col.size());
    }

    @Test
    @DisplayName("Deve converter String em LocalDateTime via addFromString valido")
    void testAddFromStringValido() {
        DateTimeColumn col = new DateTimeColumn("nascimento");

        LocalDateTime inpRef = LocalDateTime.of(2025, 1, 1, 0, 0, 0);
        LocalDateTime inpRefTwo = LocalDateTime.of(2026, 2, 5,0, 0, 0);
        LocalDateTime inpRefThre = LocalDateTime.of(2023, 8, 9, 0, 0, 0);

        col.add(inpRef);
        col.addFromString("2026-02-05T00:00:00");
        col.addFromString(" 2023-08-09T00:00:00 ");

        LocalDateTime ref = col.get(0);
        LocalDateTime refTwo = col.get(1);
        LocalDateTime refThre = col.get(2);

        assertEquals(inpRef, ref);
        assertEquals(inpRefTwo, refTwo);
        assertEquals(inpRefThre, refThre);
        assertEquals(3, col.size());
    }

    @Test
    @DisplayName("Deve tratar null e String vazia como entrada nula")
    void testAddFromStringNuloOuVazio() {
        DateTimeColumn col = new DateTimeColumn("nascimento");

        col.addFromString(null);
        col.addFromString("  ");

        assertNull(col.get(0));
        assertNull(col.get(1));
    }

    @Test
    @DisplayName("Deve lancar DateTimeParseException ao passar String em formato invalido")
    void testAddFromStringInvalido() {
        DateTimeColumn col = new DateTimeColumn("nascimento");

        assertThrows(DateTimeParseException.class, () -> col.addFromString("2025-01-01"));
    }

    @Test
    @DisplayName("Deve garantir que a lista retornada por values() seja imutavel")
    void testValuesImutavel() {
        DateTimeColumn col = new DateTimeColumn("nascimento");
        col.add(LocalDateTime.of(2026, 2, 1, 0, 0, 0));

        assertThrows(UnsupportedOperationException.class, () -> col.values().add(LocalDateTime.of(2025, 1, 1, 0, 0, 0)));
    }

    @Test
    @DisplayName("Deve parsear data usando formatter customizado dd/MM/yyyy HH:mm:ss")
    void testFormatterCustomizado() {
        DateTimeColumn col = new DateTimeColumn("nascimento", DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss"));
        col.addFromString("01/12/2027 23:22:22");

        assertEquals(LocalDateTime.of(2027, 12, 1, 23, 22, 22), col.get(0));
    }
}
