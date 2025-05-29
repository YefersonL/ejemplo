package com.practica.practica.model;

public class PlatoRegistroDTO {

    private String nombrePlato;
    private String descripcion;
    private double precio;
    // private String categoria; // Eliminado previamente
    // private boolean disponible; // ¡Ahora eliminado!

    public PlatoRegistroDTO() {
    }

    // Constructor actualizado sin 'categoria' ni 'disponible'
    public PlatoRegistroDTO(String nombrePlato, String descripcion, double precio) {
        this.nombrePlato = nombrePlato;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters y Setters
    public String getNombrePlato() {
        return nombrePlato;
    }

    public void setNombrePlato(String nombrePlato) {
        this.nombrePlato = nombrePlato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }

    // Métodos get/set de categoria y disponible ¡Eliminados!
    // public boolean isDisponible() { /* ... */ }
    // public void setDisponible(boolean disponible) { /* ... */ }
}