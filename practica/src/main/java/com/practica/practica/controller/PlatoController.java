package com.practica.practica.controller;

import com.practica.practica.model.Plato;
import com.practica.practica.model.PlatoRegistroDTO;
import com.practica.practica.service.PlatoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional; // Asegúrate de importar Optional

@Controller
public class PlatoController {

    private final PlatoService platoService;

    @Autowired
    public PlatoController(PlatoService platoService) {
        this.platoService = platoService;
    }

    // --- MÉTODOS EXISTENTES (sin cambios) ---
    @GetMapping("/")
    public String mostrarFormulario(Model model) {
        model.addAttribute("datosFormulario", new PlatoRegistroDTO());
        System.out.println("Sirviendo formulario.html desde templates");
        return "formulario";
    }

    @PostMapping("/guardar-datos")
    public String registrarPlato(@ModelAttribute("datosFormulario") PlatoRegistroDTO datosFormulario,
                                 RedirectAttributes redirectAttributes) {
        System.out.println("Datos recibidos del formulario en POST:");
        System.out.println("Nombre_Plato: " + datosFormulario.getNombrePlato());
        System.out.println("Descripcion: " + datosFormulario.getDescripcion());
        System.out.println("Precio: " + datosFormulario.getPrecio());

        try {
            platoService.registrarNuevoPlato(datosFormulario);
            redirectAttributes.addFlashAttribute("mensaje", "Plato registrado exitosamente!");
            System.out.println("Registro exitoso, redirigiendo a /exito");
            return "redirect:/exito";

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al registrar el plato: " + e.getMessage());
            System.out.println("Error en el registro, redirigiendo a /error");
            return "redirect:/error";
        }
    }

    @GetMapping("/exito")
    public String mostrarPaginaExito() {
        System.out.println("Sirviendo exito.html desde templates");
        return "exito";
    }

    @GetMapping("/error")
    public String mostrarPaginaError() {
        System.out.println("Sirviendo error.html desde templates");
        return "error";
    }

    @GetMapping("/platos")
    public String listarPlatos(Model model) {
        List<Plato> platos = platoService.obtenerTodosLosPlatos();
        model.addAttribute("platos", platos);
        System.out.println("Sirviendo lista-platos.html desde templates");
        return "lista-platos";
    }

    @PostMapping("/platos/eliminar/{id}")
    public String eliminarPlato(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        System.out.println("Intentando eliminar plato con ID: " + id);
        boolean eliminado = platoService.eliminarPlatoPorId(id);

        if (eliminado) {
            redirectAttributes.addFlashAttribute("mensaje", "Plato eliminado exitosamente.");
            System.out.println("Plato con ID " + id + " eliminado.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el plato con ID " + id + ". Posiblemente no existe.");
            System.out.println("No se encontró el plato con ID " + id + " para eliminar.");
        }
        return "redirect:/platos";
    }

    // --- NUEVOS MÉTODOS PARA EDITAR ---

    // 1. Mostrar el formulario de edición con los datos del plato existente
    @GetMapping("/platos/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Plato> platoOptional = platoService.obtenerPlatoPorId(id);

        if (platoOptional.isPresent()) {
            Plato plato = platoOptional.get();
            // Creamos un DTO a partir del Plato existente para prellenar el formulario
            PlatoRegistroDTO datosFormulario = new PlatoRegistroDTO(
                    plato.getNombre_Plato(),
                    plato.getDescripcion(),
                    plato.getPrecio()
            );
            model.addAttribute("platoId", id); // Necesitamos el ID para la URL de actualización
            model.addAttribute("datosFormulario", datosFormulario); // El DTO prellenado
            System.out.println("Sirviendo formulario-editar.html para plato ID: " + id);
            return "formulario-editar"; // Nueva plantilla HTML para editar
        } else {
            redirectAttributes.addFlashAttribute("error", "Plato no encontrado para editar.");
            return "redirect:/platos"; // Redirige a la lista si no se encuentra
        }
    }

    // 2. Procesar el envío del formulario de edición
    @PostMapping("/platos/actualizar/{id}")
    public String actualizarPlato(@PathVariable("id") Long id,
                                  @ModelAttribute("datosFormulario") PlatoRegistroDTO datosActualizacion,
                                  RedirectAttributes redirectAttributes) {
        System.out.println("Intentando actualizar plato con ID: " + id);
        System.out.println("Nuevos datos: Nombre=" + datosActualizacion.getNombrePlato() + ", Precio=" + datosActualizacion.getPrecio());

        Plato platoActualizado = platoService.actualizarPlato(id, datosActualizacion);

        if (platoActualizado != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Plato actualizado exitosamente!");
            System.out.println("Plato con ID " + id + " actualizado.");
            return "redirect:/platos"; // Redirige a la lista después de la actualización
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el plato con ID " + id + ". Posiblemente no existe.");
            System.out.println("Error al actualizar plato con ID " + id + ".");
            return "redirect:/platos/editar/" + id; // Vuelve al formulario de edición si falla
        }
    }
}