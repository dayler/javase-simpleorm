package com.tekhne.persistence;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.tekhne.domain.ColumnDto;
import com.tekhne.domain.DataType;

/**
 * Creates Delete statement.<br/>
 * DELETE FROM artists_backup WHERE artistid = 1;<br/>
 * 
 * @author arielsalazar
 *
 * @param <T>
 */
public class DeleteQuery<T> extends Query {
    
    private Class<T>clazz;
    private Object[]values;
    
    public DeleteQuery(Class<T>clazz, Object[]values) {
        this.clazz = clazz;
        this.values = values;
    }
    
    private String getValueAt(int idx, DataType dt) {
        if (values.length - 1 < idx) {
            return null;
        }
        return dt.format(values[idx]);
    }
    
    @Override
    public String toSql() {
        ColumnDto[]pkColumns = Persistence.getPrimaryKeys(clazz);
        List<String>valueList = new ArrayList<>();
        for (int i = 0; i < pkColumns.length; i++) {
            ColumnDto column = pkColumns[i];
            valueList.add(column.getName() + "=" + getValueAt(i, column.getDataType()));
        }
        return String.format("DELETE FROM %s WHERE %s;", Persistence.tableName(clazz), valueList.stream().collect(Collectors.joining(",")));
    }
}
