package com.practica.practica.service;

import com.practica.practica.model.*;
import com.practica.practica.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class PQRSService {

    private final PQRSRepository pqrsRepository;
    private final RestauranteRepository restauranteRepository;

    @Autowired
    public PQRSService(PQRSRepository pqrsRepository,
                       RestauranteRepository restauranteRepository) {
        this.pqrsRepository = pqrsRepository;
        this.restauranteRepository = restauranteRepository;
    }

    @Transactional
    public PQRS registrarNuevaPQRS(PQRSRegistroDTO pqrsRegistroDTO) {
        Restaurante restaurante = restauranteRepository.findById(pqrsRegistroDTO.getRut())
                .orElseThrow(() -> new IllegalArgumentException("No existe ningún restaurante con RUT: " + pqrsRegistroDTO.getRut()));

        LocalDate fecha = Optional.ofNullable(pqrsRegistroDTO.getFecha())
                .orElse(LocalDate.now());

        PQRS pqrs = new PQRS(
                restaurante,
                pqrsRegistroDTO.getTipoSolicitud(),
                pqrsRegistroDTO.getDescripcion(),
                fecha
        );

        return pqrsRepository.save(pqrs);
    }

    public List<PQRS> obtenerTodasLasPQRS() {
        return pqrsRepository.findAll();
    }

    public Optional<PQRS> obtenerPQRSPorId(Long id) {
        return pqrsRepository.findById(id);
    }

    @Transactional
    public boolean eliminarPQRSPorId(Long id) {
        if (pqrsRepository.existsById(id)) {
            pqrsRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional
    public PQRS actualizarPQRS(Long id, PQRSRegistroDTO datosActualizacion) {
        return pqrsRepository.findById(id).map(pqrs -> {
            // Validar datos requeridos
            if (datosActualizacion.getTipoSolicitud() == null) {
                throw new IllegalArgumentException("Tipo de solicitud es requerido");
            }

            // Actualizar restaurante si cambió el RUT
            if (datosActualizacion.getRut() != null &&
                    !datosActualizacion.getRut().equals(pqrs.getRestaurante().getRUT())) {
                Restaurante nuevoRestaurante = restauranteRepository.findById(datosActualizacion.getRut())
                        .orElseThrow(() -> new IllegalArgumentException("No existe ningún restaurante con RUT: " + datosActualizacion.getRut()));
                pqrs.setRestaurante(nuevoRestaurante);
            }

            // Actualizar otros campos
            pqrs.setTipoSolicitud(datosActualizacion.getTipoSolicitud());
            pqrs.setDescripcion(datosActualizacion.getDescripcion());

            if (datosActualizacion.getFecha() != null) {
                pqrs.setFecha(datosActualizacion.getFecha());
            }

            return pqrsRepository.save(pqrs);
        }).orElseThrow(() -> new IllegalArgumentException("PQRS no encontrada con ID: " + id));
    }

    public List<PQRS> obtenerPQRSPorRestaurante(String rutRestaurante) {
        return pqrsRepository.findByRestauranteRUT(rutRestaurante);
    }
}