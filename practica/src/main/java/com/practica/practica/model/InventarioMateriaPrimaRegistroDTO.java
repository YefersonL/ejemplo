package com.practica.practica.model; // Considera mover DTOs a un paquete 'com.practica.practica.dto'

import jakarta.validation.constraints.NotNull; // Para validación de campos no nulos
import jakarta.validation.constraints.DecimalMin; // Para validar valores mínimos para BigDecimal

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class InventarioMateriaPrimaRegistroDTO {

    @NotNull(message = "El ID de la materia prima no puede ser nulo.")
    private Integer idMateriaPrima; // Para relacionar con MateriaPrima

    @NotNull(message = "La cantidad disponible no puede ser nula.")
    @DecimalMin(value = "0.00", inclusive = true, message = "La cantidad disponible debe ser mayor o igual a 0.")
    private BigDecimal cantidadDisponible;

    // No se incluye fechaUltimaActualizacion aquí, ya que se generará en el servicio.
    // Para edición, se podría pasar la fecha existente o dejar que el servicio la actualice.

    public InventarioMateriaPrimaRegistroDTO() {
    }

    public InventarioMateriaPrimaRegistroDTO(Integer idMateriaPrima, BigDecimal cantidadDisponible) {
        this.idMateriaPrima = idMateriaPrima;
        this.cantidadDisponible = cantidadDisponible;
    }

    // Getters y Setters
    public Integer getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(Integer idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

    public BigDecimal getCantidadDisponible() {
        return cantidadDisponible;
    }

    public void setCantidadDisponible(BigDecimal cantidadDisponible) {
        this.cantidadDisponible = cantidadDisponible;
    }
}