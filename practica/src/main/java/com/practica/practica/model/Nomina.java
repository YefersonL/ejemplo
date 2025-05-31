package com.practica.practica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "Nomina")
public class Nomina {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Nomina")
    private Integer idNomina;

    // Relación Many-to-One con Empleado (un empleado tiene muchas nóminas)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "Cedula", nullable = false) // FK en Nomina que referencia la Cedula de Empleado
    private Empleado empleado;

    @Column(name = "Fecha_Pago", nullable = false)
    private LocalDate fechaPago;

    @Column(name = "Horas_Trabajadas", nullable = false)
    private Integer horasTrabajadas;

    @Column(name = "Bonificaciones", nullable = false, precision = 10, scale = 2)
    private BigDecimal bonificaciones;

    @Column(name = "Salario_Neto", nullable = false, precision = 10, scale = 2)
    private BigDecimal salarioNeto; // Campo calculado

    // Constructores
    public Nomina() {
    }

    public Nomina(Empleado empleado, LocalDate fechaPago, Integer horasTrabajadas, BigDecimal bonificaciones, BigDecimal salarioNeto) {
        this.empleado = empleado;
        this.fechaPago = fechaPago;
        this.horasTrabajadas = horasTrabajadas;
        this.bonificaciones = bonificaciones;
        this.salarioNeto = salarioNeto;
    }

    // Getters y Setters
    public Integer getIdNomina() {
        return idNomina;
    }

    public void setIdNomina(Integer idNomina) {
        this.idNomina = idNomina;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
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

    public BigDecimal getSalarioNeto() {
        return salarioNeto;
    }

    public void setSalarioNeto(BigDecimal salarioNeto) {
        this.salarioNeto = salarioNeto;
    }

    @Override
    public String toString() {
        return "Nomina{" +
                "idNomina=" + idNomina +
                ", empleado=" + (empleado != null ? empleado.getNombres() : "N/A") +
                ", fechaPago=" + fechaPago +
                ", horasTrabajadas=" + horasTrabajadas +
                ", bonificaciones=" + bonificaciones +
                ", salarioNeto=" + salarioNeto +
                '}';
    }
}