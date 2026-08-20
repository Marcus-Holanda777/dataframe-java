package com.dataframe.column;

import com.dataframe.type.DataType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public abstract class AbstractColumn<T> implements Column<T> {
    private final String name;
    private final DataType dataType;
    private final Class<T> type;
    protected final List<T> values;

    // Construtor da classe
    public AbstractColumn(String name, DataType dataType, Class<T> type) {
        this.name = name;
        this.dataType = dataType;
        this.type = type;
        this.values = new ArrayList<>();
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public DataType getDataType() {
        return dataType;
    }

    @Override
    public Class<T> getType() {
        return type;
    }

    @Override
    public int size() {
        return values().size();
    }

    @Override
    public T get(int index) {
        return values.get(index);
    }

    @Override
    public void add(T value) {
        values.add(value);
    }

    @Override
    public List<T> values() {
        return Collections.unmodifiableList(values);
    }

    @Override
    public abstract void addFromString(String value);
}
