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

    // --- MÉTODOS EXISTENTES (con el cambio principal aquí) ---

    // CAMBIO AQUÍ: Cambiado de @GetMapping("/") a @GetMapping("/platos/registro")
    @GetMapping("/platos/registro") // Esta será la URL para acceder al formulario de registro de platos
    public String mostrarFormulario(Model model) {
        model.addAttribute("datosFormulario", new PlatoRegistroDTO());
        System.out.println("Sirviendo formulario.html (ahora para registro de platos) desde templates");
        // Asegúrate de que tu plantilla se llame "formulario-plato-registro.html"
        // para mayor claridad, aunque "formulario.html" funcionará si es el nombre.
        return "formulario-plato-registro"; // Sugerencia de nombre de plantilla
    }

    // Asegúrate de que en tu HTML de registro (formulario-plato-registro.html)
    // el action del formulario apunte a la URL correcta, por ejemplo:
    // <form th:action="@{/platos/guardar-datos}" th:object="${datosFormulario}" method="post">
    @PostMapping("/platos/guardar-datos") // Añadido /platos/ para mayor consistencia y claridad
    public String registrarPlato(@ModelAttribute("datosFormulario") PlatoRegistroDTO datosFormulario,
                                 RedirectAttributes redirectAttributes) {
        System.out.println("Datos recibidos del formulario en POST:");
        System.out.println("Nombre_Plato: " + datosFormulario.getNombrePlato());
        System.out.println("Descripcion: " + datosFormulario.getDescripcion());
        System.out.println("Precio: " + datosFormulario.getPrecio());

        try {
            platoService.registrarNuevoPlato(datosFormulario);
            redirectAttributes.addFlashAttribute("mensaje", "Plato registrado exitosamente!");
            System.out.println("Registro exitoso, redirigiendo a /platos/exito"); // Cambiado URL
            return "redirect:/platos/exito"; // Redirige a una URL específica de platos

        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al registrar el plato: " + e.getMessage());
            System.out.println("Error en el registro, redirigiendo a /platos/error"); // Cambiado URL
            return "redirect:/platos/error"; // Redirige a una URL específica de platos
        }
    }

    // Cambiado de /exito a /platos/exito
    @GetMapping("/platos/exito")
    public String mostrarPaginaExito() {
        System.out.println("Sirviendo exito.html desde templates para platos");
        return "exito"; // Puedes crear un exito-plato.html si quieres uno específico
    }

    // Cambiado de /error a /platos/error
    @GetMapping("/platos/error")
    public String mostrarPaginaError() {
        System.out.println("Sirviendo error.html desde templates para platos");
        return "error"; // Puedes crear un error-plato.html si quieres uno específico
    }

    @GetMapping("/platos/lista") // Esta es la URL para listar platos (también puedes usar /platos/lista)
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
        return "redirect:/platos/lista"; // Redirige a la lista de platos
    }

    // --- MÉTODOS PARA EDITAR (sin cambios de URL aquí, ya eran específicos) ---

    // 1. Mostrar el formulario de edición con los datos del plato existente
    @GetMapping("/platos/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Plato> platoOptional = platoService.obtenerPlatoPorId(id);

        if (platoOptional.isPresent()) {
            Plato plato = platoOptional.get();
            PlatoRegistroDTO datosFormulario = new PlatoRegistroDTO(
                    plato.getNombre_Plato(),
                    plato.getDescripcion(),
                    plato.getPrecio()
            );
            model.addAttribute("platoId", id);
            model.addAttribute("datosFormulario", datosFormulario);
            System.out.println("Sirviendo formulario-editar.html para plato ID: " + id);
            return "formulario-editar";
        } else {
            redirectAttributes.addFlashAttribute("error", "Plato no encontrado para editar.");
            return "redirect:/platos/lista";
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
            return "redirect:/platos/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el plato con ID " + id + ". Posiblemente no existe.");
            System.out.println("Error al actualizar plato con ID " + id + ".");
            return "redirect:/platos/editar/" + id;
        }
    }
}