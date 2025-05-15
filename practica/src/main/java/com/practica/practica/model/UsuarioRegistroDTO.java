package com.practica.practica.model;

// Esta clase es un objeto simple para transferir datos desde el formulario HTML
// No necesita anotaciones de JPA (@Entity, @Id, etc.)

public class UsuarioRegistroDTO {

    // Los nombres de los campos deben coincidir con los atributos 'name' en los inputs de tu HTML
    private String nombre;
    private String email;
    private Integer edad; // Usa el mismo tipo que en la Entidad

    // --- Constructores ---
    public UsuarioRegistroDTO() {
    }

    public UsuarioRegistroDTO(String nombre, String email, Integer edad) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }


    // --- Getters y Setters (Necesarios para que Spring pueda mapear los datos del formulario) ---
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    // Opcional: toString()
}