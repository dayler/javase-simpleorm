package com.tekhne.persistence;

import java.util.Optional;
import java.util.stream.Stream;

import com.tekhne.domain.Persona;
import com.tekhne.persistence.sqlite.ConnectionManager;
import com.tekhne.persistence.sqlite.RepositorioPolicy;

public class Repositorio<T> {
    
    public Repositorio(Class<T>clazz, ConnectionManager cm, RepositorioPolicy policy) {
        
    }

    public void agregar(Persona persona) {
        // TODO Auto-generated method stub
        
    }

    public Stream<T> getTodos() {
        // TODO Auto-generated method stub
        return null;
    }

    public void actualizar(int id, Persona persona) {
        // TODO Auto-generated method stub
        
    }

    public <R>Stream<R> executeQuery(Class<R> clazz, String sql) {
        // TODO Auto-generated method stub
        return null;
    }

    public Optional<T>getPorId(int id) {
        // TODO Auto-generated method stub
        return null;
    }

    public void delete(int id) {
        // TODO Auto-generated method stub
        
    }
}
