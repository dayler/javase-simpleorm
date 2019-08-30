package com.tekhne.domain;

public enum DataType {
    INTEGER(new Class<?>[] {Integer.class, Long.class}),
    REAL(new Class<?>[] {Float.class, Double.class}),
    TEXT(new Class<?>[] {String.class}),
    BLOB(new Class<?>[] {}),
    ;
    
    private Class<?>[]primitiveTypes;
    
    DataType(Class<?>[]primitiveTypes) {
        this.primitiveTypes = primitiveTypes;
    }
    
    private boolean match(Class<?>type) {
        for (Class<?> pt : primitiveTypes) {
            if (pt.equals(type)) {
                return true;
            }
        }
        return false;
    }
    
    public static DataType of(Class<?>type) {
        for (DataType dt : values()) {
            if (dt.match(type)) {
                return dt;
            }
        }
        return BLOB;
    }
}
