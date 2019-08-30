package com.tekhne.persistence;

import java.util.Arrays;
import java.util.function.Supplier;
import java.util.stream.Collectors;

import com.tekhne.domain.ColumnDto;

/**
 * Make a create table statement<br>
 * Example:<br>
 * CREATE TABLE IF NOT EXISTS contacts (contact_id INTEGER PRIMARY KEY,first_name TEXT NOT NULL,last_name TEXT NOT NULL,email TEXT NOT NULL UNIQUE,
 * phone TEXT NOT NULL UNIQUE);
 * @author dayler
 *
 */
public class CreateTableIfNotExistsQuery extends Query {
    
    private Class<?>entityClass;
    
    public CreateTableIfNotExistsQuery(Class<?>entityClass) {
        this.entityClass = entityClass;
    }

    @Override
    public Supplier<String> toSql() {
        String tableName = Persistence.tableName(entityClass);
        ColumnDto[]columns = Persistence.columns(entityClass);
        return () -> {
            StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ").append(tableName)
                                                                               .append(" (")
                                                                               .append(makeCreateColumnsBody(columns))
                                                                               .append(");");
            
            return sb.toString();
        };
    }

    private static String makeCreateColumnsBody(ColumnDto[] columns) {
        return Arrays.stream(columns).map(CreateTableIfNotExistsQuery::makeCollDefinition).collect(Collectors.joining(","));
    }
    
    private static String makeCollDefinition(ColumnDto column) {
        String base = column.getName() + " " + column.getDataType().name();
        if (column.isPrimaryKey()) {
            return base + " PRIMARY KEY";
        }
        return base + (column.isNullable()? " NULL" : " NOT NULL") + (column.isUnique()? " UNIQUE" : "");
    }
    
}
