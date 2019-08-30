package com.tekhne.persistence;

import java.util.function.Supplier;

/**
 * Base query class.
 * @author dayler
 */
public abstract class Query {

    public abstract Supplier<String> toSql();
}
