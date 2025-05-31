package com.practica.practica.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.OneToMany;
import jakarta.persistence.CascadeType;
import java.util.List;
import java.util.ArrayList;

@Entity
@Table(name = "Materias_Primas") // Asegúrate de que el nombre de la tabla coincida exactamente con tu DDL
public class MateriaPrima {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ID_Materia_Prima") // Nombre de la columna en la BD
    private Integer idMateriaPrima;

    @Column(name = "Nombre", nullable = false, length = 100) // Nombre de la columna en la BD
    private String nombre;

    @Column(name = "Unidad_Medida", nullable = false, length = 50) // Nombre de la columna en la BD
    private String unidadMedida;

    // Relación One-to-Many con InventarioMateriaPrima
    // mappedBy="materiaPrima" indica que el campo 'materiaPrima' en InventarioMateriaPrima
    // es el dueño de la relación (el que tiene la clave foránea).
    // CascadeType.ALL significa que las operaciones (guardar, eliminar) en MateriaPrima
    // se propagarán a sus InventarioMateriaPrima asociados.
    @OneToMany(mappedBy = "materiaPrima", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<InventarioMateriaPrima> inventario = new ArrayList<>();

    // Constructores
    public MateriaPrima() {
    }

    public MateriaPrima(String nombre, String unidadMedida) {
        this.nombre = nombre;
        this.unidadMedida = unidadMedida;
    }

    // Getters y Setters
    public Integer getIdMateriaPrima() {
        return idMateriaPrima;
    }

    public void setIdMateriaPrima(Integer idMateriaPrima) {
        this.idMateriaPrima = idMateriaPrima;
    }

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

    public List<InventarioMateriaPrima> getInventario() {
        return inventario;
    }

    public void setInventario(List<InventarioMateriaPrima> inventario) {
        this.inventario = inventario;
    }

    // Métodos de conveniencia para manejar la relación bidireccional
    public void addInventario(InventarioMateriaPrima item) {
        this.inventario.add(item);
        item.setMateriaPrima(this);
    }

    public void removeInventario(InventarioMateriaPrima item) {
        this.inventario.remove(item);
        item.setMateriaPrima(null);
    }

    @Override
    public String toString() {
        return "MateriaPrima{" +
                "idMateriaPrima=" + idMateriaPrima +
                ", nombre='" + nombre + '\'' +
                ", unidadMedida='" + unidadMedida + '\'' +
                '}';
    }
}