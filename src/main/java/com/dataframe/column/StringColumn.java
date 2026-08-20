package com.dataframe.column;

import com.dataframe.type.DataType;

public class StringColumn extends AbstractColumn<String> {

    public StringColumn(String name) {
        super(name, DataType.STRING, String.class);
    }

    @Override
    public void addFromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            add(null);
        } else {
            add(value.trim());
        }
    }
}
