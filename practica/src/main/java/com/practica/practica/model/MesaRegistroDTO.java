package com.practica.practica.model; // O en un paquete com.practica.practica.dto

public class MesaRegistroDTO {

    private Integer idEspacio; // Para la clave foránea a Espacio
    private Integer numeroMesa;
    private String estadoMesa;
    private Integer capacidadMesa;

    public MesaRegistroDTO() {
    }

    public MesaRegistroDTO(Integer idEspacio, Integer numeroMesa, String estadoMesa, Integer capacidadMesa) {
        this.idEspacio = idEspacio;
        this.numeroMesa = numeroMesa;
        this.estadoMesa = estadoMesa;
        this.capacidadMesa = capacidadMesa;
    }

    // Getters y Setters
    public Integer getIdEspacio() {
        return idEspacio;
    }

    public void setIdEspacio(Integer idEspacio) {
        this.idEspacio = idEspacio;
    }

    public Integer getNumeroMesa() {
        return numeroMesa;
    }

    public void setNumeroMesa(Integer numeroMesa) {
        this.numeroMesa = numeroMesa;
    }

    public String getEstadoMesa() {
        return estadoMesa;
    }

    public void setEstadoMesa(String estadoMesa) {
        this.estadoMesa = estadoMesa;
    }

    public Integer getCapacidadMesa() {
        return capacidadMesa;
    }

    public void setCapacidadMesa(Integer capacidadMesa) {
        this.capacidadMesa = capacidadMesa;
    }
}