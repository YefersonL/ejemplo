package com.practica.practica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany; // Importar OneToMany
import jakarta.persistence.Table;
import java.util.HashSet; // Opcional, para Set
import java.util.Set; // Opcional, para Set
// import java.util.ArrayList; // Opcional, para List
// import java.util.List; // Opcional, para List

@Entity
@Table(name = "Empleado")
public class Empleado {

    @Id // Cedula es la clave primaria y no es autogenerada
    @Column(name = "Cedula", length = 20)
    private String cedula;

    @Column(name = "Nombres", nullable = false, length = 100)
    private String nombres;

    @Column(name = "Numero_Contacto", length = 20)
    private String numeroContacto;

    @Column(name = "Correo_Corporativo", length = 100)
    private String correoCorporativo;

    // Relación Many-to-One con Restaurante
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RUT", nullable = false) // FK en Empleado
    private Restaurante restaurante;

    // Relación Many-to-One con Sede
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Sede", nullable = false) // FK en Empleado
    private Sede sede;

    // Relación Many-to-One con Cargo1 (asegúrate de usar Cargo1, no Cargo)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Cargo", nullable = false) // FK en Empleado
    private Cargo1 cargo; // Asegúrate de que el tipo sea Cargo1

    // Relación Many-to-One con Area
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ID_Area", nullable = false) // FK en Empleado
    private Area area; // Campo para la relación con Area

    // --- Nueva relación: Uno a Muchos con Horario ---
    @OneToMany(mappedBy = "empleado", fetch = FetchType.LAZY)
    // 'mappedBy' indica que la propiedad 'empleado' en la clase Horario es la que
    // posee la relación (contiene la clave foránea).
    // FetchType.LAZY es generalmente recomendado para colecciones grandes para evitar
    // cargar todos los horarios cuando solo necesitas el empleado.
    private Set<Horario> horarios = new HashSet<>(); // Usa Set para evitar duplicados, o List si el orden importa

    // Constructores
    public Empleado() {
    }

    public Empleado(String cedula, String nombres, String numeroContacto, String correoCorporativo,
                    Restaurante restaurante, Sede sede, Cargo1 cargo, Area area) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.numeroContacto = numeroContacto;
        this.correoCorporativo = correoCorporativo;
        this.restaurante = restaurante;
        this.sede = sede;
        this.cargo = cargo;
        this.area = area;
    }

    // --- Constructor actualizado para incluir horarios (opcional, pero útil) ---
    public Empleado(String cedula, String nombres, String numeroContacto, String correoCorporativo,
                    Restaurante restaurante, Sede sede, Cargo1 cargo, Area area, Set<Horario> horarios) {
        this.cedula = cedula;
        this.nombres = nombres;
        this.numeroContacto = numeroContacto;
        this.correoCorporativo = correoCorporativo;
        this.restaurante = restaurante;
        this.sede = sede;
        this.cargo = cargo;
        this.area = area;
        if (horarios != null) {
            this.horarios = horarios;
        }
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

    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public Sede getSede() {
        return sede;
    }

    public void setSede(Sede sede) {
        this.sede = sede;
    }

    public Cargo1 getCargo() {
        return cargo;
    }

    public void setCargo(Cargo1 cargo) {
        this.cargo = cargo;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    // --- Nuevo Getter y Setter para la colección de Horarios ---
    public Set<Horario> getHorarios() {
        return horarios;
    }

    public void setHorarios(Set<Horario> horarios) {
        this.horarios = horarios;
    }

    // --- Métodos de conveniencia para manejar la colección (buenas prácticas) ---
    public void addHorario(Horario horario) {
        this.horarios.add(horario);
        horario.setEmpleado(this); // Asegura la bidireccionalidad de la relación
    }

    public void removeHorario(Horario horario) {
        this.horarios.remove(horario);
        horario.setEmpleado(null); // Desvincula el horario del empleado
    }


    @Override
    public String toString() {
        return "Empleado{" +
                "cedula='" + cedula + '\'' +
                ", nombres='" + nombres + '\'' +
                ", numeroContacto='" + numeroContacto + '\'' +
                ", correoCorporativo='" + correoCorporativo + '\'' +
                ", restaurante=" + (restaurante != null ? restaurante.getNombre() : "N/A") +
                ", sede=" + (sede != null ? sede.getNombre_Sede() : "N/A") +
                ", cargo=" + (cargo != null ? cargo.getNombre_Cargo() : "N/A") +
                ", area=" + (area != null ? area.getNombre_Area() : "N/A") +
                ", numHorarios=" + (horarios != null ? horarios.size() : 0) + // Agregado para ver el número de horarios
                '}';
    }
}