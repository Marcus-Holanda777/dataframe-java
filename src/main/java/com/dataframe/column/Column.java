package com.dataframe.column;

import com.dataframe.type.DataType;
import java.util.List;

public interface Column<T> {

    // Retorna o nome da coluna
    String getName();

    // Retorna o total de linhas na coluna
    int size();

    // Retornar o elemento em um determinado indice (0, 1, 2)
    T get(int index);

    // Adiciona um novo elemento ao final da coluna
    void add(T value);

    // Retorna todos os valores como uma lista
    List<T> values();

    // Retorna a classe do tipo armazenado
    Class<T> getType();

    // retorna o tipo Enum da coluna
    DataType getDataType();

    // Metodo para entender o tipo com base em texto ele faz a convercao
    void addFromString(String value);
}