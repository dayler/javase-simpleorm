/**
 * 
 */
package com.tekhne.persistence;

public final class Queries {
    
    private Queries() { /* no op */ }
    
    public static String createTableIfNotExists(Class<?>entityClass) {
        return new CreateTableIfNotExistsQuery(entityClass).toSql();
    }
    
    public static <T>String insert(T entity) {
        return new InsertQuery<>(entity).toSql();
    }
    
    public static <T>String deleteByPrimaryKeys(Class<T>entityClazz, Object...ids) {
        return new DeleteQuery<>(entityClazz, ids).toSql();
    }
    
    public static <T>String getAll(Class<T>entityClass) {
        return new SelectAllQuery<>(entityClass).toSql();
    }
    
    public static <T>String update(T entity, Object...ids) {
        return new UpdateQuery<>(entity, ids).toSql();
    }
}
