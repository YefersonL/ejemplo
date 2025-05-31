package com.practica.practica.model;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import java.math.BigDecimal;
import java.time.LocalDate;

public class NominaRegistroDTO {

    private Integer idNomina; // Para la edición

    @NotBlank(message = "La cédula del empleado no puede estar vacía.")
    private String cedulaEmpleado;

    @NotNull(message = "La fecha de pago no puede estar vacía.")
    @PastOrPresent(message = "La fecha de pago no puede ser futura.")
    private LocalDate fechaPago;

    @NotNull(message = "Las horas trabajadas no pueden estar vacías.")
    @Min(value = 0, message = "Las horas trabajadas deben ser un número positivo.")
    private Integer horasTrabajadas;

    @NotNull(message = "Las bonificaciones no pueden estar vacías.")
    @DecimalMin(value = "0.0", inclusive = true, message = "Las bonificaciones deben ser un valor positivo o cero.")
    private BigDecimal bonificaciones;

    // Campo para el salario base del cargo, no es persistido directamente en Nomina
    // pero es necesario para el cálculo del salario neto.
    // No requiere validación @NotNull aquí porque se obtiene del Cargo.
    private BigDecimal salarioBaseCargo;


    public NominaRegistroDTO() {
    }

    // Constructor para registro
    public NominaRegistroDTO(String cedulaEmpleado, LocalDate fechaPago, Integer horasTrabajadas, BigDecimal bonificaciones) {
        this.cedulaEmpleado = cedulaEmpleado;
        this.fechaPago = fechaPago;
        this.horasTrabajadas = horasTrabajadas;
        this.bonificaciones = bonificaciones;
    }

    // Constructor para edición (incluye ID)
    public NominaRegistroDTO(Integer idNomina, String cedulaEmpleado, LocalDate fechaPago, Integer horasTrabajadas, BigDecimal bonificaciones) {
        this.idNomina = idNomina;
        this.cedulaEmpleado = cedulaEmpleado;
        this.fechaPago = fechaPago;
        this.horasTrabajadas = horasTrabajadas;
        this.bonificaciones = bonificaciones;
    }

    // Getters y Setters
    public Integer getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(Integer idNomina) {
        this.idNomina = idNomina;
    }

    public String getCedulaEmpleado() {
        return cedulaEmpleado;
    }

    public void setCedulaEmpleado(String cedulaEmpleado) {
        this.cedulaEmpleado = cedulaEmpleado;
    }

    public LocalDate getFechaPago() {
        return fechaPago;
    }

    public void setFechaPago(LocalDate fechaPago) {
        this.fechaPago = fechaPago;
    }

    public Integer getHorasTrabajadas() {
        return horasTrabajadas;
    }

    public void setHorasTrabajadas(Integer horasTrabajadas) {
        this.horasTrabajadas = horasTrabajadas;
    }

    public BigDecimal getBonificaciones() {
        return bonificaciones;
    }

    public void setBonificaciones(BigDecimal bonificaciones) {
        this.bonificaciones = bonificaciones;
    }

    public BigDecimal getSalarioBaseCargo() {
        return salarioBaseCargo;
    }

    public void setSalarioBaseCargo(BigDecimal salarioBaseCargo) {
        this.salarioBaseCargo = salarioBaseCargo;
    }
}