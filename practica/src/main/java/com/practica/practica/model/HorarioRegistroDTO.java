package com.practica.practica.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

import java.time.LocalTime;

public class HorarioRegistroDTO {

    private Long idHorario; // Para edición, puede ser nulo en registro

    @NotBlank(message = "La cédula del empleado no puede estar vacía.")
    private String cedulaEmpleado;

    @NotBlank(message = "El día de la semana no puede estar vacío.")
    @Pattern(regexp = "^(Lunes|Martes|Miércoles|Jueves|Viernes|Sábado|Domingo)$", message = "El día de la semana no es válido. Ej: Lunes")
    private String dia;

    @NotNull(message = "La hora de entrada no puede ser nula.")
    // @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "La hora de entrada debe tener el formato HH:mm.")
    private LocalTime horaEntrada; // Usamos LocalTime directamente

    @NotNull(message = "La hora de salida no puede ser nula.")
    // @Pattern(regexp = "^([01]?[0-9]|2[0-3]):[0-5][0-9]$", message = "La hora de salida debe tener el formato HH:mm.")
    private LocalTime horaSalida; // Usamos LocalTime directamente

    // Constructor vacío
    public HorarioRegistroDTO() {
    }

    // Constructor con campos
    public HorarioRegistroDTO(Long idHorario, String cedulaEmpleado, String dia, LocalTime horaEntrada, LocalTime horaSalida) {
        this.idHorario = idHorario;
        this.cedulaEmpleado = cedulaEmpleado;
        this.dia = dia;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    // Getters y Setters
    public Long getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(Long idHorario) {
        this.idHorario = idHorario;
    }

    public String getCedulaEmpleado() {
        return cedulaEmpleado;
    }

    public void setCedulaEmpleado(String cedulaEmpleado) {
        this.cedulaEmpleado = cedulaEmpleado;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public LocalTime getHoraEntrada() {
        return horaEntrada;
    }

    public void setHoraEntrada(LocalTime horaEntrada) {
        this.horaEntrada = horaEntrada;
    }

    public LocalTime getHoraSalida() {
        return horaSalida;
    }

    public void setHoraSalida(LocalTime horaSalida) {
        this.horaSalida = horaSalida;
    }
}