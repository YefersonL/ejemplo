package com.practica.practica.model;

import jakarta.persistence.CascadeType; // Importa CascadeType
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany; // Importa OneToMany

import java.util.ArrayList; // Para inicializar listas
import java.util.List;     // Para usar List

@Entity
public class Plato {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombre_Plato;
    private String descripcion;
    private double precio;

    // --- CONEXIÓN UNO A MUCHO CON DETALLE_ORDEN ---
    // Un Plato puede estar en muchos Detalle_Orden
    // mappedBy="plato" indica que el campo 'plato' en la entidad Detalle_Orden
    // es el dueño de la relación (el que tiene la clave foránea).
    // CascadeType.ALL significa que las operaciones (guardar, eliminar) en Plato
    // se propagarán a sus Detalle_Orden asociados.
    // orphanRemoval=true asegura que si un Detalle_Orden se desvincula de un Plato
    // (o el Plato se elimina), ese Detalle_Orden también se elimine.
    /**
    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Detalle_Orden> detallesOrden = new ArrayList<>(); // Inicializa la lista para evitar NullPointer

    // --- CONEXIÓN UNO A MUCHO CON PLATO_INGREDIENTE ---
    // Un Plato tiene muchos Plato_Ingrediente
    // mappedBy="plato" indica que el campo 'plato' en la entidad Plato_Ingrediente
    // es el dueño de la relación.
    // CascadeType.ALL y orphanRemoval=true son buenas prácticas aquí también.
    @OneToMany(mappedBy = "plato", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Plato_Ingrediente> platosIngredientes = new ArrayList<>(); // Inicializa la lista
**/
    // Constructores
    public Plato() {
    }

    public Plato(String nombre_Plato, String descripcion, double precio) {
        this.nombre_Plato = nombre_Plato;
        this.descripcion = descripcion;
        this.precio = precio;
    }

    // Getters y Setters para todos los campos, incluyendo las nuevas listas
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_Plato() {
        return nombre_Plato;
    }

    public void setNombre_Plato(String nombre_Plato) {
        this.nombre_Plato = nombre_Plato;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public double getPrecio() {
        return precio;
    }

    public void setPrecio(double precio) {
        this.precio = precio;
    }
/**
    // Getters y Setters para las relaciones
    public List<Detalle_Orden> getDetallesOrden() {
        return detallesOrden;
    }

    public void setDetallesOrden(List<Detalle_Orden> detallesOrden) {
        this.detallesOrden = detallesOrden;
    }

    public List<Plato_Ingrediente> getPlatosIngredientes() {
        return platosIngredientes;
    }

    public void setPlatosIngredientes(List<Plato_Ingrediente> platosIngredientes) {
        this.platosIngredientes = platosIngredientes;
    }


    // --- Métodos de ayuda para mantener la bidireccionalidad de las relaciones (OPCIONAL PERO RECOMENDADO) ---
    // Estos métodos ayudan a asegurar que ambos lados de la relación (Plato y Detalle_Orden/Plato_Ingrediente)
    // estén sincronizados cuando añades o quitas elementos.

    public void addDetalleOrden(Detalle_Orden detalleOrden) {
        this.detallesOrden.add(detalleOrden);
        detalleOrden.setPlato(this); // Establece la referencia al Plato en el Detalle_Orden
    }

    public void removeDetalleOrden(Detalle_Orden detalleOrden) {
        this.detallesOrden.remove(detalleOrden);
        detalleOrden.setPlato(null); // Desvincula el Detalle_Orden del Plato
    }

    public void addPlatoIngrediente(Plato_Ingrediente platoIngrediente) {
        this.platosIngredientes.add(platoIngrediente);
        platoIngrediente.setPlato(this); // Establece la referencia al Plato en el Plato_Ingrediente
    }

    public void removePlatoIngrediente(Plato_Ingrediente platoIngrediente) {
        this.platosIngredientes.remove(platoIngrediente);
        platoIngrediente.setPlato(null); // Desvincula el Plato_Ingrediente del Plato
    }
 **/

    @Override
    public String toString() {
        return "Plato{" +
                "id=" + id +
                ", nombre_Plato='" + nombre_Plato + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", precio=" + precio +
                // No incluimos las listas en toString para evitar bucles infinitos si las otras entidades también tienen toString con Plato
                '}';
    }
}