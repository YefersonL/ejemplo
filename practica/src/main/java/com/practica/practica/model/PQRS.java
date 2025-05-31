// src/main/java/com/practica/practica/model/PQRS.java
package com.practica.practica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.ManyToOne; // Importar ManyToOne
import jakarta.persistence.JoinColumn; // Importar JoinColumn
import java.time.LocalDate;

@Entity
@Table(name = "PQRS") // Asegúrate de que el nombre de la tabla coincida exactamente con tu DB
public class PQRS {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Asume que ID_Solicitud es autoincremental
    @Column(name = "ID_Solicitud")
    private Long idSolicitud;

    // --- CAMBIO AQUÍ: Relación Many-to-One con Restaurante ---
    @ManyToOne // Muchas PQRS pueden pertenecer a un Restaurante
    @JoinColumn(name = "RUT", referencedColumnName = "RUT", nullable = false)
    // 'name' es la columna FK en la tabla PQRS.
    // 'referencedColumnName' es la columna PK en la tabla Restaurante (en este caso, 'RUT').
    // nullable = false porque tu esquema indica 'RUT' como NN (Not Null).
    private Restaurante restaurante; // La entidad Restaurante a la que pertenece esta PQRS

    @Column(name = "Tipo_Solicitud", length = 50, nullable = false)
    private String tipoSolicitud;

    @Column(name = "Descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "Fecha", nullable = false)
    private LocalDate fecha;

    // Constructor vacío (necesario para JPA)
    public PQRS() {
    }

    // --- CAMBIO AQUÍ: Constructor con el objeto Restaurante ---
    public PQRS(Restaurante restaurante, String tipoSolicitud, String descripcion, LocalDate fecha) {
        this.restaurante = restaurante; // Ahora recibimos el objeto Restaurante
        this.tipoSolicitud = tipoSolicitud;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    // --- Getters y Setters ---

    public Long getIdSolicitud() {
        return idSolicitud;
    }

    public void setIdSolicitud(Long idSolicitud) {
        this.idSolicitud = idSolicitud;
    }

    // --- CAMBIO AQUÍ: Getter y Setter para Restaurante (no para un String RUT simple) ---
    public Restaurante getRestaurante() {
        return restaurante;
    }

    public void setRestaurante(Restaurante restaurante) {
        this.restaurante = restaurante;
    }

    public String getTipoSolicitud() {
        return tipoSolicitud;
    }

    public void setTipoSolicitud(String tipoSolicitud) {
        this.tipoSolicitud = tipoSolicitud;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}