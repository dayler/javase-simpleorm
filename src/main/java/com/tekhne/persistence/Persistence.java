package com.tekhne.persistence;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import com.tekhne.domain.ColumnDto;
import com.tekhne.exception.NullEntityAnnotationException;
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
                     .map(ColumnDto::of)
                     .filter(Objects::nonNull)
                     .toArray(ColumnDto[]::new);
    }
    
    private static void isEntityAnnotationPresent(Class<?>clazz) {
        if (!clazz.isAnnotationPresent(Entity.class)) {
            throw new NullEntityAnnotationException("Null Entity Annotation class:" + clazz.getName());
        }
    }
    
    public static String tableName(Class<?>clazz) {
        return Optional.ofNullable(clazz.getAnnotation(Entity.class))
                       .map(Entity::value)
                       .orElseThrow(() -> new NullEntityAnnotationException("Null Entity Annotation class:" + clazz.getName()));
    }
    
    // TODO Codify the values, for example dates, strings
    private static String toStringObject(Object obj) {
        if (obj == null) {
            return null;
        }
        return obj.toString();
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
}
