package com.practica.practica.repository;

import com.practica.practica.model.Espacio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EspacioRepository extends JpaRepository<Espacio, Integer> {
    // JpaRepository<TipoDeEntidad, TipoDelIdDeEntidad>
}