package com.practica.practica.repository;

import com.practica.practica.model.PQRS;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface PQRSRepository extends JpaRepository<PQRS, Long> {
    List<PQRS> findByRestauranteRUT(String rut);
    List<PQRS> findByTipoSolicitud(String tipoSolicitud);

    @Query("SELECT p FROM PQRS p WHERE p.fecha BETWEEN :fechaInicio AND :fechaFin")
    List<PQRS> findByFechaBetween(@Param("fechaInicio") LocalDate fechaInicio,
                                  @Param("fechaFin") LocalDate fechaFin);

    @Query("SELECT COUNT(p) FROM PQRS p WHERE p.restaurante.RUT = :rut")
    long countByRestaurante(@Param("rut") String rut);

    List<PQRS> findByDescripcionContainingIgnoreCase(String keyword);

    // Versión corregida del método problemático:
    @Query("SELECT CASE WHEN COUNT(p) > 0 THEN true ELSE false END FROM PQRS p WHERE p.idSolicitud = :id AND p.restaurante.RUT = :rut")
    boolean existsByIdAndRestauranteRUT(@Param("id") Long id, @Param("rut") String rut);
}