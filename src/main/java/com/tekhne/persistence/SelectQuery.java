package com.tekhne.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.tekhne.domain.ColumnDto;
import com.tekhne.domain.DataType;

public class SelectQuery<T> extends Query {

    private Class<T>entityClass;
    private Object[] ids;
    
    public SelectQuery(Class<T>entityClass, Object...ids) {
        this.entityClass = entityClass;
        this.ids = ids;
    }
    
    @Override
    public String toSql() {
        String tableName = Persistence.tableName(entityClass);
        ColumnDto[]columns = Persistence.columns(entityClass);
        Map<String, Field>fieldMap = Persistence.toMapField(entityClass);
        List<String> whereValueList = whereCondition(Persistence.getPrimaryKeys(entityClass), fieldMap);
        return String.format("SELECT %s FROM %s WHERE %s;", Arrays.stream(columns).map(ColumnDto::getName).collect(Collectors.joining(",")), tableName,
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
    
    private String getValueAt(int idx, DataType dt) {
        if (ids.length - 1 < idx) {
            return null;
        }
        return dt.format(ids[idx]);
    }
}
