package com.tekhne.domain;

import com.tekhne.persistence.Column;
import com.tekhne.persistence.Entity;
import com.tekhne.persistence.Id;

@Entity("persona")
public class Persona {
    
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
        // no op
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
        // TODO Auto-generated method stub
        return null;
    }
}
