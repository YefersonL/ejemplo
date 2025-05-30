package com.practica.practica.model; // Puedes ponerlo en com.practica.practica.dto si lo prefieres

public class RestauranteRegistroDTO {

    // No incluir el RUT aquí si es generado automáticamente (pero en tu DDL no lo es, es PK)
    // Para registro y edición, el RUT se pasará como parte del DTO o como PathVariable
    private String RUT; // Para la creación y para identificar en la edición
    private String nombre;

    public RestauranteRegistroDTO() {
    }

    public RestauranteRegistroDTO(String RUT, String nombre) {
        this.RUT = RUT;
        this.nombre = nombre;
    }

    // Getters y Setters
    public String getRUT() {
        return RUT;
    }

    public void setRUT(String RUT) {
        this.RUT = RUT;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}