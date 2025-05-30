package com.practica.practica.controller;

import com.practica.practica.model.Restaurante;
import com.practica.practica.model.RestauranteRegistroDTO;
import com.practica.practica.service.RestauranteService;
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
public class RestauranteController {

    private final RestauranteService restauranteService;

    @Autowired
    public RestauranteController(RestauranteService restauranteService) {
        this.restauranteService = restauranteService;
    }

    // Listar todos los restaurantes
    @GetMapping("/restaurantes/lista")
    public String listarRestaurantes(Model model) {
        List<Restaurante> restaurantes = restauranteService.obtenerTodosLosRestaurantes();
        model.addAttribute("restaurantes", restaurantes);
        return "lista-restaurantes"; // Nombre de la plantilla HTML
    }

    // Mostrar formulario para registrar un nuevo restaurante
    @GetMapping("/restaurantes/registro")
    public String mostrarFormularioRegistroRestaurante(Model model) {
        model.addAttribute("datosFormularioRestaurante", new RestauranteRegistroDTO());
        return "formulario-restaurante-registro"; // Nombre de la plantilla HTML
    }

    // Procesar el registro de un nuevo restaurante
    @PostMapping("/restaurantes/guardar")
    public String guardarRestaurante(@ModelAttribute("datosFormularioRestaurante") RestauranteRegistroDTO datosRegistro,
                                     RedirectAttributes redirectAttributes) {
        try {
            restauranteService.registrarNuevoRestaurante(datosRegistro);
            redirectAttributes.addFlashAttribute("mensaje", "Restaurante registrado exitosamente!");
            return "redirect:/restaurantes/lista";
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el restaurante: " + e.getMessage());
            return "redirect:/restaurantes/registro";
        }
    }

    // Mostrar formulario para editar un restaurante existente
    @GetMapping("/restaurantes/editar/{RUT}")
    public String mostrarFormularioEditarRestaurante(@PathVariable("RUT") String RUT,
                                                     Model model,
                                                     RedirectAttributes redirectAttributes) {
        Optional<Restaurante> restauranteOptional = restauranteService.obtenerRestaurantePorRUT(RUT);

        if (restauranteOptional.isPresent()) {
            Restaurante restaurante = restauranteOptional.get();
            RestauranteRegistroDTO dto = new RestauranteRegistroDTO(restaurante.getRUT(), restaurante.getNombre());
            model.addAttribute("datosFormularioRestaurante", dto);
            model.addAttribute("restauranteRUT", RUT); // Para la acción del formulario
            return "formulario-restaurante-editar";
        } else {
            redirectAttributes.addFlashAttribute("error", "Restaurante no encontrado para editar.");
            return "redirect:/restaurantes/lista";
        }
    }

    // Procesar la actualización de un restaurante
    @PostMapping("/restaurantes/actualizar/{RUT}")
    public String actualizarRestaurante(@PathVariable("RUT") String RUT,
                                        @ModelAttribute("datosFormularioRestaurante") RestauranteRegistroDTO datosActualizacion,
                                        RedirectAttributes redirectAttributes) {
        Restaurante restauranteActualizado = restauranteService.actualizarRestaurante(RUT, datosActualizacion);

        if (restauranteActualizado != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Restaurante actualizado exitosamente!");
            return "redirect:/restaurantes/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el restaurante con RUT " + RUT + ". Posiblemente no existe.");
            return "redirect:/restaurantes/editar/" + RUT;
        }
    }

    // Eliminar un restaurante
    @PostMapping("/restaurantes/eliminar/{RUT}") // Usar POST para eliminación es una buena práctica
    public String eliminarRestaurante(@PathVariable("RUT") String RUT, RedirectAttributes redirectAttributes) {
        boolean eliminada = restauranteService.eliminarRestaurantePorRUT(RUT);
        if (eliminada) {
            redirectAttributes.addFlashAttribute("mensaje", "Restaurante eliminado exitosamente!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el restaurante con RUT " + RUT + ". Posiblemente no existe.");
        }
        return "redirect:/restaurantes/lista";
    }
}