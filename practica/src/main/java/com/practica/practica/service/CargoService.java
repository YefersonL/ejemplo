package com.practica.practica.service;

import com.practica.practica.model.Cargo1; // <-- Importa Cargo1
import com.practica.practica.model.CargoRegistroDTO;
import com.practica.practica.repository.CargoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal; // Si estás usando BigDecimal
import java.util.List;
import java.util.Optional;

@Service
public class CargoService {

    private final CargoRepository cargoRepository; // El repositorio ya es de Cargo1

    @Autowired
    public CargoService(CargoRepository cargoRepository) {
        this.cargoRepository = cargoRepository;
    }

    public Cargo1 registrarNuevoCargo(CargoRegistroDTO datosRegistro) { // Retorna Cargo1
        Cargo1 nuevoCargo = new Cargo1(); // Crea una instancia de Cargo1
        nuevoCargo.setNombre_Cargo(datosRegistro.getNombreCargo());
        nuevoCargo.setSalario_Base(datosRegistro.getSalarioBase());
        return cargoRepository.save(nuevoCargo);
    }

    public List<Cargo1> obtenerTodosLosCargos() { // Retorna List de Cargo1
        return cargoRepository.findAll();
    }

    public Optional<Cargo1> obtenerCargoPorId(Long id) { // Retorna Optional de Cargo1
        return cargoRepository.findById(id);
    }

    public boolean eliminarCargoPorId(Long id) {
        if (cargoRepository.existsById(id)) {
            cargoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Cargo1 actualizarCargo(Long id, CargoRegistroDTO datosActualizacion) { // Retorna Cargo1
        Optional<Cargo1> cargoExistenteOptional = cargoRepository.findById(id); // Optional de Cargo1

        if (cargoExistenteOptional.isPresent()) {
            Cargo1 cargoExistente = cargoExistenteOptional.get(); // Instancia de Cargo1
            cargoExistente.setNombre_Cargo(datosActualizacion.getNombreCargo());
            cargoExistente.setSalario_Base(datosActualizacion.getSalarioBase());
            return cargoRepository.save(cargoExistente);
        } else {
            return null; // O lanzar una excepción
        }
    }
}