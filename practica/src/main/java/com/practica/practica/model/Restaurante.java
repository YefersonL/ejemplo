package com.practica.practica.model;

import jakarta.persistence.CascadeType; // Importante para operaciones en cascada
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType; // Útil para definir la estrategia de carga
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany; // Para la relación uno a muchos
import jakarta.persistence.Table;
import java.util.List; // Para la colección de PQRS
import java.util.ArrayList; // Implementación de List

@Entity
@Table(name = "Restaurante")
public class Restaurante {

    @Id
    @Column(name = "RUT", length = 20)
    private String RUT;

    @Column(name = "Nombre", nullable = false, length = 100)
    private String nombre;

    // mappedBy indica el campo en la entidad PQRS que gestiona la relación
    // CascadeType.ALL significa que las operaciones (persist, merge, remove, etc.)
    // en Restaurante se propagarán a las PQRS asociadas.
    // FetchType.LAZY significa que las PQRS no se cargarán hasta que se accedan explícitamente.
    @OneToMany(mappedBy = "restaurante", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PQRS> pqrsList = new ArrayList<>(); // Inicializar la lista

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

    public List<PQRS> getPqrsList() {
        return pqrsList;
    }

    public void setPqrsList(List<PQRS> pqrsList) {
        this.pqrsList = pqrsList;
    }

    // Métodos de ayuda para gestionar la relación bidireccional (opcional pero recomendado)
    public void addPQRS(PQRS pqrs) {
        pqrsList.add(pqrs);
        pqrs.setRestaurante(this);
    }

    public void removePQRS(PQRS pqrs) {
        pqrsList.remove(pqrs);
        pqrs.setRestaurante(null);
    }

    @Override
    public String toString() {
        return "Restaurante{" +
                "RUT='" + RUT + '\'' +
                ", nombre='" + nombre + '\'' +
                '}';
    }
}