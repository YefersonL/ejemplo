package com.practica.practica.model; // O tu paquete DTO si lo tienes separado

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
// Otros imports si son necesarios, como NotNull, Min, Max, etc.

public class AreaRegistroDTO {

    @NotBlank(message = "El nombre del área no puede estar vacío.")
    @Size(max = 50, message = "El nombre del área no puede exceder los 50 caracteres.")
    private String nombreArea;

    // Constructor vacío
    public AreaRegistroDTO() {
    }

    // Constructor con todos los campos
    public AreaRegistroDTO(String nombreArea) {
        this.nombreArea = nombreArea;
    }

    // Getters y Setters
    public String getNombreArea() {
        return nombreArea;
    }

    public void setNombreArea(String nombreArea) {
        this.nombreArea = nombreArea;
    }
}