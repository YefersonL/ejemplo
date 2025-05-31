package com.practica.practica.service;

import com.practica.practica.model.InventarioMateriaPrima;
import com.practica.practica.model.InventarioMateriaPrimaRegistroDTO;
import com.practica.practica.model.MateriaPrima;
import com.practica.practica.repository.InventarioMateriaPrimaRepository;
import com.practica.practica.repository.MateriaPrimaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.math.BigDecimal;

@Service
public class InventarioMateriaPrimaService {

    private final InventarioMateriaPrimaRepository inventarioMateriaPrimaRepository;
    private final MateriaPrimaRepository materiaPrimaRepository; // Necesario para buscar la MateriaPrima

    @Autowired
    public InventarioMateriaPrimaService(InventarioMateriaPrimaRepository inventarioMateriaPrimaRepository,
                                         MateriaPrimaRepository materiaPrimaRepository) {
        this.inventarioMateriaPrimaRepository = inventarioMateriaPrimaRepository;
        this.materiaPrimaRepository = materiaPrimaRepository;
    }

    public InventarioMateriaPrima registrarNuevaEntradaInventario(InventarioMateriaPrimaRegistroDTO datosRegistro) {
        Optional<MateriaPrima> materiaPrimaOptional = materiaPrimaRepository.findById(datosRegistro.getIdMateriaPrima());

        if (materiaPrimaOptional.isPresent()) {
            MateriaPrima materiaPrima = materiaPrimaOptional.get();
            InventarioMateriaPrima nuevaEntrada = new InventarioMateriaPrima();
            nuevaEntrada.setMateriaPrima(materiaPrima);
            nuevaEntrada.setCantidadDisponible(datosRegistro.getCantidadDisponible());
            nuevaEntrada.setFechaUltimaActualizacion(LocalDateTime.now()); // Establece la fecha actual
            return inventarioMateriaPrimaRepository.save(nuevaEntrada);
        } else {
            // Materia Prima no encontrada
            throw new IllegalArgumentException("Materia Prima con ID " + datosRegistro.getIdMateriaPrima() + " no encontrada.");
        }
    }

    public List<InventarioMateriaPrima> obtenerTodoElInventario() {
        return inventarioMateriaPrimaRepository.findAll();
    }

    public Optional<InventarioMateriaPrima> obtenerEntradaInventarioPorId(Integer id) {
        return inventarioMateriaPrimaRepository.findById(id);
    }

    public boolean eliminarEntradaInventarioPorId(Integer id) {
        if (inventarioMateriaPrimaRepository.existsById(id)) {
            inventarioMateriaPrimaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public InventarioMateriaPrima actualizarEntradaInventario(Integer id, InventarioMateriaPrimaRegistroDTO datosActualizacion) {
        Optional<InventarioMateriaPrima> entradaExistenteOptional = inventarioMateriaPrimaRepository.findById(id);
        Optional<MateriaPrima> materiaPrimaOptional = materiaPrimaRepository.findById(datosActualizacion.getIdMateriaPrima());

        if (entradaExistenteOptional.isPresent() && materiaPrimaOptional.isPresent()) {
            InventarioMateriaPrima entradaExistente = entradaExistenteOptional.get();
            MateriaPrima materiaPrima = materiaPrimaOptional.get();

            entradaExistente.setMateriaPrima(materiaPrima); // Asegura que la relación se actualice si el ID de materia prima cambia
            entradaExistente.setCantidadDisponible(datosActualizacion.getCantidadDisponible());
            entradaExistente.setFechaUltimaActualizacion(LocalDateTime.now()); // Actualiza la fecha al modificar

            return inventarioMateriaPrimaRepository.save(entradaExistente);
        } else {
            return null; // O lanzar una excepción personalizada
        }
    }

    // Métodos para actualizar cantidad (ej: añadir/restar)
    public InventarioMateriaPrima actualizarCantidad(Integer id, BigDecimal cambioCantidad) {
        Optional<InventarioMateriaPrima> entradaOptional = inventarioMateriaPrimaRepository.findById(id);
        if (entradaOptional.isPresent()) {
            InventarioMateriaPrima entrada = entradaOptional.get();
            BigDecimal nuevaCantidad = entrada.getCantidadDisponible().add(cambioCantidad);
            if (nuevaCantidad.compareTo(BigDecimal.ZERO) < 0) {
                throw new IllegalArgumentException("La cantidad no puede ser negativa.");
            }
            entrada.setCantidadDisponible(nuevaCantidad);
            entrada.setFechaUltimaActualizacion(LocalDateTime.now());
            return inventarioMateriaPrimaRepository.save(entrada);
        }
        return null; // O lanzar excepción
    }
}