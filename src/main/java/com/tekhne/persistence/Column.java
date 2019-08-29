package com.tekhne.persistence;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

@Retention(RUNTIME)
@Target(FIELD)
public @interface Column {
    public String value();
    public int length() default 0;
    public boolean nullable() default true;
    public boolean unique() default false;
}
