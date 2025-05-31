package com.practica.practica.model; // Considera mover DTOs a un paquete 'com.practica.practica.dto'

import jakarta.validation.constraints.NotBlank; // Para validación de campos no vacíos
import jakarta.validation.constraints.Size;   // Para validación de tamaño

public class MateriaPrimaRegistroDTO {

    @NotBlank(message = "El nombre de la materia prima no puede estar vacío.")
    @Size(max = 100, message = "El nombre no puede exceder los 100 caracteres.")
    private String nombre;

    @NotBlank(message = "La unidad de medida no puede estar vacía.")
    @Size(max = 50, message = "La unidad de medida no puede exceder los 50 caracteres.")
    private String unidadMedida;

    public MateriaPrimaRegistroDTO() {
    }

    public MateriaPrimaRegistroDTO(String nombre, String unidadMedida) {
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
    }

    // Getters y Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUnidadMedida() {
        return unidadMedida;
    }

    public void setUnidadMedida(String unidadMedida) {
        this.unidadMedida = unidadMedida;
    }
}