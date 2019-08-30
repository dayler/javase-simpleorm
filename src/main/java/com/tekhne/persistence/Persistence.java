package com.tekhne.persistence;

import java.lang.reflect.Field;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import com.tekhne.domain.ColumnDto;
import com.tekhne.domain.Pair;
import com.tekhne.exception.NullValueException;
import com.tekhne.exception.OperationException;

/**
 * Utility class used to manipulate annotations of "persistence"
 * @author dayler
 *
 */
public final class Persistence {
    
    private Persistence() { /* no op */ }
    
    public static ColumnDto[] columns(Class<?>clazz) {
        isEntityAnnotationPresent(clazz);
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(f -> !f.isAnnotationPresent(Id.class))
                     .map(ColumnDto::of)
                     .filter(Objects::nonNull)
                     .toArray(ColumnDto[]::new);
    }
    
    public static ColumnDto[] columnsWithoutPrimaryKey(Class<?>clazz) {
        isEntityAnnotationPresent(clazz);
        return Arrays.stream(clazz.getDeclaredFields())
                     .map(ColumnDto::of)
                     .filter(Objects::nonNull)
                     .toArray(ColumnDto[]::new);
    }
    
    private static void isEntityAnnotationPresent(Class<?>clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new NullValueException("Null Entity Annotation class:" + clazz.getName());
        }
    }
    
    public static String tableName(Class<?>clazz) {
        return Optional.ofNullable(clazz.getAnnotation(Entity.class))
                       .map(Entity::value)
                       .orElseThrow(() -> new NullValueException("Null Entity Annotation class:" + clazz.getName()));
    }
    
    // TODO Codify the values, for example dates, strings
    private static String toStringObject(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
    }
    
    public static <T>ColumnDto[] getPrimaryKeys(Class<T>clazz) {
        isEntityAnnotationPresent(clazz);
        return Arrays.stream(clazz.getDeclaredFields())
                     .filter(f -> f.isAnnotationPresent(Id.class))
                     .filter(f -> f.isAnnotationPresent(Column.class))
                     .map(ColumnDto::of)
                     .toArray(ColumnDto[]::new);
    }
    
    public static <T>String[]values(T entity) {
        isEntityAnnotationPresent(entity.getClass());
        Field[] fields = entity.getClass().getDeclaredFields();
        List<String> values = new ArrayList<>();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Column.class)) {
                    values.add(toStringObject(field.get(entity)));
                }
            }
        } catch (IllegalArgumentException | IllegalAccessException ex) {
            throw new OperationException("At get value of field.", ex);
        }
        return values.stream().toArray(String[]::new);
    }
    
    public static <T>List<T> entityFromRs(Class<T>clazz, ResultSet rs) {
        isEntityAnnotationPresent(clazz);
        ColumnDto[]columns = columns(clazz);
        Map<String, Field>fieldMap = toMapField(clazz);
        List<T>values = new LinkedList<>();
        try {
            while (rs.next()) {
                // create new instance of entity.
                T obj = clazz.newInstance();
                // populate data.
                for (ColumnDto c : columns) {
                    fieldMap.get(c.getFieldName()).set(obj, rs.getObject(c.getName()));
                }
                values.add(obj);
            }
            return values;
        } catch (SQLException | InstantiationException | IllegalAccessException ex) {
            throw new OperationException("At retrieve all objects form RS", ex);
        }
    }
    
    public static <T>Map<String, Field>toMapField(Class<T>clazz) {
        return Arrays.stream(clazz.getDeclaredFields())
                     .map(f -> {
                         f.setAccessible(true);
                         return Pair.of(f.getName(), f);})
                     .collect(Collectors.toMap(Pair::first, Pair::second));
    }
}
