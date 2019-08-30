package com.tekhne.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tekhne.domain.ColumnDto;
import com.tekhne.domain.DataType;
import com.tekhne.exception.OperationException;

/**
 * Make the update SQL query.
 * <br/>
 * UPDATE table SET column_1 = new_value_1,column_2 = new_value_2 WHERE search_condition;
 * @author arielsalazar
 * @since 1.8
 *
 * @param <T>
 */
public class UpdateQuery<T> extends Query {
    
    private T entity;
    private Object[] ids;
    
    public UpdateQuery(T entity, Object...ids) {
        this.entity = entity;
        this.ids = ids;
    }
    
    @Override
    public String toSql() {
        String tableName = Persistence.tableName(entity.getClass());
        ColumnDto[] columns = Persistence.columnsWithoutPrimaryKey(entity.getClass());
        Map<String, Field>fieldMap = Persistence.toMapField(entity.getClass());
        List<String> setValues = getSetStatement(columns, fieldMap);
        List<String> whereValueList = whereCondition(Persistence.getPrimaryKeys(entity.getClass()), fieldMap);
        return String.format("UPDATE %s SET %s WHERE %s;", tableName, setValues.stream().collect(Collectors.joining(",")),
                             whereValueList.stream().collect(Collectors.joining(",")));
    }
    
    private List<String>whereCondition(ColumnDto[]pkColumns, Map<String, Field> fieldMap) {
        List<String>whereValues = new ArrayList<>();
        for (int i = 0; i < pkColumns.length; i++) {
            ColumnDto c = pkColumns[i];
            whereValues.add(c.getName() + "=" + getValueAt(i, c.getDataType()));
        }
        return whereValues;
    }
    
    private List<String> getSetStatement(ColumnDto[] columns, Map<String, Field> fieldMap) {
        List<String>setValues = new ArrayList<>();
        try {
            for (ColumnDto c : columns) {
                Field field = fieldMap.get(c.getFieldName());
                setValues.add(c.getName() + "=" + c.getDataType().format(field.get(entity)));
            }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new OperationException("At get SET statement", ex);
        }
        return setValues;
    }
    
    private String getValueAt(int idx, DataType dt) {
        if (ids.length - 1 < idx) {
            return null;
        }
        return dt.format(ids[idx]);
    }
}
