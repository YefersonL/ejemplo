package com.practica.practica.repository;

import com.practica.practica.model.Nomina;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NominaRepository extends JpaRepository<Nomina, Integer> {
    // Puedes añadir métodos de consulta personalizados si los necesitas,
    // por ejemplo, List<Nomina> findByEmpleadoCedula(String cedulaEmpleado);
}