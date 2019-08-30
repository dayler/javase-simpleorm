/**
 * 
 */
package com.tekhne.persistence;

/**
 * @author dayler
 *
 */
public final class Db {
    
    private Db() { /* no op */ }
    
    public static String createTableIfNotExists(Class<?>entityClass) {
        return new CreateTableIfNotExistsQuery(entityClass).toSql().get();
    }
    
    public static <T>String insert(T entity) {
        return new InsertQuery<>(entity).toSql().get();
    }
    
    public static <T>String deleteByPrimaryKey(Class<T>entityClazz, Object...ids) {
        // TODO 
        return null;
    }
    
    public static <T>String getAll(Class<T>entityClass) {
        // TODO 
        return null;
    }
    
    public static <T>String update(T entity, Object...ids) {
        // TODO 
        return null;
    }
}
