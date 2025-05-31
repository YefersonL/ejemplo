package com.practica.practica.service;

import com.practica.practica.model.Mesa;
import com.practica.practica.model.MesaRegistroDTO;
import com.practica.practica.model.Espacio;
import com.practica.practica.repository.MesaRepository;
import com.practica.practica.repository.EspacioRepository; // Necesitamos el repositorio de Espacio
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MesaService {

    private final MesaRepository mesaRepository;
    private final EspacioRepository espacioRepository; // Inyectamos EspacioRepository

    @Autowired
    public MesaService(MesaRepository mesaRepository, EspacioRepository espacioRepository) {
        this.mesaRepository = mesaRepository;
        this.espacioRepository = espacioRepository;
    }

    public Mesa registrarNuevaMesa(MesaRegistroDTO datosRegistro) {
        Optional<Espacio> espacioOptional = espacioRepository.findById(datosRegistro.getIdEspacio());
        if (espacioOptional.isPresent()) {
            Espacio espacio = espacioOptional.get();
            Mesa nuevaMesa = new Mesa();
            nuevaMesa.setEspacio(espacio); // Establecer el Espacio
            nuevaMesa.setNumero_Mesa(datosRegistro.getNumeroMesa());
            nuevaMesa.setEstado_Mesa(datosRegistro.getEstadoMesa());
            nuevaMesa.setCapacidad_Mesa(datosRegistro.getCapacidadMesa());
            return mesaRepository.save(nuevaMesa);
        }
        return null; // O lanzar una excepción si el Espacio no existe
    }

    public List<Mesa> obtenerTodasLasMesas() {
        return mesaRepository.findAll();
    }

    public Optional<Mesa> obtenerMesaPorId(Integer id) {
        return mesaRepository.findById(id);
    }

    public boolean eliminarMesaPorId(Integer id) {
        if (mesaRepository.existsById(id)) {
            mesaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    public Mesa actualizarMesa(Integer id, MesaRegistroDTO datosActualizacion) {
        Optional<Mesa> mesaExistenteOptional = mesaRepository.findById(id);

        if (mesaExistenteOptional.isPresent()) {
            Mesa mesaExistente = mesaExistenteOptional.get();

            // Actualizar Espacio si se proporciona un nuevo ID de Espacio y es válido
            if (datosActualizacion.getIdEspacio() != null) {
                Optional<Espacio> espacioOptional = espacioRepository.findById(datosActualizacion.getIdEspacio());
                if (espacioOptional.isPresent()) {
                    mesaExistente.setEspacio(espacioOptional.get());
                } else {
                    // Manejar el caso donde el ID de Espacio no es válido
                    return null;
                }
            }

            mesaExistente.setNumero_Mesa(datosActualizacion.getNumeroMesa());
            mesaExistente.setEstado_Mesa(datosActualizacion.getEstadoMesa());
            mesaExistente.setCapacidad_Mesa(datosActualizacion.getCapacidadMesa());
            return mesaRepository.save(mesaExistente);
        }
        return null;
    }
}