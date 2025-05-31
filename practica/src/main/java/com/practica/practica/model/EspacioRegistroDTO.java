package com.practica.practica.model; // O en un paquete com.practica.practica.dto

public class EspacioRegistroDTO {

    private Integer idSede; // Para la clave foránea a Sede
    private String nombreEspacio;
    private String tipoEspacio;
    private Integer capacidad;

    public EspacioRegistroDTO() {
    }

    public EspacioRegistroDTO(Integer idSede, String nombreEspacio, String tipoEspacio, Integer capacidad) {
        this.idSede = idSede;
        this.nombreEspacio = nombreEspacio;
        this.tipoEspacio = tipoEspacio;
        this.capacidad = capacidad;
    }

    // Getters y Setters
    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public String getNombreEspacio() {
        return nombreEspacio;
    }

    public void setNombreEspacio(String nombreEspacio) {
        this.nombreEspacio = nombreEspacio;
    }

    public String getTipoEspacio() {
        return tipoEspacio;
    }

    public void setTipoEspacio(String tipoEspacio) {
        this.tipoEspacio = tipoEspacio;
    }

    public Integer getCapacidad() {
        return capacidad;
    }

    public void setCapacidad(Integer capacidad) {
        this.capacidad = capacidad;
    }
}