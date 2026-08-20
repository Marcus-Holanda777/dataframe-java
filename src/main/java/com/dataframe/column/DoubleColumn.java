package com.dataframe.column;

import com.dataframe.type.DataType;

public class DoubleColumn extends AbstractColumn<Double> {

    public DoubleColumn(String name) {
        super(name, DataType.DOUBLE, Double.class);
    }

    @Override
    public void addFromString(String value) {
        if(value == null || value.trim().isEmpty()) {
            add(null);
        } else {
            add(Double.parseDouble(value.trim()));
        }
    }
}
