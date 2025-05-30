package com.practica.practica.service;

import com.practica.practica.model.Sede;
import com.practica.practica.model.SedeRegistroDTO;
import com.practica.practica.repository.SedeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SedeService {

    private final SedeRepository sedeRepository;

    @Autowired
    public SedeService(SedeRepository sedeRepository) {
        this.sedeRepository = sedeRepository;
    }

    public Sede registrarNuevaSede(SedeRegistroDTO datosRegistro) {
        Sede nuevaSede = new Sede();
        nuevaSede.setNombre_Sede(datosRegistro.getNombreSede());
        nuevaSede.setDireccion(datosRegistro.getDireccion());
        return sedeRepository.save(nuevaSede);
    }

    public List<Sede> obtenerTodasLasSedes() {
        return sedeRepository.findAll();
    }

    public Optional<Sede> obtenerSedePorId(Integer id) {
        return sedeRepository.findById(id);
    }

    public boolean eliminarSedePorId(Integer id) {
        if (sedeRepository.existsById(id)) {
            sedeRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Sede actualizarSede(Integer id, SedeRegistroDTO datosActualizacion) {
        Optional<Sede> sedeExistenteOptional = sedeRepository.findById(id);

        if (sedeExistenteOptional.isPresent()) {
            Sede sedeExistente = sedeExistenteOptional.get();
            sedeExistente.setNombre_Sede(datosActualizacion.getNombreSede());
            sedeExistente.setDireccion(datosActualizacion.getDireccion());
            return sedeRepository.save(sedeExistente);
        } else {
            return null; // O lanzar una excepción
        }
    }
}