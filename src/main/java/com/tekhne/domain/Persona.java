package com.tekhne.domain;

import com.tekhne.persistence.Column;
import com.tekhne.persistence.Entity;
import com.tekhne.persistence.Id;

@Entity("persona")
public class Persona {
    
    private static final Persona EMPTY = new Persona();
    
    @Column("id")
    @Id
    private int id;
    
    @Column("nombre")
    private String nombre;
    
    @Column("aplellido")
    private String apellido;
    
    /**
     * Default constructor, only used by ORM.
     */
    public Persona() {
        id = 0;
        nombre = "";
        apellido = "";
    }
    
    public Persona(int id, String nombre, String apellido) {
        this.id = id;
        this.nombre = nombre;
        this.apellido = apellido;
    }
    
    public int getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getApellido() {
        return apellido;
    }

    public static Persona vacia() {
        return EMPTY;
    }
}
