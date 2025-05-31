package com.practica.practica.controller;

import com.practica.practica.model.Empleado;
import com.practica.practica.model.Nomina;
import com.practica.practica.model.NominaRegistroDTO;
import com.practica.practica.service.EmpleadoService;
import com.practica.practica.service.NominaService;
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
public class NominaController {

    private final NominaService nominaService;
    private final EmpleadoService empleadoService; // Para poblar el dropdown de empleados

    @Autowired
    public NominaController(NominaService nominaService, EmpleadoService empleadoService) {
        this.nominaService = nominaService;
        this.empleadoService = empleadoService;
    }

    // Listar todas las nóminas
    @GetMapping("/nominas/lista")
    public String listarNominas(Model model) {
        List<Nomina> nominas = nominaService.obtenerTodasLasNominas();
        model.addAttribute("nominas", nominas);
        return "lista-nominas"; // Nombre de la plantilla HTML
    }

    // Mostrar formulario para registrar una nueva nómina
    @GetMapping("/nominas/registro")
    public String mostrarFormularioRegistroNomina(Model model) {
        model.addAttribute("datosFormularioNomina", new NominaRegistroDTO());
        model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados()); // Para el dropdown de empleados
        return "formulario-nomina-registro";
    }

    // Procesar el registro de una nueva nómina
    @PostMapping("/nominas/registro")
    public String registrarNomina(@Valid @ModelAttribute("datosFormularioNomina") NominaRegistroDTO datosRegistro,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        if (result.hasErrors()) {
            // Si hay errores de validación, regresa al formulario con los mensajes y recarga las listas
            model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
            return "formulario-nomina-registro";
        }
        try {
            Nomina nuevaNomina = nominaService.registrarNuevaNomina(datosRegistro);
            redirectAttributes.addFlashAttribute("mensaje", "Nómina registrada exitosamente con ID: " + nuevaNomina.getIdNomina());
            return "redirect:/nominas/lista";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la nómina: " + e.getMessage());
            // Si hay un error, volvemos a cargar los empleados para el formulario
            model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
            return "formulario-nomina-registro"; // Mantener al usuario en la página de registro
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error inesperado al registrar la nómina.");
            model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
            return "formulario-nomina-registro";
        }
    }

    // Mostrar formulario para editar una nómina existente
    @GetMapping("/nominas/editar/{id}")
    public String mostrarFormularioEditarNomina(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Nomina> nominaOptional = nominaService.obtenerNominaPorId(id);
        if (nominaOptional.isPresent()) {
            Nomina nomina = nominaOptional.get();
            // Mapear la entidad Nomina a un DTO para el formulario
            NominaRegistroDTO dto = new NominaRegistroDTO(
                    nomina.getIdNomina(),
                    nomina.getEmpleado().getCedula(),
                    nomina.getFechaPago(),
                    nomina.getHorasTrabajadas(),
                    nomina.getBonificaciones()
            );
            model.addAttribute("datosFormularioNomina", dto);
            model.addAttribute("idNomina", id); // Para usar en la acción del formulario
            model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
            return "formulario-nomina-editar";
        } else {
            redirectAttributes.addFlashAttribute("error", "Nómina no encontrada para editar.");
            return "redirect:/nominas/lista";
        }
    }

    // Procesar la actualización de una nómina
    @PostMapping("/nominas/actualizar/{id}")
    public String actualizarNomina(@PathVariable("id") Integer id,
                                   @Valid @ModelAttribute("datosFormularioNomina") NominaRegistroDTO datosActualizacion,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes,
                                   Model model) { // Añadir Model para recargar listas en caso de error
        if (result.hasErrors()) {
            // Si hay errores de validación, regresa al formulario con los mensajes y recarga las listas
            model.addAttribute("idNomina", id); // Necesario para la URL de acción
            model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
            return "formulario-nomina-editar";
        }
        try {
            Nomina nominaActualizada = nominaService.actualizarNomina(id, datosActualizacion);
            if (nominaActualizada != null) {
                redirectAttributes.addFlashAttribute("mensaje", "Nómina actualizada exitosamente!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Error al actualizar la nómina con ID " + id + ". Posiblemente no existe.");
            }
            return "redirect:/nominas/lista";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la nómina: " + e.getMessage());
            model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
            return "formulario-nomina-editar";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error inesperado al actualizar la nómina.");
            model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
            return "formulario-nomina-editar";
        }
    }

    // Eliminar una nómina
    @PostMapping("/nominas/eliminar/{id}")
    public String eliminarNomina(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean eliminado = nominaService.eliminarNominaPorId(id);
        if (eliminado) {
            redirectAttributes.addFlashAttribute("mensaje", "Nómina eliminada exitosamente!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la nómina con ID " + id + ". Posiblemente no existe.");
        }
        return "redirect:/nominas/lista";
    }
}