package com.practica.practica.service;

import com.practica.practica.model.Plato;
import com.practica.practica.model.PlatoRegistroDTO;
import com.practica.practica.repository.PlatoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PlatoService {

    private final PlatoRepository platoRepository;

    @Autowired
    public PlatoService(PlatoRepository platoRepository) {
        this.platoRepository = platoRepository;
    }

    public Plato registrarNuevoPlato(PlatoRegistroDTO datosRegistro) {
        Plato nuevoPlato = new Plato();
        nuevoPlato.setNombre_Plato(datosRegistro.getNombrePlato());
        nuevoPlato.setDescripcion(datosRegistro.getDescripcion());
        nuevoPlato.setPrecio(datosRegistro.getPrecio());
        // `categoria` y `disponible` fueron eliminados

        return platoRepository.save(nuevoPlato);
    }

    public List<Plato> obtenerTodosLosPlatos() {
        return platoRepository.findAll();
    }

    public boolean eliminarPlatoPorId(Long id) {
        if (platoRepository.existsById(id)) {
            platoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Método para obtener un plato por ID (ya lo teníamos, ¡es clave para editar!)
    public Optional<Plato> obtenerPlatoPorId(Long id) {
        return platoRepository.findById(id);
    }

    // --- NUEVO MÉTODO PARA ACTUALIZAR ---
    // Usaremos el mismo DTO de registro para recibir los datos actualizados
    // y luego actualizaremos el objeto Plato existente.
    public Plato actualizarPlato(Long id, PlatoRegistroDTO datosActualizacion) {
        // 1. Buscar el plato existente por su ID
        Optional<Plato> platoExistenteOptional = platoRepository.findById(id);

        if (platoExistenteOptional.isPresent()) {
            Plato platoExistente = platoExistenteOptional.get();

            // 2. Actualizar los campos del plato existente con los datos del DTO
            platoExistente.setNombre_Plato(datosActualizacion.getNombrePlato());
            platoExistente.setDescripcion(datosActualizacion.getDescripcion());
            platoExistente.setPrecio(datosActualizacion.getPrecio());
            // No hay `categoria` ni `disponible`

            // 3. Guardar (actualizar) el plato en la base de datos
            return platoRepository.save(platoExistente);
        } else {
            // Manejar el caso donde el plato no se encuentra (lanzar excepción, retornar null, etc.)
            // Por simplicidad, aquí retornamos null. En un caso real, podrías lanzar una `ResourceNotFoundException`.
            return null;
        }
    }
}