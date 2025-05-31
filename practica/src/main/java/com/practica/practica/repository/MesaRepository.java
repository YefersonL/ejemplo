package com.practica.practica.repository;

import com.practica.practica.model.Mesa;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MesaRepository extends JpaRepository<Mesa, Integer> {
    // JpaRepository<TipoDeEntidad, TipoDelIdDeEntidad>
}