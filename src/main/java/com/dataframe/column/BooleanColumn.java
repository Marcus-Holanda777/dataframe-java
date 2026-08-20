package com.dataframe.column;

import com.dataframe.type.DataType;

public class BooleanColumn extends AbstractColumn<Boolean> {

    public BooleanColumn(String name) {
        super(name, DataType.BOOLEAN, Boolean.class);
    }

    @Override
    public void addFromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            add(null);
        } else {
            add(Boolean.parseBoolean(value.trim()));
        }
    }
}
