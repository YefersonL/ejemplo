package com.practica.practica.controller;

import com.practica.practica.model.Area;
import com.practica.practica.model.Cargo1; // Cargo1 según tus archivos
import com.practica.practica.model.Empleado;
import com.practica.practica.model.EmpleadoRegistroDTO;
import com.practica.practica.model.Restaurante;
import com.practica.practica.model.Sede;
import com.practica.practica.service.AreaService;         // Necesitarás crear este servicio
import com.practica.practica.service.CargoService;       // Ya deberías tenerlo
import com.practica.practica.service.EmpleadoService;
import com.practica.practica.service.RestauranteService; // Ya deberías tenerlo
import com.practica.practica.service.SedeService;         // Ya deberías tenerlo
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class EmpleadoController {

    private final EmpleadoService empleadoService;
    private final RestauranteService restauranteService;
    private final SedeService sedeService;
    private final CargoService cargoService;
    private final AreaService areaService; // Inyectar AreaService

    @Autowired
    public EmpleadoController(EmpleadoService empleadoService,
                              RestauranteService restauranteService,
                              SedeService sedeService,
                              CargoService cargoService,
                              AreaService areaService) { // Añadir AreaService al constructor
        this.empleadoService = empleadoService;
        this.restauranteService = restauranteService;
        this.sedeService = sedeService;
        this.cargoService = cargoService;
        this.areaService = areaService;
    }

    // Listar todos los empleados
    @GetMapping("/empleados/lista")
    public String listarEmpleados(Model model) {
        List<Empleado> empleados = empleadoService.obtenerTodosLosEmpleados();
        model.addAttribute("empleados", empleados);
        return "lista-empleados"; // Nombre de la plantilla HTML
    }

    // Mostrar formulario para registrar un nuevo empleado
    @GetMapping("/empleados/registro")
    public String mostrarFormularioRegistroEmpleado(Model model) {
        model.addAttribute("datosFormularioEmpleado", new EmpleadoRegistroDTO());
        model.addAttribute("restaurantes", restauranteService.obtenerTodosLosRestaurantes());
        model.addAttribute("sedes", sedeService.obtenerTodasLasSedes());
        model.addAttribute("cargos", cargoService.obtenerTodosLosCargos());
        model.addAttribute("areas", areaService.obtenerTodasLasAreas()); // Añadir áreas al modelo
        return "formulario-empleado-registro";
    }

    // Procesar el registro de un nuevo empleado
    @PostMapping("/empleados/registro")
    public String registrarEmpleado(@Valid @ModelAttribute("datosFormularioEmpleado") EmpleadoRegistroDTO datosRegistro,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model) { // Añadir Model para recargar listas en caso de error
        if (result.hasErrors()) {
            // Si hay errores de validación, regresa al formulario con los mensajes y recarga las listas
            model.addAttribute("restaurantes", restauranteService.obtenerTodosLosRestaurantes());
            model.addAttribute("sedes", sedeService.obtenerTodasLasSedes());
            model.addAttribute("cargos", cargoService.obtenerTodosLosCargos());
            model.addAttribute("areas", areaService.obtenerTodasLasAreas());
            return "formulario-empleado-registro";
        }
        try {
            Empleado nuevoEmpleado = empleadoService.registrarNuevoEmpleado(datosRegistro);
            redirectAttributes.addFlashAttribute("mensaje", "Empleado registrado exitosamente con cédula: " + nuevoEmpleado.getCedula());
            return "redirect:/empleados/lista";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el empleado: " + e.getMessage());
            return "redirect:/empleados/registro";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error inesperado al registrar el empleado.");
            return "redirect:/empleados/registro";
        }
    }

    // Mostrar formulario para editar un empleado existente
    @GetMapping("/empleados/editar/{cedula}")
    public String mostrarFormularioEditarEmpleado(@PathVariable("cedula") String cedula, Model model, RedirectAttributes redirectAttributes) {
        Optional<Empleado> empleadoOptional = empleadoService.obtenerEmpleadoPorCedula(cedula);
        if (empleadoOptional.isPresent()) {
            Empleado empleado = empleadoOptional.get();
            // Mapear la entidad Empleado a un DTO para el formulario
            EmpleadoRegistroDTO dto = new EmpleadoRegistroDTO(
                    empleado.getCedula(),
                    empleado.getNombres(),
                    empleado.getNumeroContacto(),
                    empleado.getCorreoCorporativo(),
                    empleado.getRestaurante().getRUT(),
                    empleado.getSede().getId_Sede(),
                    empleado.getCargo().getId_Cargo(),
                    empleado.getArea().getId_Area() // Obtener el ID del área
            );
            model.addAttribute("datosFormularioEmpleado", dto);
            model.addAttribute("cedulaEmpleado", cedula); // Para usar en la acción del formulario
            model.addAttribute("restaurantes", restauranteService.obtenerTodosLosRestaurantes());
            model.addAttribute("sedes", sedeService.obtenerTodasLasSedes());
            model.addAttribute("cargos", cargoService.obtenerTodosLosCargos());
            model.addAttribute("areas", areaService.obtenerTodasLasAreas()); // Añadir áreas al modelo
            return "formulario-empleado-editar";
        } else {
            redirectAttributes.addFlashAttribute("error", "Empleado no encontrado para editar.");
            return "redirect:/empleados/lista";
        }
    }

    // Procesar la actualización de un empleado
    @PostMapping("/empleados/actualizar/{cedula}")
    public String actualizarEmpleado(@PathVariable("cedula") String cedula,
                                     @Valid @ModelAttribute("datosFormularioEmpleado") EmpleadoRegistroDTO datosActualizacion,
                                     BindingResult result,
                                     RedirectAttributes redirectAttributes,
                                     Model model) { // Añadir Model para recargar listas en caso de error
        if (result.hasErrors()) {
            // Si hay errores de validación, regresa al formulario con los mensajes y recarga las listas
            model.addAttribute("cedulaEmpleado", cedula); // Necesario para la URL de acción
            model.addAttribute("restaurantes", restauranteService.obtenerTodosLosRestaurantes());
            model.addAttribute("sedes", sedeService.obtenerTodasLasSedes());
            model.addAttribute("cargos", cargoService.obtenerTodosLosCargos());
            model.addAttribute("areas", areaService.obtenerTodasLasAreas());
            return "formulario-empleado-editar";
        }
        try {
            Empleado empleadoActualizado = empleadoService.actualizarEmpleado(cedula, datosActualizacion);
            if (empleadoActualizado != null) {
                redirectAttributes.addFlashAttribute("mensaje", "Empleado actualizado exitosamente!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Error al actualizar el empleado con cédula " + cedula + ". Posiblemente no existe.");
            }
            return "redirect:/empleados/lista";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el empleado: " + e.getMessage());
            return "redirect:/empleados/editar/" + cedula;
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error inesperado al actualizar el empleado.");
            return "redirect:/empleados/editar/" + cedula;
        }
    }

    // Eliminar un empleado
    @PostMapping("/empleados/eliminar/{cedula}")
    public String eliminarEmpleado(@PathVariable("cedula") String cedula, RedirectAttributes redirectAttributes) {
        boolean eliminado = empleadoService.eliminarEmpleadoPorCedula(cedula);
        if (eliminado) {
            redirectAttributes.addFlashAttribute("mensaje", "Empleado eliminado exitosamente!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el empleado con cédula " + cedula + ". Posiblemente no existe.");
        }
        return "redirect:/empleados/lista";
    }
}