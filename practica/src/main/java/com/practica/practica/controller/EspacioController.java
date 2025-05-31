package com.practica.practica.controller;

import com.practica.practica.model.Espacio;
import com.practica.practica.model.EspacioRegistroDTO;
import com.practica.practica.model.Sede; // Importar Sede para el formulario de registro/edición
import com.practica.practica.service.EspacioService;
import com.practica.practica.service.SedeService; // Importar SedeService
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
public class EspacioController {

    private final EspacioService espacioService;
    private final SedeService sedeService; // Inyectar SedeService

    @Autowired
    public EspacioController(EspacioService espacioService, SedeService sedeService) {
        this.espacioService = espacioService;
        this.sedeService = sedeService;
    }

    // Listar todos los espacios
    @GetMapping("/espacios/lista")
    public String listarEspacios(Model model) {
        List<Espacio> espacios = espacioService.obtenerTodosLosEspacios();
        model.addAttribute("espacios", espacios);
        return "lista-espacios"; // Nombre de la plantilla HTML
    }

    // Mostrar formulario para crear un nuevo espacio
    @GetMapping("/espacios/registro")
    public String mostrarFormularioRegistroEspacio(Model model) {
        model.addAttribute("datosFormularioEspacio", new EspacioRegistroDTO());
        model.addAttribute("sedes", sedeService.obtenerTodasLasSedes()); // Para el desplegable de sedes
        return "formulario-espacio-registro"; // Nombre de la plantilla HTML
    }

    // Procesar el registro de un nuevo espacio
    @PostMapping("/espacios/registro")
    public String registrarEspacio(@ModelAttribute("datosFormularioEspacio") EspacioRegistroDTO datosRegistro,
                                   RedirectAttributes redirectAttributes) {
        Espacio nuevoEspacio = espacioService.registrarNuevoEspacio(datosRegistro);
        if (nuevoEspacio != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Espacio registrado exitosamente!");
            return "redirect:/espacios/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al registrar el espacio. Verifica que la sede exista.");
            return "redirect:/espacios/registro";
        }
    }

    // Mostrar formulario para editar un espacio existente
    @GetMapping("/espacios/editar/{id}")
    public String mostrarFormularioEditarEspacio(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Espacio> espacioOptional = espacioService.obtenerEspacioPorId(id);
        if (espacioOptional.isPresent()) {
            Espacio espacio = espacioOptional.get();
            EspacioRegistroDTO dto = new EspacioRegistroDTO(
                    espacio.getSede() != null ? espacio.getSede().getId_Sede() : null, // Obtener el ID de la sede
                    espacio.getNombre_Espacio(),
                    espacio.getTipo_Espacio(),
                    espacio.getCapacidad()
            );
            model.addAttribute("espacioId", id);
            model.addAttribute("datosFormularioEspacio", dto);
            model.addAttribute("sedes", sedeService.obtenerTodasLasSedes()); // Para el desplegable de sedes
            return "formulario-espacio-editar"; // Nombre de la plantilla HTML
        } else {
            redirectAttributes.addFlashAttribute("error", "Espacio no encontrado para editar.");
            return "redirect:/espacios/lista";
        }
    }

    // Procesar la actualización de un espacio
    @PostMapping("/espacios/actualizar/{id}")
    public String actualizarEspacio(@PathVariable("id") Integer id,
                                    @ModelAttribute("datosFormularioEspacio") EspacioRegistroDTO datosActualizacion,
                                    RedirectAttributes redirectAttributes) {
        Espacio espacioActualizado = espacioService.actualizarEspacio(id, datosActualizacion);

        if (espacioActualizado != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Espacio actualizado exitosamente!");
            return "redirect:/espacios/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el espacio con ID " + id + ". Posiblemente no existe o la sede no es válida.");
            return "redirect:/espacios/editar/" + id;
        }
    }

    // Eliminar un espacio
    @GetMapping("/espacios/eliminar/{id}")
    public String eliminarEspacio(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean eliminada = espacioService.eliminarEspacioPorId(id);
        if (eliminada) {
            redirectAttributes.addFlashAttribute("mensaje", "Espacio eliminado exitosamente!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar el espacio con ID " + id + ". Posiblemente no existe.");
        }
        return "redirect:/espacios/lista";
    }
}