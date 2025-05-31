// src/main/java/com/practica/practica.model/Area.java
package com.practica.practica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "Area")
public class Area {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_Area;

    @Column(name = "Nombre_Area", nullable = false, length = 50)
    private String nombre_Area;

    // Constructores
    public Area() {
    }

    public Area(String nombre_Area) {
        this.nombre_Area = nombre_Area;
    }

    // Getters y Setters
    public Integer getId_Area() {
        return id_Area;
    }

    public void setId_Area(Integer id_Area) {
        this.id_Area = id_Area;
    }

    public String getNombre_Area() {
        return nombre_Area;
    }

    public void setNombre_Area(String nombre_Area) {
        this.nombre_Area = nombre_Area;
    }

    @Override
    public String toString() {
        return "Area{" +
                "id_Area=" + id_Area +
                ", nombre_Area='" + nombre_Area + '\'' +
                '}';
    }
}