package com.practica.practica.model; // O en un paquete `com.practica.practica.dto`

import java.math.BigDecimal; // Si estás usando BigDecimal para el salario

public class CargoRegistroDTO {

    private String nombreCargo;
    private BigDecimal salarioBase; // Asegúrate de que coincida con Cargo1.java (double o BigDecimal)

    public CargoRegistroDTO() {
    }

    public CargoRegistroDTO(String nombreCargo, BigDecimal salarioBase) { // O double
        this.nombreCargo = nombreCargo;
        this.salarioBase = salarioBase;
    }

    public String getNombreCargo() {
        return nombreCargo;
    }

    public void setNombreCargo(String nombreCargo) {
        this.nombreCargo = nombreCargo;
    }

    public BigDecimal getSalarioBase() { // O double
        return salarioBase;
    }

    public void setSalarioBase(BigDecimal salarioBase) { // O double
        this.salarioBase = salarioBase;
    }
}