package com.practica.practica.model;

import jakarta.persistence.*; // O javax.persistence.* si usas una versión antigua de Spring Boot/JPA

@Entity // Indica que esta clase es una Entidad JPA y se mapea a una tabla de DB
@Table(name = "Plato") // Opcional: especifica el nombre de la tabla si es diferente al nombre de la clase
public class Plato {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la autogeneración del ID por la DB
    private Long id;

    @Column(name = "Nombre_Plato", nullable = false, length = 100) // Mapea a una columna 'nombre', no nula, max 100 chars
    private String Nombre_Plato;

    @Column(name = "Descripcion", nullable = false, unique = true) // Mapea a una columna 'Descripcion', no nula, única
    private String Descripcion;

    @Column(name = "Precio") // Mapea a una columna 'Precio'
    private double Precio; // Usamos double para permitir valores nulos si es opcional

    // --- Constructores ---
    // JPA requiere un constructor sin argumentos
    public Plato() {
    }

    // Constructor útil para crear objetos Plato
    public Plato(String Nombre_Plato, String Descripcion, double Precio) {
        this.Nombre_Plato = Nombre_Plato;
        this.Descripcion = Descripcion;
        this.Precio = Precio;
    }

    // --- Getters y Setters (Necesarios para JPA y para acceder/modificar los datos) ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre_Plato() {
        return Nombre_Plato;
    }

    public void setNombre_Plato(String Nombre_Plato) {
        this.Nombre_Plato = Nombre_Plato;
    }

    public String getDescripcion() {
        return Descripcion;
    }

    public void setDescripcion(String Descripcion) {
        this.Descripcion = Descripcion;
    }

    public double getPrecio () {
        return Precio;
    }

    public void setPrecio(double Precio) {
        this.Precio = Precio;
    }

    // Opcional: toString(), equals(), hashCode() para depuración y comparación
}