package com.practica.practica.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EmpleadoRegistroDTO {

    @NotBlank(message = "La cédula no puede estar vacía.")
    @Size(max = 20, message = "La cédula no puede exceder los 20 caracteres.")
    private String cedula;

    @NotBlank(message = "Los nombres no pueden estar vacíos.")
    @Size(max = 100, message = "Los nombres no pueden exceder los 100 caracteres.")
    private String nombres;

    @Size(max = 20, message = "El número de contacto no puede exceder los 20 caracteres.")
    @Pattern(regexp = "^$|^[0-9]{7,15}$", message = "El número de contacto debe contener solo dígitos y entre 7 y 15 caracteres (opcional).")
    private String numeroContacto;

    @Email(message = "El correo corporativo debe ser una dirección de correo válida.")
    @Size(max = 100, message = "El correo corporativo no puede exceder los 100 caracteres.")
    @Pattern(regexp = "^$|^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$", message = "El correo corporativo debe tener un formato válido (opcional).")
    private String correoCorporativo;

    @NotBlank(message = "El RUT del restaurante no puede estar vacío.")
    private String rutRestaurante;

    @NotNull(message = "El ID de la sede no puede estar vacío.")
    private Integer idSede;

    @NotNull(message = "El ID del cargo no puede estar vacío.")
    private Long idCargo; // Asegúrate de que coincida con el tipo de ID de Cargo1

    @NotNull(message = "El ID del área no puede estar vacío.")
    private Integer idArea; // Nuevo campo para el ID del área

    public EmpleadoRegistroDTO() {
    }

    public EmpleadoRegistroDTO(String cedula, String nombres, String numeroContacto, String correoCorporativo,
                               String rutRestaurante, Integer idSede, Long idCargo, Integer idArea) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.numeroContacto = numeroContacto;
        this.correoCorporativo = correoCorporativo;
        this.rutRestaurante = rutRestaurante;
        this.idSede = idSede;
        this.idCargo = idCargo;
        this.idArea = idArea;
    }

    // Getters y Setters
    public String getCedula() {
        return cedula;
    }

    public void setCedula(String cedula) {
        this.cedula = cedula;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getNumeroContacto() {
        return numeroContacto;
    }

    public void setNumeroContacto(String numeroContacto) {
        this.numeroContacto = numeroContacto;
    }

    public String getCorreoCorporativo() {
        return correoCorporativo;
    }

    public void setCorreoCorporativo(String correoCorporativo) {
        this.correoCorporativo = correoCorporativo;
    }

    public String getRutRestaurante() {
        return rutRestaurante;
    }

    public void setRutRestaurante(String rutRestaurante) {
        this.rutRestaurante = rutRestaurante;
    }

    public Integer getIdSede() {
        return idSede;
    }

    public void setIdSede(Integer idSede) {
        this.idSede = idSede;
    }

    public Long getIdCargo() {
        return idCargo;
    }

    public void setIdCargo(Long idCargo) {
        this.idCargo = idCargo;
    }

    public Integer getIdArea() {
        return idArea;
    }

    public void setIdArea(Integer idArea) {
        this.idArea = idArea;
    }
}