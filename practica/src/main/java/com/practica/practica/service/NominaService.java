package com.practica.practica.service;

import com.practica.practica.model.Empleado;
import com.practica.practica.model.Nomina;
import com.practica.practica.model.NominaRegistroDTO;
import com.practica.practica.repository.NominaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Optional;

@Service
public class NominaService {

    private final NominaRepository nominaRepository;
    private final EmpleadoService empleadoService; // Necesitamos el servicio de empleado para obtener los datos del empleado

    @Autowired
    public NominaService(NominaRepository nominaRepository, EmpleadoService empleadoService) {
        this.nominaRepository = nominaRepository;
        this.empleadoService = empleadoService;
    }

    @Transactional
    public Nomina registrarNuevaNomina(NominaRegistroDTO datosRegistro) {
        Empleado empleado = empleadoService.obtenerEmpleadoPorCedula(datosRegistro.getCedulaEmpleado())
                .orElseThrow(() -> new RuntimeException("Empleado no encontrado con cédula: " + datosRegistro.getCedulaEmpleado()));

        // Calcular el salario neto
        BigDecimal salarioPorHora = empleado.getCargo().getSalario_Base().divide(BigDecimal.valueOf(160), 2, RoundingMode.HALF_UP); // Asumiendo 160 horas al mes
        BigDecimal salarioCalculadoPorHoras = salarioPorHora.multiply(BigDecimal.valueOf(datosRegistro.getHorasTrabajadas()));
        BigDecimal salarioNeto = salarioCalculadoPorHoras.add(datosRegistro.getBonificaciones());

        Nomina nuevaNomina = new Nomina();
        nuevaNomina.setEmpleado(empleado);
        nuevaNomina.setFechaPago(datosRegistro.getFechaPago());
        nuevaNomina.setHorasTrabajadas(datosRegistro.getHorasTrabajadas());
        nuevaNomina.setBonificaciones(datosRegistro.getBonificaciones());
        nuevaNomina.setSalarioNeto(salarioNeto);

        return nominaRepository.save(nuevaNomina);
    }

    public List<Nomina> obtenerTodasLasNominas() {
        return nominaRepository.findAll();
    }

    public Optional<Nomina> obtenerNominaPorId(Integer id) {
        return nominaRepository.findById(id);
    }

    @Transactional
    public Nomina actualizarNomina(Integer id, NominaRegistroDTO datosActualizacion) {
        Optional<Nomina> nominaExistenteOptional = nominaRepository.findById(id);

        if (nominaExistenteOptional.isPresent()) {
            Nomina nominaExistente = nominaExistenteOptional.get();

            // Obtener el empleado actualizado
            Empleado empleado = empleadoService.obtenerEmpleadoPorCedula(datosActualizacion.getCedulaEmpleado())
                    .orElseThrow(() -> new RuntimeException("Empleado no encontrado con cédula: " + datosActualizacion.getCedulaEmpleado()));

            // Recalcular el salario neto
            BigDecimal salarioPorHora = empleado.getCargo().getSalario_Base().divide(BigDecimal.valueOf(160), 2, RoundingMode.HALF_UP); // Asumiendo 160 horas al mes
            BigDecimal salarioCalculadoPorHoras = salarioPorHora.multiply(BigDecimal.valueOf(datosActualizacion.getHorasTrabajadas()));
            BigDecimal salarioNeto = salarioCalculadoPorHoras.add(datosActualizacion.getBonificaciones());

            nominaExistente.setEmpleado(empleado);
            nominaExistente.setFechaPago(datosActualizacion.getFechaPago());
            nominaExistente.setHorasTrabajadas(datosActualizacion.getHorasTrabajadas());
            nominaExistente.setBonificaciones(datosActualizacion.getBonificaciones());
            nominaExistente.setSalarioNeto(salarioNeto);

            return nominaRepository.save(nominaExistente);
        } else {
            return null; // O lanzar una excepción ResourceNotFoundException
        }
    }

    public boolean eliminarNominaPorId(Integer id) {
        if (nominaRepository.existsById(id)) {
            nominaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}