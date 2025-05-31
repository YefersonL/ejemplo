package com.practica.practica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.DayOfWeek; // Para representar el día de la semana
import java.time.LocalTime; // Para representar la hora

@Entity
@Table(name = "Horario") // Asegúrate de que el nombre de la tabla en tu DB sea este
public class Horario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_horario") // Nombre de la columna para la clave primaria de Horario
    private Long id_Horario;

    @ManyToOne // Un horario pertenece a un Empleado
    @JoinColumn(name = "cedula_empleado", referencedColumnName = "Cedula", nullable = false)
    // 'cedula_empleado' es el nombre de la columna FK en la tabla Horario
    // 'Cedula' es el nombre de la columna PK en la tabla Empleado
    private Empleado empleado;

    @Column(name = "dia_semana", nullable = false, length = 15)
    // Podrías usar un enum para DayOfWeek, pero String es más simple para empezar
    private String diaSemana;

    @Column(name = "hora_entrada", nullable = false)
    private LocalTime horaEntrada;

    @Column(name = "hora_salida", nullable = false)
    private LocalTime horaSalida;

    // Constructores
    public Horario() {
    }

    public Horario(Empleado empleado, String diaSemana, LocalTime horaEntrada, LocalTime horaSalida) {
        this.empleado = empleado;
        this.diaSemana = diaSemana;
        this.horaEntrada = horaEntrada;
        this.horaSalida = horaSalida;
    }

    // Getters y Setters
    public Long getId_Horario() {
        return id_Horario;
    }

    public void setId_Horario(Long id_Horario) {
        this.id_Horario = id_Horario;
    }

    public Empleado getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Empleado empleado) {
        this.empleado = empleado;
    }

    public String getDiaSemana() {
        return diaSemana;
    }

    public void setDiaSemana(String diaSemana) {
        this.diaSemana = diaSemana;
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

    @Override
    public String toString() {
        return "Horario{" +
                "id_Horario=" + id_Horario +
                ", empleadoCedula='" + (empleado != null ? empleado.getCedula() : "N/A") + '\'' +
                ", diaSemana='" + diaSemana + '\'' +
                ", horaEntrada=" + horaEntrada +
                ", horaSalida=" + horaSalida +
                '}';
    }
}