package com.practica.practica.repository;

import com.practica.practica.model.MateriaPrima;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MateriaPrimaRepository extends JpaRepository<MateriaPrima, Integer> {
    // Métodos CRUD básicos proporcionados por JpaRepository
    // Puedes añadir métodos personalizados si necesitas búsquedas específicas,
    // por ejemplo: Optional<MateriaPrima> findByNombre(String nombre);
}