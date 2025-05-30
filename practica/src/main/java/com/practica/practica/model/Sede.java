package com.practica.practica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;

@Entity
public class Sede {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_Sede; // Tipo Integer según tu imagen

    @Column(name = "Nombre_Sede", nullable = false, length = 50)
    private String nombre_Sede;

    @Column(name = "Direccion", length = 255)
    private String direccion;

    // Constructores
    public Sede() {
    }

    public Sede(String nombre_Sede, String direccion) {
        this.nombre_Sede = nombre_Sede;
        this.direccion = direccion;
    }

    // Getters y Setters
    public Integer getId_Sede() {
        return id_Sede;
    }

    public void setId_Sede(Integer id_Sede) {
        this.id_Sede = id_Sede;
    }

    public String getNombre_Sede() {
        return nombre_Sede;
    }

    public void setNombre_Sede(String nombre_Sede) {
        this.nombre_Sede = nombre_Sede;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    @Override
    public String toString() {
        return "Sede{" +
                "id_Sede=" + id_Sede +
                ", nombre_Sede='" + nombre_Sede + '\'' +
                ", direccion='" + direccion + '\'' +
                '}';
    }
}