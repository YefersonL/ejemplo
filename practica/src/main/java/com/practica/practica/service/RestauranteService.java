package com.practica.practica.service;

import com.practica.practica.model.Restaurante;
import com.practica.practica.model.RestauranteRegistroDTO;
import com.practica.practica.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RestauranteService {

    private final RestauranteRepository restauranteRepository;

    @Autowired
    public RestauranteService(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    public Restaurante registrarNuevoRestaurante(RestauranteRegistroDTO datosRegistro) {
        // En este caso, el RUT viene en el DTO, así que lo usamos directamente
        Restaurante nuevoRestaurante = new Restaurante();
        nuevoRestaurante.setRUT(datosRegistro.getRUT()); // Establecer el RUT
        nuevoRestaurante.setNombre(datosRegistro.getNombre());
        return restauranteRepository.save(nuevoRestaurante);
    }

    public List<Restaurante> obtenerTodosLosRestaurantes() {
        return restauranteRepository.findAll();
    }

    public Optional<Restaurante> obtenerRestaurantePorRUT(String RUT) {
        return restauranteRepository.findById(RUT);
    }

    public boolean eliminarRestaurantePorRUT(String RUT) {
        if (restauranteRepository.existsById(RUT)) {
            restauranteRepository.deleteById(RUT);
            return true;
        }
        return false;
    }

    public Restaurante actualizarRestaurante(String RUT, RestauranteRegistroDTO datosActualizacion) {
        Optional<Restaurante> restauranteExistenteOptional = restauranteRepository.findById(RUT);

        if (restauranteExistenteOptional.isPresent()) {
            Restaurante restauranteExistente = restauranteExistenteOptional.get();
            restauranteExistente.setNombre(datosActualizacion.getNombre());
            // No actualizamos el RUT si es la clave primaria y no debería cambiar
            return restauranteRepository.save(restauranteExistente);
        } else {
            return null; // O lanzar una excepción ResourceNotFoundException
        }
    }
}