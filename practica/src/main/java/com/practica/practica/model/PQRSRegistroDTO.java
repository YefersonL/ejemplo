// src/main/java/com/practica/practica/model/PQRSRegistroDTO.java
package com.practica.practica.model;

import java.time.LocalDate;

public class PQRSRegistroDTO {

    private String rut; // Sigue siendo un String para la entrada del formulario
    private String tipoSolicitud;
    private String descripcion;
    private LocalDate fecha;

    // Constructor vacío
    public PQRSRegistroDTO() {
    }

    // Constructor con todos los campos
    public PQRSRegistroDTO(String rut, String tipoSolicitud, String descripcion, LocalDate fecha) {
        this.rut = rut;
        this.tipoSolicitud = tipoSolicitud;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    // --- Getters y Setters ---

    public String getRut() {
        return rut;
    }

    public void setRut(String rut) {
        this.rut = rut;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}