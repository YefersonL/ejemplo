package com.practica.practica.service;

import com.practica.practica.model.Empleado;
import com.practica.practica.model.Horario;
import com.practica.practica.model.HorarioRegistroDTO;
import com.practica.practica.repository.HorarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioService {

    private final HorarioRepository horarioRepository;
    private final EmpleadoService empleadoService; // Necesitamos EmpleadoService para buscar empleados

    @Autowired
    public HorarioService(HorarioRepository horarioRepository, EmpleadoService empleadoService) {
        this.horarioRepository = horarioRepository;
        this.empleadoService = empleadoService;
    }

    @Transactional
    public Horario guardarHorario(HorarioRegistroDTO horarioDTO) {
        // Buscar el empleado por su cédula
        Optional<Empleado> empleadoOptional = empleadoService.obtenerEmpleadoPorCedula(horarioDTO.getCedulaEmpleado());

        if (empleadoOptional.isEmpty()) {
            throw new RuntimeException("Empleado con cédula " + horarioDTO.getCedulaEmpleado() + " no encontrado.");
        }

        Empleado empleado = empleadoOptional.get();

        Horario horario = new Horario();
        // Si el DTO tiene un ID, es una actualización (esto es para un "upsert")
        if (horarioDTO.getIdHorario() != null) {
            horario.setId_Horario(horarioDTO.getIdHorario()); // CORREGIDO: Usar setId_Horario()
        }
        horario.setEmpleado(empleado);
        horario.setDiaSemana(horarioDTO.getDia()); // CORREGIDO: Usar setDiaSemana()
        horario.setHoraEntrada(horarioDTO.getHoraEntrada());
        horario.setHoraSalida(horarioDTO.getHoraSalida());

        return horarioRepository.save(horario);
    }

    @Transactional(readOnly = true)
    public List<Horario> obtenerTodosLosHorarios() {
        return horarioRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Horario> obtenerHorarioPorId(Long id) {
        return horarioRepository.findById(id);
    }

    @Transactional
    public Horario actualizarHorario(Long id, HorarioRegistroDTO datosActualizacion) {
        return horarioRepository.findById(id).map(horarioExistente -> {
            // Buscar el empleado por su cédula (por si cambia en la actualización)
            Optional<Empleado> empleadoOptional = empleadoService.obtenerEmpleadoPorCedula(datosActualizacion.getCedulaEmpleado());

            if (empleadoOptional.isEmpty()) {
                throw new RuntimeException("Empleado con cédula " + datosActualizacion.getCedulaEmpleado() + " no encontrado para actualizar horario.");
            }
            horarioExistente.setEmpleado(empleadoOptional.get());
            horarioExistente.setDiaSemana(datosActualizacion.getDia()); // CORREGIDO: Usar setDiaSemana()
            horarioExistente.setHoraEntrada(datosActualizacion.getHoraEntrada());
            horarioExistente.setHoraSalida(datosActualizacion.getHoraSalida());
            return horarioRepository.save(horarioExistente);
        }).orElse(null); // O podrías lanzar una excepción si no se encuentra
    }

    @Transactional
    public boolean eliminarHorarioPorId(Long id) {
        if (horarioRepository.existsById(id)) {
            horarioRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @Transactional(readOnly = true)
    public List<Horario> obtenerHorariosPorCedulaEmpleado(String cedulaEmpleado) {
        // Asumiendo que HorarioRepository tiene un método findByEmpleadoCedula
        // Si no lo tienes, deberás agregarlo a HorarioRepository
        return horarioRepository.findByEmpleadoCedula(cedulaEmpleado);
    }
}