// src/main/java/com/practica/practica.controller/AreaController.java
package com.practica.practica.controller;

import com.practica.practica.model.Area;
import com.practica.practica.model.AreaRegistroDTO;
import com.practica.practica.service.AreaService;
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
public class AreaController {

    private final AreaService areaService;

    @Autowired
    public AreaController(AreaService areaService) {
        this.areaService = areaService;
    }

    // Listar todas las áreas
    @GetMapping("/areas/lista")
    public String listarAreas(Model model) {
        List<Area> areas = areaService.obtenerTodasLasAreas();
        model.addAttribute("areas", areas);
        return "lista-areas"; // Nombre de la plantilla HTML
    }

    // Mostrar formulario para registrar una nueva área
    @GetMapping("/areas/registro")
    public String mostrarFormularioRegistroArea(Model model) {
        model.addAttribute("datosFormularioArea", new AreaRegistroDTO());
        return "formulario-area-registro"; // Nombre de la plantilla HTML
    }

    // Procesar el registro de una nueva área
    @PostMapping("/areas/guardar")
    public String guardarArea(@ModelAttribute("datosFormularioArea") AreaRegistroDTO datosRegistro,
                              RedirectAttributes redirectAttributes) {
        Area nuevaArea = areaService.registrarNuevaArea(datosRegistro);
        if (nuevaArea != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Área registrada exitosamente!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el área.");
        }
        return "redirect:/areas/lista";
    }

    // Mostrar formulario de edición para un área existente
    @GetMapping("/areas/editar/{id}")
    public String mostrarFormularioEditarArea(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Area> areaOptional = areaService.obtenerAreaPorId(id);
        if (areaOptional.isPresent()) {
            Area area = areaOptional.get();
            AreaRegistroDTO datosFormulario = new AreaRegistroDTO(area.getNombre_Area());
            model.addAttribute("datosFormularioArea", datosFormulario);
            model.addAttribute("areaId", id); // Para usar en la acción del formulario
            return "formulario-area-editar";
        } else {
            redirectAttributes.addFlashAttribute("error", "Área no encontrada para editar.");
            return "redirect:/areas/lista";
        }
    }

    // Procesar la actualización de un área
    @PostMapping("/areas/actualizar/{id}")
    public String actualizarArea(@PathVariable("id") Integer id,
                                 @ModelAttribute("datosFormularioArea") AreaRegistroDTO datosActualizacion,
                                 RedirectAttributes redirectAttributes) {
        Area areaActualizada = areaService.actualizarArea(id, datosActualizacion);

        if (areaActualizada != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Área actualizada exitosamente!");
            return "redirect:/areas/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el área con ID " + id + ". Posiblemente no existe.");
            return "redirect:/areas/editar/" + id;
        }
    }

    // Eliminar un área
    @PostMapping("/areas/eliminar/{id}")
    public String eliminarArea(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean eliminada = areaService.eliminarAreaPorId(id);
        if (eliminada) {
            redirectAttributes.addFlashAttribute("mensaje", "Área eliminada exitosamente!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el área con ID " + id + ". Posiblemente no existe.");
        }
        return "redirect:/areas/lista";
    }
}