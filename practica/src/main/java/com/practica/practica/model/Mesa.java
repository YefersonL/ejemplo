package com.practica.practica.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;

import java.util.List;
import java.util.ArrayList;

@Entity
public class Mesa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_Mesa;

    @ManyToOne
    @JoinColumn(name = "ID_Espacio", nullable = false)
    private Espacio espacio; // Relación Muchas a Una con Espacio

    @Column(name = "Numero_Mesa", nullable = false)
    private Integer numero_Mesa;

    @Column(name = "Estado_Mesa", length = 50)
    private String estado_Mesa; // Ej: "Disponible", "Ocupada", "Reservada", "Limpieza"

    @Column(name = "Capacidad_Mesa")
    private Integer capacidad_Mesa;

//    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
//    private List<Orden> ordenes = new ArrayList<>(); // Relación Una a Muchas con Orden

    // Constructores
    public Mesa() {
    }

    public Mesa(Espacio espacio, Integer numero_Mesa, String estado_Mesa, Integer capacidad_Mesa) {
        this.espacio = espacio;
        this.numero_Mesa = numero_Mesa;
        this.estado_Mesa = estado_Mesa;
        this.capacidad_Mesa = capacidad_Mesa;
    }

    // Getters y Setters
    public Integer getId_Mesa() {
        return id_Mesa;
    }

    public void setId_Mesa(Integer id_Mesa) {
        this.id_Mesa = id_Mesa;
    }

    public Espacio getEspacio() {
        return espacio;
    }

    public void setEspacio(Espacio espacio) {
        this.espacio = espacio;
    }

    public Integer getNumero_Mesa() {
        return numero_Mesa;
    }

    public void setNumero_Mesa(Integer numero_Mesa) {
        this.numero_Mesa = numero_Mesa;
    }

    public String getEstado_Mesa() {
        return estado_Mesa;
    }

    public void setEstado_Mesa(String estado_Mesa) {
        this.estado_Mesa = estado_Mesa;
    }

    public Integer getCapacidad_Mesa() {
        return capacidad_Mesa;
    }

    public void setCapacidad_Mesa(Integer capacidad_Mesa) {
        this.capacidad_Mesa = capacidad_Mesa;
    }

//    public List<Orden> getOrdenes() {
//        return ordenes;
//    }
//
//    public void setOrdenes(List<Orden> ordenes) {
//        this.ordenes = ordenes;
//    }
//
//    // Métodos de conveniencia para la relación OneToMany
//    public void addOrden(Orden orden) {
//        ordenes.add(orden);
//        orden.setMesa(this);
//    }
//
//    public void removeOrden(Orden orden) {
//        ordenes.remove(orden);
//        orden.setMesa(null);
//    }
}