package com.tekhne.persistence;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.tekhne.domain.ColumnDto;

/**
 * Create insert statement:<br/>
 * INSERT INTO artists (name) VALUES ("Buddy Rich"),("Candido"),("Charlie Byrd");
 * @author dayler
 *
 * @param <T>
 */
public class InsertQuery<T> extends Query {
    
    private T entity;
    
    public InsertQuery(T entity) {
        this.entity = entity;
    }
    
    @Override
    public String toSql() {
        String tableName = Persistence.tableName(entity.getClass());
        String columns = Arrays.stream(Persistence.columns(entity.getClass())).map(ColumnDto::getName).collect(Collectors.joining(","));
        String values = Arrays.stream(Persistence.values(entity)).collect(Collectors.joining(","));
        return String.format("INSERT INTO %s (%s) VALUES (%s);", tableName, columns, values);
    }

}
