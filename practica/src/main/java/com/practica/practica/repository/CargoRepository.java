package com.practica.practica.repository;

import com.practica.practica.model.Cargo1; // <-- Importa Cargo1
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CargoRepository extends JpaRepository<Cargo1, Long> { // <-- Usa Cargo1 aquí
    // JpaRepository<TipoDeEntidad, TipoDelIdDeEntidad>
}