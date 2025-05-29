package com.practica.practica.model;

// Esta clase es un objeto simple para transferir datos desde el formulario HTML
// No necesita anotaciones de JPA (@Entity, @Id, etc.)

public class PlatoRegistroDTO {

    // Los Nombre_Platos de los campos deben coincidir con los atributos 'name' en los inputs de tu HTML
    private String Nombre_Plato;
    private String Descripcion;
    private double Precio; // Usa el mismo tipo que en la Entidad

    // --- Constructores ---
    public PlatoRegistroDTO() {
    }

    public PlatoRegistroDTO(String Nombre_Plato, String Descripcion, double Precio) {
        this.Nombre_Plato = Nombre_Plato;
        this.Descripcion = Descripcion;
        this.Precio = Precio;
    }


    // --- Getters y Setters (Necesarios para que Spring pueda mapear los datos del formulario) ---
    public String getNombre_Plato() {
        return Nombre_Plato;
    }

    public void setNombre_Plato(String Nombre_Plato) {
        this.Nombre_Plato = Nombre_Plato;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public double getPrecio() {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }

    // Opcional: toString()
}