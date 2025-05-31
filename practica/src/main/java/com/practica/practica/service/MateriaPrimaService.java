package com.practica.practica.service;

import com.practica.practica.model.MateriaPrima;
import com.practica.practica.model.MateriaPrimaRegistroDTO;
import com.practica.practica.repository.MateriaPrimaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MateriaPrimaService {

    private final MateriaPrimaRepository materiaPrimaRepository;

    @Autowired
    public MateriaPrimaService(MateriaPrimaRepository materiaPrimaRepository) {
        this.materiaPrimaRepository = materiaPrimaRepository;
    }

    public MateriaPrima registrarNuevaMateriaPrima(MateriaPrimaRegistroDTO datosRegistro) {
        MateriaPrima nuevaMateriaPrima = new MateriaPrima();
        nuevaMateriaPrima.setNombre(datosRegistro.getNombre());
        nuevaMateriaPrima.setUnidadMedida(datosRegistro.getUnidadMedida());
        return materiaPrimaRepository.save(nuevaMateriaPrima);
    }

    public List<MateriaPrima> obtenerTodasLasMateriasPrimas() {
        return materiaPrimaRepository.findAll();
    }

    public Optional<MateriaPrima> obtenerMateriaPrimaPorId(Integer id) {
        return materiaPrimaRepository.findById(id);
    }

    public boolean eliminarMateriaPrimaPorId(Integer id) {
        if (materiaPrimaRepository.existsById(id)) {
            materiaPrimaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public MateriaPrima actualizarMateriaPrima(Integer id, MateriaPrimaRegistroDTO datosActualizacion) {
        Optional<MateriaPrima> materiaPrimaExistenteOptional = materiaPrimaRepository.findById(id);

        if (materiaPrimaExistenteOptional.isPresent()) {
            MateriaPrima materiaPrimaExistente = materiaPrimaExistenteOptional.get();
            materiaPrimaExistente.setNombre(datosActualizacion.getNombre());
            materiaPrimaExistente.setUnidadMedida(datosActualizacion.getUnidadMedida());
            return materiaPrimaRepository.save(materiaPrimaExistente);
        } else {
            return null; // O lanzar una excepción personalizada
        }
    }
}