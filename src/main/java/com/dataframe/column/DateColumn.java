package com.dataframe.column;

import com.dataframe.type.DataType;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateColumn extends AbstractColumn<LocalDate> {

    private final DateTimeFormatter formatter;

    public DateColumn(String name) {
        this(name, DateTimeFormatter.ISO_LOCAL_DATE);
    }

    public DateColumn(String name, DateTimeFormatter formatter) {
        super(name, DataType.DATE, LocalDate.class);
        this.formatter = formatter;
    }

    @Override
    public void addFromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            add(null);
        } else {
            add(LocalDate.parse(value.trim(), formatter));
        }
    }
}
