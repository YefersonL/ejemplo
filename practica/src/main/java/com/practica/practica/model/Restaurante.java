package com.practica.practica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Restaurante") // Asegúrate de que el nombre de la tabla coincida con tu DDL
public class Restaurante {

    @Id
    @Column(name = "RUT", length = 20) // RUT es la clave primaria y VARCHAR(20)
    private String RUT;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    // Constructores
    public Restaurante() {
    }

    public Restaurante(String RUT, String nombre) {
        this.RUT = RUT;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getRUT() {
        return RUT;
    }

    public void setRUT(String RUT) {
        this.RUT = RUT;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "RUT='" + RUT + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}