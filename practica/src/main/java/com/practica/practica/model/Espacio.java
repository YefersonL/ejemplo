package com.practica.practica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;

@Entity
public class Espacio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_Espacio;

    @ManyToOne
    @JoinColumn(name = "ID_Sede", nullable = false)
    private Sede sede; // Relación con la entidad Sede

    @Column(name = "Nombre_Espacio", nullable = false, length = 50)
    private String nombre_Espacio;

    @Column(name = "Tipo_Espacio", length = 50)
    private String tipo_Espacio;

    @Column(name = "Capacidad")
    private Integer capacidad;

    // Constructores
    public Espacio() {
    }

    public Espacio(Sede sede, String nombre_Espacio, String tipo_Espacio, Integer capacidad) {
        this.sede = sede;
        this.nombre_Espacio = nombre_Espacio;
        this.tipo_Espacio = tipo_Espacio;
        this.capacidad = capacidad;
    }

    // Getters y Setters
    public Integer getId_Espacio() {
        return id_Espacio;
    }

    public void setId_Espacio(Integer id_Espacio) {
        this.id_Espacio = id_Espacio;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public String getNombre_Espacio() {
        return nombre_Espacio;
    }

    public void setNombre_Espacio(String nombre_Espacio) {
        this.nombre_Espacio = nombre_Espacio;
    }

    public String getTipo_Espacio() {
        return tipo_Espacio;
    }

    public void setTipo_Espacio(String tipo_Espacio) {
        this.tipo_Espacio = tipo_Espacio;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
}