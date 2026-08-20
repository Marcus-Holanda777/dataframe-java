package com.dataframe.column;

import com.dataframe.type.DataType;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class DateTimeColumn extends AbstractColumn<LocalDateTime> {
    private final DateTimeFormatter formatter;

    public DateTimeColumn(String name) {
        this(name, DateTimeFormatter.ISO_LOCAL_DATE_TIME);
    }

    public DateTimeColumn(String name, DateTimeFormatter formatter) {
        super(name, DataType.DATETIME, LocalDateTime.class);
        this.formatter = formatter;
    }

    @Override
    public void addFromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            add(null);
        } else {
            add(LocalDateTime.parse(value.trim(), formatter));
        }
    }
}
