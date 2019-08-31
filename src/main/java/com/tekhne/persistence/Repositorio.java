package com.tekhne.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Optional;
import java.util.stream.Stream;

import com.tekhne.exception.NullValueException;
import com.tekhne.exception.OperationException;

public class Repositorio<T> {
    
    private Class<T>entityClass;
    private ConnectionManager cm;
    private RepositorioPolicy policy;
    
    public Repositorio(Class<T>clazz, ConnectionManager cm, RepositorioPolicy policy) {
        this.entityClass = clazz;
        this.cm = cm;
        this.policy = policy;
    }

    public void agregar(T entity) {
        createTableIfNotExists();
        try (Connection conn = cm.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(Queries.insert(entity));
        } catch (SQLException ex) {
            throw new OperationException("Trying to insert new column", ex);
        }
    }

    private void createTableIfNotExists() {
        if (RepositorioPolicy.CREAR_TABLA_SI_NO_EXISTE.equals(policy)) {
            try (Connection conn = cm.getConnection(); Statement stmt = conn.createStatement()) {
                stmt.execute(Queries.createTableIfNotExists(entityClass));
            } catch (SQLException ex) {
                throw new OperationException("Trying to create new table:" + entityClass.getName(), ex);
            }
        }
    }

    public Stream<T> getTodos() {
        try (Connection conn = cm.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(Queries.selectAll(entityClass));
            return Persistence.entitiesFromRs(entityClass, rs).stream();
        } catch (SQLException ex) {
            throw new OperationException("At get all entities for " + entityClass.getName(), ex);
        }
    }

    public void actualizar(int id, T entity) {
        try (Connection conn = cm.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(Queries.update(entity, id));
        } catch (SQLException ex) {
            throw new OperationException("At update entity:" + entityClass.getName(), ex);
        }
    }

    public <R>Stream<R> executeQuery(Class<R>clazz, String sql) {
        try (Connection conn = cm.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(sql);
            if (clazz.isAnnotationPresent(Entity.class)) {
                return Persistence.entitiesFromRs(clazz, rs).stream();
            } else if (String.class.equals(clazz) ||
                       Integer.class.equals(clazz) || 
                       int.class.equals(clazz) ||
                       Long.class.equals(clazz) || 
                       long.class.equals(clazz) ||
                       Float.class.equals(clazz) || 
                       float.class.equals(clazz) ||
                       Double.class.equals(clazz) || 
                       double.class.equals(clazz)) { // check for primitive types.
                return Persistence.primitivesFromRs(clazz, rs).stream();
            } else {
                throw new NullValueException("Null Entity Annotation class:" + clazz.getName());
            }
        } catch (SQLException ex) {
            throw new OperationException("At execute query:" + sql, ex);
        }
    }

    public Optional<T>getPorId(int id) {
        try (Connection conn = cm.getConnection(); Statement stmt = conn.createStatement()) {
            ResultSet rs = stmt.executeQuery(Queries.select(entityClass, id));
            return Persistence.entitiesFromRs(entityClass, rs).stream().findAny();
        } catch (SQLException ex) {
            throw new OperationException("At select query ID:" + id, ex);
        }
    }

    public void delete(int id) {
        try (Connection conn = cm.getConnection(); Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(Queries.deleteByPrimaryKeys(entityClass, id));
        } catch (SQLException ex) {
            throw new OperationException("At delete entity id:" + id, ex);
        }
    }
}
