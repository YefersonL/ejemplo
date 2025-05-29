package com.practica.practica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre_Plato;
    private String descripcion;
    private double precio;
    // private String categoria; // Eliminado previamente
    // private boolean disponible; // ¡Ahora eliminado!

    // Constructores
    public Plato() {
    }

    // Constructor actualizado sin 'categoria' ni 'disponible'
    public Plato(String nombre_Plato, String descripcion, double precio) {
        this.nombre_Plato = nombre_Plato;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters y Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_Plato() {
        return nombre_Plato;
    }

    public void setNombre_Plato(String nombre_Plato) {
        this.nombre_Plato = nombre_Plato;
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

    @Override
    public String toString() {
        return "Plato{" +
                "id=" + id +
                ", nombre_Plato='" + nombre_Plato + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                '}';
    }
}