package com.practica.practica.controller;

import com.practica.practica.model.MateriaPrima;
import com.practica.practica.model.MateriaPrimaRegistroDTO;
import com.practica.practica.service.MateriaPrimaService;
import jakarta.validation.Valid; // Para usar @Valid en el DTO
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult; // Para manejar resultados de validación
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
public class MateriaPrimaController {

    private final MateriaPrimaService materiaPrimaService;

    @Autowired
    public MateriaPrimaController(MateriaPrimaService materiaPrimaService) {
        this.materiaPrimaService = materiaPrimaService;
    }

    // Mostrar lista de materias primas
    @GetMapping("/materias-primas/lista")
    public String listarMateriasPrimas(Model model) {
        List<MateriaPrima> materiasPrimas = materiaPrimaService.obtenerTodasLasMateriasPrimas();
        model.addAttribute("materiasPrimas", materiasPrimas);
        return "lista-materias-primas"; // Nombre de la plantilla HTML
    }

    // Mostrar formulario de registro de materia prima
    @GetMapping("/materias-primas/registro")
    public String mostrarFormularioRegistroMateriaPrima(Model model) {
        model.addAttribute("datosFormularioMateriaPrima", new MateriaPrimaRegistroDTO());
        return "formulario-materia-prima-registro"; // Nombre de la plantilla HTML
    }

    // Procesar el registro de una nueva materia prima
    @PostMapping("/materias-primas/guardar")
    public String guardarMateriaPrima(@Valid @ModelAttribute("datosFormularioMateriaPrima") MateriaPrimaRegistroDTO datosRegistro,
                                      BindingResult result,
                                      RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error de validación: " + result.getFieldError().getDefaultMessage());
            return "formulario-materia-prima-registro"; // Volver al formulario si hay errores
        }
        try {
            materiaPrimaService.registrarNuevaMateriaPrima(datosRegistro);
            redirectAttributes.addFlashAttribute("mensaje", "Materia prima registrada exitosamente!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la materia prima: " + e.getMessage());
        }
        return "redirect:/materias-primas/lista";
    }

    // Mostrar formulario de edición de materia prima
    @GetMapping("/materias-primas/editar/{id}")
    public String mostrarFormularioEditarMateriaPrima(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<MateriaPrima> materiaPrimaOptional = materiaPrimaService.obtenerMateriaPrimaPorId(id);
        if (materiaPrimaOptional.isPresent()) {
            MateriaPrima materiaPrima = materiaPrimaOptional.get();
            MateriaPrimaRegistroDTO dto = new MateriaPrimaRegistroDTO(materiaPrima.getNombre(), materiaPrima.getUnidadMedida());
            model.addAttribute("materiaPrimaId", id); // Para el action del formulario
            model.addAttribute("datosFormularioMateriaPrima", dto);
            return "formulario-materia-prima-editar"; // Nombre de la plantilla HTML
        } else {
            redirectAttributes.addFlashAttribute("error", "Materia prima no encontrada para editar.");
            return "redirect:/materias-primas/lista";
        }
    }

    // Procesar la actualización de una materia prima
    @PostMapping("/materias-primas/actualizar/{id}")
    public String actualizarMateriaPrima(@PathVariable("id") Integer id,
                                         @Valid @ModelAttribute("datosFormularioMateriaPrima") MateriaPrimaRegistroDTO datosActualizacion,
                                         BindingResult result,
                                         RedirectAttributes redirectAttributes) {
        if (result.hasErrors()) {
            redirectAttributes.addFlashAttribute("error", "Error de validación: " + result.getFieldError().getDefaultMessage());
            return "formulario-materia-prima-editar"; // Volver al formulario si hay errores
        }
        MateriaPrima materiaPrimaActualizada = materiaPrimaService.actualizarMateriaPrima(id, datosActualizacion);
        if (materiaPrimaActualizada != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Materia prima actualizada exitosamente!");
            return "redirect:/materias-primas/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la materia prima con ID " + id + ". Posiblemente no existe.");
            return "redirect:/materias-primas/editar/" + id;
        }
    }

    // Eliminar una materia prima
    @PostMapping("/materias-primas/eliminar/{id}") // Usar POST para eliminación es una buena práctica
    public String eliminarMateriaPrima(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean eliminada = materiaPrimaService.eliminarMateriaPrimaPorId(id);
        if (eliminada) {
            redirectAttributes.addFlashAttribute("mensaje", "Materia prima eliminada exitosamente!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la materia prima con ID " + id + ". Posiblemente no existe o tiene dependencias.");
        }
        return "redirect:/materias-primas/lista";
    }
}