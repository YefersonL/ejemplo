// src/main/java/com/practica/practica.repository/AreaRepository.java
package com.practica.practica.repository;

import com.practica.practica.model.Area;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AreaRepository extends JpaRepository<Area, Integer> {
    // JpaRepository<TipoDeEntidad, TipoDelIdDeEntidad>
}