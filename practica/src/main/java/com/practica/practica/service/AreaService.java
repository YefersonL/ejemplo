// src/main/java/com/practica/practica.service/AreaService.java
package com.practica.practica.service;

import com.practica.practica.model.Area;
import com.practica.practica.model.AreaRegistroDTO;
import com.practica.practica.repository.AreaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AreaService {

    private final AreaRepository areaRepository;

    @Autowired
    public AreaService(AreaRepository areaRepository) {
        this.areaRepository = areaRepository;
    }

    public Area registrarNuevaArea(AreaRegistroDTO datosRegistro) {
        Area nuevaArea = new Area();
        nuevaArea.setNombre_Area(datosRegistro.getNombreArea());
        return areaRepository.save(nuevaArea);
    }

    public List<Area> obtenerTodasLasAreas() {
        return areaRepository.findAll();
    }

    public Optional<Area> obtenerAreaPorId(Integer id) {
        return areaRepository.findById(id);
    }

    public boolean eliminarAreaPorId(Integer id) {
        if (areaRepository.existsById(id)) {
            areaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Area actualizarArea(Integer id, AreaRegistroDTO datosActualizacion) {
        Optional<Area> areaExistenteOptional = areaRepository.findById(id);

        if (areaExistenteOptional.isPresent()) {
            Area areaExistente = areaExistenteOptional.get();
            areaExistente.setNombre_Area(datosActualizacion.getNombreArea());
            return areaRepository.save(areaExistente);
        } else {
            return null; // O lanzar una excepción ResourceNotFoundException
        }
    }
}