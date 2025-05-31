package com.practica.practica.repository;

import com.practica.practica.model.Empleado;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface EmpleadoRepository extends JpaRepository<Empleado, String> {
    // El ID de Empleado es un String (Cédula)

}