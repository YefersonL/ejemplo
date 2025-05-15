package com.practica.practica.repository;

import com.practica.practica.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository // Opcional, pero hace explícito el propósito
// Extender JpaRepository le da a esta interfaz métodos CRUD básicos
// <Entidad, Tipo del ID de la Entidad>
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

    // Aquí puedes añadir métodos personalizados si necesitas consultas específicas
    // Spring Data JPA puede generar consultas automáticamente a partir de los nombres de los métodos
    // Ejemplo: buscar un usuario por su email
    // Optional<Usuario> findByEmail(String email);
}