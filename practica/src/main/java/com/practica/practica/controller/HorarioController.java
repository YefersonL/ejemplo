package com.practica.practica.controller;

import com.practica.practica.model.Empleado;
import com.practica.practica.model.Horario;
import com.practica.practica.model.HorarioRegistroDTO;
import com.practica.practica.service.EmpleadoService;
import com.practica.practica.service.HorarioService;
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
public class HorarioController {

    private final HorarioService horarioService;
    private final EmpleadoService empleadoService; // Para obtener la lista de empleados para los select

    @Autowired
    public HorarioController(HorarioService horarioService, EmpleadoService empleadoService) {
        this.horarioService = horarioService;
        this.empleadoService = empleadoService;
    }

    // Muestra el formulario de registro de un nuevo horario
    @GetMapping("/horarios/registro")
    public String mostrarFormularioRegistro(Model model) {
        model.addAttribute("horario", new HorarioRegistroDTO());
        model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados()); // Para el select de empleados
        model.addAttribute("diasSemana", List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
        return "formulario-horario-registro";
    }

    // Procesa el formulario de registro de un nuevo horario
    @PostMapping("/horarios/registro")
    public String registrarHorario(@Valid @ModelAttribute("horario") HorarioRegistroDTO horarioRegistroDTO,
                                   BindingResult result,
                                   RedirectAttributes redirectAttributes,
                                   Model model) {
        if (result.hasErrors()) {
            model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
            model.addAttribute("diasSemana", List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
            return "formulario-horario-registro";
        }

        try {
            horarioService.guardarHorario(horarioRegistroDTO);
            redirectAttributes.addFlashAttribute("mensaje", "Horario registrado exitosamente!");
            return "redirect:/horarios/lista";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el horario: " + e.getMessage());
            model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
            model.addAttribute("diasSemana", List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
            return "formulario-horario-registro";
        }
    }

    // Muestra la lista de todos los horarios
    @GetMapping("/horarios/lista")
    public String listarHorarios(Model model) {
        List<Horario> horarios = horarioService.obtenerTodosLosHorarios();
        model.addAttribute("horarios", horarios);
        return "lista-horarios";
    }

    // Muestra el formulario para editar un horario existente
    @GetMapping("/horarios/editar/{id}")
    public String mostrarFormularioEditar(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Horario> horarioOptional = horarioService.obtenerHorarioPorId(id);

        if (horarioOptional.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Horario no encontrado con ID: " + id);
            return "redirect:/horarios/lista";
        }

        Horario horario = horarioOptional.get();
        // Mapear la entidad Horario a HorarioRegistroDTO para el formulario
        HorarioRegistroDTO horarioDTO = new HorarioRegistroDTO(
                horario.getId_Horario(), // CORREGIDO: Usar getId_Horario()
                horario.getEmpleado().getCedula(), // Obtener la cédula del empleado
                horario.getDiaSemana(), // CORREGIDO: Usar getDiaSemana()
                horario.getHoraEntrada(),
                horario.getHoraSalida()
        );

        model.addAttribute("horario", horarioDTO);
        model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
        model.addAttribute("diasSemana", List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
        return "formulario-horario-editar";
    }

    // Procesa el formulario de actualización de un horario
    @PostMapping("/horarios/editar/{id}")
    public String actualizarHorario(@PathVariable("id") Long id,
                                    @Valid @ModelAttribute("horario") HorarioRegistroDTO horarioRegistroDTO,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        if (result.hasErrors()) {
            model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
            model.addAttribute("diasSemana", List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
            return "formulario-horario-editar";
        }

        try {
            Horario horarioActualizado = horarioService.actualizarHorario(id, horarioRegistroDTO);
            if (horarioActualizado != null) {
                redirectAttributes.addFlashAttribute("mensaje", "Horario actualizado exitosamente!");
            } else {
                redirectAttributes.addFlashAttribute("error", "Error al actualizar el horario con ID " + id + ". Posiblemente no existe.");
            }
            return "redirect:/horarios/lista";
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el horario: " + e.getMessage());
            model.addAttribute("empleados", empleadoService.obtenerTodosLosEmpleados());
            model.addAttribute("diasSemana", List.of("Lunes", "Martes", "Miércoles", "Jueves", "Viernes", "Sábado", "Domingo"));
            return "formulario-horario-editar";
        }
    }

    // Eliminar un horario
    @PostMapping("/horarios/eliminar/{id}")
    public String eliminarHorario(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean eliminado = horarioService.eliminarHorarioPorId(id);
        if (eliminado) {
            redirectAttributes.addFlashAttribute("mensaje", "Horario eliminado exitosamente!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el horario con ID " + id + ". Posiblemente no existe.");
        }
        return "redirect:/horarios/lista";
    }
}