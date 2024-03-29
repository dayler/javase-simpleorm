package com.tekhne;

import com.tekhne.domain.Persona;
import com.tekhne.persistence.ConnectionManager;
import com.tekhne.persistence.Repositorio;
import com.tekhne.persistence.RepositorioPolicy;

public class ProyectoFinal {

    public static void main(String[] args) {
        try {
            //ConnectionManager cm = new ConnectionManager("jdbc:sqlite:D:/JavaFinal/personas.db");
            ConnectionManager cm = new ConnectionManager("jdbc:sqlite:/tmp/personas.db");

            Repositorio<Persona> genteRepo = new Repositorio<>(Persona.class, 
                                                               cm, 
                                                               RepositorioPolicy.CREAR_TABLA_SI_NO_EXISTE);
            
            genteRepo.agregar(new Persona(164592, "Roberto", "Gomez"));
            genteRepo.agregar(new Persona(164593, "Juan", "Perez"));
            genteRepo.agregar(new Persona(164594, "Juan", "Lopez"));
            
            genteRepo.getTodos()
                     .filter(p -> p.getNombre().equals("Juan"))
                     .forEach(System.out::println);
            
            System.out.println(genteRepo.getPorId(12354).orElse(Persona.vacia()));
            
            genteRepo.agregar(new Persona(482721, "Roberto", "Gomez"));
            genteRepo.actualizar(164594, new Persona(164594, "Peter", "Gabriel"));
            // Always check if the RS has the required structure
            genteRepo.executeQuery(Persona.class, "SELECT * FROM personas WHERE apellido LIKE 'G%'")
                     .map(p -> p.getId())
                     .forEach(System.out::println);
            
            System.out.println("****");
            // For the primitive types just check if the first column has a compatible type
            genteRepo.executeQuery(String.class, "SELECT nombre FROM personas")
                     .forEach(System.out::println);
            
            System.out.println("****");
            genteRepo.delete(164592);
        }
        catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}
