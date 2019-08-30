/**
 * 
 */
package com.tekhne.persistence;

import java.util.Arrays;
import java.util.stream.Collectors;

import com.tekhne.domain.ColumnDto;

/**
 * Creates SELECT all query, all data with out restrictions.<br/>
 * SELECT COL1, COL2, FROM TRABLE;
 * 
 * @author arielsalazar
 * @version 1.0 Aug 30, 2019
 * @since 1.8
 *
 */
public class SelectAllQuery<T> extends Query {
    
    private Class<T>clazz;
    
    public SelectAllQuery(Class<T>clazz) {
        this.clazz = clazz;
    }
    
    @Override
    public String toSql() {
        String tableName = Persistence.tableName(clazz);
        ColumnDto[]columns = Persistence.columns(clazz);
        return String.format("SELECT % FROM %s;", Arrays.stream(columns).map(ColumnDto::getName).collect(Collectors.joining(",")), tableName);
    }

}
