package com.dataframe.column;

import com.dataframe.type.DataType;

public class IntColumn extends AbstractColumn<Integer> {

    public IntColumn(String name) {
        super(name, DataType.INTEGER, Integer.class);
    }

    @Override
    public void addFromString(String value) {
        if (value == null || value.trim().isEmpty()) {
            add(null);
        } else {
            add(Integer.parseInt(value.trim()));
        }
    }
}
