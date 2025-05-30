package com.practica.practica.controller;

import com.practica.practica.model.Sede;
import com.practica.practica.model.SedeRegistroDTO;
import com.practica.practica.service.SedeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class SedeController {

    private final SedeService sedeService;

    @Autowired
    public SedeController(SedeService sedeService) {
        this.sedeService = sedeService;
    }

    // Listar todas las sedes
    @GetMapping("/sedes/lista")
    public String listarSedes(Model model) {
        List<Sede> sedes = sedeService.obtenerTodasLasSedes();
        model.addAttribute("sedes", sedes);
        return "lista-sedes"; // Nombre de la plantilla HTML
    }

    // Mostrar formulario para crear una nueva sede
    @GetMapping("/sedes/registro")
    public String mostrarFormularioNuevaSede(Model model) {
        model.addAttribute("datosFormularioSede", new SedeRegistroDTO());
        return "formulario-sede-registro"; // Nombre de la plantilla HTML
    }

    // Procesar la creación de una nueva sede
    @PostMapping("/sedes/registro")
    public String crearNuevaSede(@ModelAttribute("datosFormularioSede") SedeRegistroDTO datosRegistro,
                                 RedirectAttributes redirectAttributes) {
        Sede nuevaSede = sedeService.registrarNuevaSede(datosRegistro);
        if (nuevaSede != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Sede creada exitosamente!");
            return "redirect:/sedes/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al crear la sede.");
            return "redirect:/sedes/registro"; // Corrección: Redirigir al formulario de registro en caso de error
        }
    }

    // Mostrar formulario para editar una sede existente
    @GetMapping("/sedes/editar/{id}")
    public String mostrarFormularioEdicionSede(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Sede> sedeOptional = sedeService.obtenerSedePorId(id);

        if (sedeOptional.isPresent()) {
            Sede sede = sedeOptional.get();
            SedeRegistroDTO datosFormulario = new SedeRegistroDTO(
                    sede.getNombre_Sede(),
                    sede.getDireccion()
            );
            model.addAttribute("sedeId", id);
            model.addAttribute("datosFormularioSede", datosFormulario);
            return "formulario-sede-editar"; // Nombre de la plantilla HTML
        } else {
            redirectAttributes.addFlashAttribute("error", "Sede no encontrada para editar.");
            return "redirect:/sedes/lista";
        }
    }

    // Procesar la actualización de una sede
    @PostMapping("/sedes/actualizar/{id}")
    public String actualizarSede(@PathVariable("id") Integer id,
                                 @ModelAttribute("datosFormularioSede") SedeRegistroDTO datosActualizacion,
                                 RedirectAttributes redirectAttributes) {
        Sede sedeActualizada = sedeService.actualizarSede(id, datosActualizacion);

        if (sedeActualizada != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Sede actualizada exitosamente!");
            return "redirect:/sedes/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la sede con ID " + id + ". Posiblemente no existe.");
            return "redirect:/sedes/editar/" + id;
        }
    }

    // Eliminar una sede
    @GetMapping("/sedes/eliminar/{id}")
    public String eliminarSede(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean eliminada = sedeService.eliminarSedePorId(id);
        if (eliminada) {
            redirectAttributes.addFlashAttribute("mensaje", "Sede eliminada exitosamente!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la sede con ID " + id + ". Posiblemente no existe.");
        }
        return "redirect:/sedes/lista";
    }
}