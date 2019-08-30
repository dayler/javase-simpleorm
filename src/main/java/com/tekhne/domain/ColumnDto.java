
package com.tekhne.domain;

import java.lang.reflect.Field;

import com.tekhne.persistence.Column;
import com.tekhne.persistence.Id;

public class ColumnDto {

    private String name;
    private boolean primaryKey;
    private boolean nullable;
    private boolean unique;
    private DataType dataType;
    
    public ColumnDto(String name, boolean primaryKey, boolean nullable, boolean unique, DataType dataType) {
        this.name = name;
        this.primaryKey = primaryKey;
        this.nullable = nullable;
        this.unique = unique;
        this.dataType = dataType;
    }
    
    public String getName() {
        return name;
    }


    public boolean isPrimaryKey() {
        return primaryKey;
    }

    public boolean isNullable() {
        return nullable;
    }

    public boolean isUnique() {
        return unique;
    }

    public DataType getDataType() {
        return dataType;
    }

    public static ColumnDto of(Field field) {
        Column c  = field.getAnnotation(Column.class);
        if (c == null) {
            return null;
        }
        Id id = field.getAnnotation(Id.class);
        return new ColumnDto(c.value(), id != null, c.nullable(), c.unique(), DataType.of(field.getType()));
    }
}
