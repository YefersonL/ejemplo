package com.practica.practica.service;

import com.practica.practica.model.Area;
import com.practica.practica.model.Cargo1;
import com.practica.practica.model.Empleado;
import com.practica.practica.model.EmpleadoRegistroDTO;
import com.practica.practica.model.Restaurante;
import com.practica.practica.model.Sede;
import com.practica.practica.repository.EmpleadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class EmpleadoService {

    private final EmpleadoRepository empleadoRepository;
    private final RestauranteService restauranteService;
    private final SedeService sedeService;
    private final CargoService cargoService;
    private final AreaService areaService; // Inyectar AreaService

    @Autowired
    public EmpleadoService(EmpleadoRepository empleadoRepository,
                           RestauranteService restauranteService,
                           SedeService sedeService,
                           CargoService cargoService,
                           AreaService areaService) { // Añadir AreaService al constructor
        this.empleadoRepository = empleadoRepository;
        this.restauranteService = restauranteService;
        this.sedeService = sedeService;
        this.cargoService = cargoService;
        this.areaService = areaService;
    }

    @Transactional
    public Empleado registrarNuevoEmpleado(EmpleadoRegistroDTO datosRegistro) {
        // Validar si la cédula ya existe
        if (empleadoRepository.existsById(datosRegistro.getCedula())) {
            throw new RuntimeException("La cédula " + datosRegistro.getCedula() + " ya está registrada.");
        }

        // Obtener las entidades relacionadas por sus IDs
        Restaurante restaurante = restauranteService.obtenerRestaurantePorRUT(datosRegistro.getRutRestaurante())
                .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con RUT: " + datosRegistro.getRutRestaurante()));

        Sede sede = sedeService.obtenerSedePorId(datosRegistro.getIdSede())
                .orElseThrow(() -> new RuntimeException("Sede no encontrada con ID: " + datosRegistro.getIdSede()));

        Cargo1 cargo = cargoService.obtenerCargoPorId(datosRegistro.getIdCargo())
                .orElseThrow(() -> new RuntimeException("Cargo no encontrado con ID: " + datosRegistro.getIdCargo()));

        Area area = areaService.obtenerAreaPorId(datosRegistro.getIdArea()) // Obtener el área
                .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + datosRegistro.getIdArea()));

        // Crear el objeto Empleado
        Empleado nuevoEmpleado = new Empleado();
        nuevoEmpleado.setCedula(datosRegistro.getCedula());
        nuevoEmpleado.setNombres(datosRegistro.getNombres());
        nuevoEmpleado.setNumeroContacto(datosRegistro.getNumeroContacto());
        nuevoEmpleado.setCorreoCorporativo(datosRegistro.getCorreoCorporativo());
        nuevoEmpleado.setRestaurante(restaurante);
        nuevoEmpleado.setSede(sede);
        nuevoEmpleado.setCargo(cargo);
        nuevoEmpleado.setArea(area); // Asignar el área

        return empleadoRepository.save(nuevoEmpleado);
    }

    public List<Empleado> obtenerTodosLosEmpleados() {
        // Usa findAll para traerlos todos y como se usa FetchType.LAZY en las relaciones
        // JPA puede cargar las entidades relacionadas solo cuando se acceden.
        // Si necesitas todas las relaciones cargadas inmediatamente (EAGER), considera usar un JOIN FETCH en un método personalizado del repositorio
        // o asegúrate de que el contexto de la transacción persista al acceder a ellas.
        return empleadoRepository.findAll();
    }

    public Optional<Empleado> obtenerEmpleadoPorCedula(String cedula) {
        return empleadoRepository.findById(cedula);
    }

    @Transactional
    public Empleado actualizarEmpleado(String cedula, EmpleadoRegistroDTO datosActualizacion) {
        Optional<Empleado> empleadoExistenteOptional = empleadoRepository.findById(cedula);

        if (empleadoExistenteOptional.isPresent()) {
            Empleado empleadoExistente = empleadoExistenteOptional.get();

            // Obtener las entidades relacionadas actualizadas
            Restaurante restaurante = restauranteService.obtenerRestaurantePorRUT(datosActualizacion.getRutRestaurante())
                    .orElseThrow(() -> new RuntimeException("Restaurante no encontrado con RUT: " + datosActualizacion.getRutRestaurante()));

            Sede sede = sedeService.obtenerSedePorId(datosActualizacion.getIdSede())
                    .orElseThrow(() -> new RuntimeException("Sede no encontrada con ID: " + datosActualizacion.getIdSede()));

            Cargo1 cargo = cargoService.obtenerCargoPorId(datosActualizacion.getIdCargo())
                    .orElseThrow(() -> new RuntimeException("Cargo no encontrado con ID: " + datosActualizacion.getIdCargo()));

            Area area = areaService.obtenerAreaPorId(datosActualizacion.getIdArea()) // Obtener el área
                    .orElseThrow(() -> new RuntimeException("Área no encontrada con ID: " + datosActualizacion.getIdArea()));

            // Actualizar los campos del empleado existente (excepto la cédula que es PK)
            empleadoExistente.setNombres(datosActualizacion.getNombres());
            empleadoExistente.setNumeroContacto(datosActualizacion.getNumeroContacto());
            empleadoExistente.setCorreoCorporativo(datosActualizacion.getCorreoCorporativo());
            empleadoExistente.setRestaurante(restaurante);
            empleadoExistente.setSede(sede);
            empleadoExistente.setCargo(cargo);
            empleadoExistente.setArea(area); // Asignar el área

            return empleadoRepository.save(empleadoExistente);
        } else {
            return null; // O lanzar una excepción ResourceNotFoundException
        }
    }

    public boolean eliminarEmpleadoPorCedula(String cedula) {
        if (empleadoRepository.existsById(cedula)) {
            empleadoRepository.deleteById(cedula);
            return true;
        }
        return false;
    }
}