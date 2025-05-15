package com.practica.practica.model;

import jakarta.persistence.*; // O javax.persistence.* si usas una versión antigua de Spring Boot/JPA

@Entity // Indica que esta clase es una Entidad JPA y se mapea a una tabla de DB
@Table(name = "usuarios") // Opcional: especifica el nombre de la tabla si es diferente al nombre de la clase
public class Usuario {

    @Id // Indica que este campo es la clave primaria
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Configura la autogeneración del ID por la DB
    private Long id;

    @Column(name = "nombre", nullable = false, length = 100) // Mapea a una columna 'nombre', no nula, max 100 chars
    private String nombre;

    @Column(name = "email", nullable = false, unique = true) // Mapea a una columna 'email', no nula, única
    private String email;

    @Column(name = "edad") // Mapea a una columna 'edad'
    private Integer edad; // Usamos Integer para permitir valores nulos si es opcional

    // --- Constructores ---
    // JPA requiere un constructor sin argumentos
    public Usuario() {
    }

    // Constructor útil para crear objetos Usuario
    public Usuario(String nombre, String email, Integer edad) {
        this.nombre = nombre;
        this.email = email;
        this.edad = edad;
    }

    // --- Getters y Setters (Necesarios para JPA y para acceder/modificar los datos) ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getEdad() {
        return edad;
    }

    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    // Opcional: toString(), equals(), hashCode() para depuración y comparación
}