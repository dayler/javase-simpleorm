package com.tekhne.persistence;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.tekhne.domain.Persona;

class QueriesTest {

    @BeforeEach
    void setUp() throws Exception {
        // no op
    }

    @AfterEach
    void tearDown() throws Exception {
        // no op
    }

    @Test
    void createTableIfNotExists() {
        assertEquals("CREATE TABLE IF NOT EXISTS persona (id INTEGER PRIMARY KEY,nombre TEXT NOT NULL,apellido TEXT NOT NULL);",
                      Queries.createTableIfNotExists(Persona.class));
    }

    @Test
    void insert() {
        Persona pepe = new Persona(100, "José Marcelo", "Villanueva Rojas");
        assertEquals("INSERT INTO persona (id,nombre,apellido) VALUES (100,'José Marcelo','Villanueva Rojas');",
                     Queries.insert(pepe));
    }

    @Test
    void deleteByPrimaryKeys() {
        assertEquals("DELETE FROM persona WHERE id=100;", Queries.deleteByPrimaryKeys(Persona.class, 100));
    }

    @Test
    void getAll() {
        System.out.println(Queries.getAll(Persona.class));
        assertEquals("SELECT id,nombre,apellido FROM persona;", Queries.getAll(Persona.class));
        
    }

    @Test
    void update() {
        Persona pepe = new Persona(100, "José Marcelo", "Villanueva Rojas");
        assertEquals("UPDATE persona SET nombre='José Marcelo',apellido='Villanueva Rojas' WHERE id=100;", Queries.update(pepe, 100));
    }
    
    @Test
    void select() {
        assertEquals("SELECT id,nombre,apellido FROM persona WHERE id=100;", Queries.select(Persona.class, 100));
    }
    
}
