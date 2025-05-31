package com.practica.practica.controller;

import com.practica.practica.model.Mesa;
import com.practica.practica.model.MesaRegistroDTO;
import com.practica.practica.model.Espacio; // Importar Espacio para el formulario de registro/edición
import com.practica.practica.service.MesaService;
import com.practica.practica.service.EspacioService; // Importar EspacioService
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
public class MesaController {

    private final MesaService mesaService;
    private final EspacioService espacioService; // Inyectar EspacioService

    @Autowired
    public MesaController(MesaService mesaService, EspacioService espacioService) {
        this.mesaService = mesaService;
        this.espacioService = espacioService;
    }

    // Listar todas las mesas
    @GetMapping("/mesas/lista")
    public String listarMesas(Model model) {
        List<Mesa> mesas = mesaService.obtenerTodasLasMesas();
        model.addAttribute("mesas", mesas);
        return "lista-mesas"; // Nombre de la plantilla HTML
    }

    // Mostrar formulario para crear una nueva mesa
    @GetMapping("/mesas/registro")
    public String mostrarFormularioRegistroMesa(Model model) {
        model.addAttribute("datosFormularioMesa", new MesaRegistroDTO());
        model.addAttribute("espacios", espacioService.obtenerTodosLosEspacios()); // Para el desplegable de espacios
        return "formulario-mesa-registro"; // Nombre de la plantilla HTML
    }

    // Procesar el registro de una nueva mesa
    @PostMapping("/mesas/registro")
    public String registrarMesa(@ModelAttribute("datosFormularioMesa") MesaRegistroDTO datosRegistro,
                                RedirectAttributes redirectAttributes) {
        Mesa nuevaMesa = mesaService.registrarNuevaMesa(datosRegistro);
        if (nuevaMesa != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Mesa registrada exitosamente!");
            return "redirect:/mesas/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la mesa. Verifica que el espacio exista.");
            return "redirect:/mesas/registro";
        }
    }

    // Mostrar formulario para editar una mesa existente
    @GetMapping("/mesas/editar/{id}")
    public String mostrarFormularioEditarMesa(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Mesa> mesaOptional = mesaService.obtenerMesaPorId(id);
        if (mesaOptional.isPresent()) {
            Mesa mesa = mesaOptional.get();
            MesaRegistroDTO dto = new MesaRegistroDTO(
                    mesa.getEspacio() != null ? mesa.getEspacio().getId_Espacio() : null, // Obtener el ID del espacio
                    mesa.getNumero_Mesa(),
                    mesa.getEstado_Mesa(),
                    mesa.getCapacidad_Mesa()
            );
            model.addAttribute("mesaId", id);
            model.addAttribute("datosFormularioMesa", dto);
            model.addAttribute("espacios", espacioService.obtenerTodosLosEspacios()); // Para el desplegable de espacios
            return "formulario-mesa-editar"; // Nombre de la plantilla HTML
        } else {
            redirectAttributes.addFlashAttribute("error", "Mesa no encontrada para editar.");
            return "redirect:/mesas/lista";
        }
    }

    // Procesar la actualización de una mesa
    @PostMapping("/mesas/actualizar/{id}")
    public String actualizarMesa(@PathVariable("id") Integer id,
                                 @ModelAttribute("datosFormularioMesa") MesaRegistroDTO datosActualizacion,
                                 RedirectAttributes redirectAttributes) {
        Mesa mesaActualizada = mesaService.actualizarMesa(id, datosActualizacion);

        if (mesaActualizada != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Mesa actualizada exitosamente!");
            return "redirect:/mesas/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la mesa con ID " + id + ". Posiblemente no existe o el espacio no es válido.");
            return "redirect:/mesas/editar/" + id;
        }
    }

    // Eliminar una mesa
    @GetMapping("/mesas/eliminar/{id}")
    public String eliminarMesa(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean eliminada = mesaService.eliminarMesaPorId(id);
        if (eliminada) {
            redirectAttributes.addFlashAttribute("mensaje", "Mesa eliminada exitosamente!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la mesa con ID " + id + ". Posiblemente no existe.");
        }
        return "redirect:/mesas/lista";
    }
}