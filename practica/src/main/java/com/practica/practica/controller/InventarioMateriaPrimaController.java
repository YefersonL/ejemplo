package com.practica.practica.controller;

import com.practica.practica.model.InventarioMateriaPrima;
import com.practica.practica.model.InventarioMateriaPrimaRegistroDTO;
import com.practica.practica.model.MateriaPrima; // Para poblar el dropdown en los formularios
import com.practica.practica.service.InventarioMateriaPrimaService;
import com.practica.practica.service.MateriaPrimaService; // Para obtener la lista de Materias Primas
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
public class InventarioMateriaPrimaController {

    private final InventarioMateriaPrimaService inventarioMateriaPrimaService;
    private final MateriaPrimaService materiaPrimaService; // Para obtener la lista de Materias Primas

    @Autowired
    public InventarioMateriaPrimaController(InventarioMateriaPrimaService inventarioMateriaPrimaService,
                                            MateriaPrimaService materiaPrimaService) {
        this.inventarioMateriaPrimaService = inventarioMateriaPrimaService;
        this.materiaPrimaService = materiaPrimaService;
    }

    // Mostrar lista de entradas de inventario
    @GetMapping("/inventario-materias-primas/lista")
    public String listarInventario(Model model) {
        List<InventarioMateriaPrima> inventario = inventarioMateriaPrimaService.obtenerTodoElInventario();
        model.addAttribute("inventario", inventario);
        return "lista-inventario-materias-primas"; // Nombre de la plantilla HTML
    }

    // Mostrar formulario de registro de entrada de inventario
    @GetMapping("/inventario-materias-primas/registro")
    public String mostrarFormularioRegistroInventario(Model model) {
        model.addAttribute("datosFormularioInventario", new InventarioMateriaPrimaRegistroDTO());
        model.addAttribute("materiasPrimas", materiaPrimaService.obtenerTodasLasMateriasPrimas()); // Para el dropdown
        return "formulario-inventario-materias-primas-registro"; // Nombre de la plantilla HTML
    }

    // Procesar el registro de una nueva entrada de inventario
    @PostMapping("/inventario-materias-primas/guardar")
    public String guardarInventario(@Valid @ModelAttribute("datosFormularioInventario") InventarioMateriaPrimaRegistroDTO datosRegistro,
                                    BindingResult result,
                                    RedirectAttributes redirectAttributes,
                                    Model model) {
        if (result.hasErrors()) {
            model.addAttribute("materiasPrimas", materiaPrimaService.obtenerTodasLasMateriasPrimas()); // Recargar para el dropdown
            return "formulario-inventario-materias-primas-registro"; // Volver al formulario si hay errores
        }
        try {
            inventarioMateriaPrimaService.registrarNuevaEntradaInventario(datosRegistro);
            redirectAttributes.addFlashAttribute("mensaje", "Entrada de inventario registrada exitosamente!");
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Error al registrar la entrada de inventario: " + e.getMessage());
        }
        return "redirect:/inventario-materias-primas/lista";
    }

    // Mostrar formulario de edición de entrada de inventario
    @GetMapping("/inventario-materias-primas/editar/{id}")
    public String mostrarFormularioEditarInventario(@PathVariable("id") Integer id, Model model, RedirectAttributes redirectAttributes) {
        Optional<InventarioMateriaPrima> inventarioOptional = inventarioMateriaPrimaService.obtenerEntradaInventarioPorId(id);
        if (inventarioOptional.isPresent()) {
            InventarioMateriaPrima inventario = inventarioOptional.get();
            // Crear un DTO para pre-cargar los datos del formulario
            InventarioMateriaPrimaRegistroDTO dto = new InventarioMateriaPrimaRegistroDTO(
                    inventario.getMateriaPrima().getIdMateriaPrima(),
                    inventario.getCantidadDisponible()
            );
            model.addAttribute("inventarioId", id); // Para el action del formulario
            model.addAttribute("datosFormularioInventario", dto);
            model.addAttribute("materiasPrimas", materiaPrimaService.obtenerTodasLasMateriasPrimas()); // Para el dropdown
            return "formulario-inventario-materias-primas-editar"; // Nombre de la plantilla HTML
        } else {
            redirectAttributes.addFlashAttribute("error", "Entrada de inventario no encontrada para editar.");
            return "redirect:/inventario-materias-primas/lista";
        }
    }

    // Procesar la actualización de una entrada de inventario
    @PostMapping("/inventario-materias-primas/actualizar/{id}")
    public String actualizarInventario(@PathVariable("id") Integer id,
                                       @Valid @ModelAttribute("datosFormularioInventario") InventarioMateriaPrimaRegistroDTO datosActualizacion,
                                       BindingResult result,
                                       RedirectAttributes redirectAttributes,
                                       Model model) {
        if (result.hasErrors()) {
            model.addAttribute("materiasPrimas", materiaPrimaService.obtenerTodasLasMateriasPrimas()); // Recargar para el dropdown
            return "formulario-inventario-materias-primas-editar"; // Volver al formulario si hay errores
        }
        try {
            InventarioMateriaPrima inventarioActualizado = inventarioMateriaPrimaService.actualizarEntradaInventario(id, datosActualizacion);
            if (inventarioActualizado != null) {
                redirectAttributes.addFlashAttribute("mensaje", "Entrada de inventario actualizada exitosamente!");
                return "redirect:/inventario-materias-primas/lista";
            } else {
                redirectAttributes.addFlashAttribute("error", "Error al actualizar la entrada de inventario con ID " + id + ". Posiblemente no existe.");
                return "redirect:/inventario-materias-primas/editar/" + id;
            }
        } catch (IllegalArgumentException e) {
            redirectAttributes.addFlashAttribute("error", "Error: " + e.getMessage());
            return "redirect:/inventario-materias-primas/editar/" + id;
        }
    }

    // Eliminar una entrada de inventario
    @PostMapping("/inventario-materias-primas/eliminar/{id}")
    public String eliminarInventario(@PathVariable("id") Integer id, RedirectAttributes redirectAttributes) {
        boolean eliminada = inventarioMateriaPrimaService.eliminarEntradaInventarioPorId(id);
        if (eliminada) {
            redirectAttributes.addFlashAttribute("mensaje", "Entrada de inventario eliminada exitosamente!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al eliminar la entrada de inventario con ID " + id + ". Posiblemente no existe.");
        }
        return "redirect:/inventario-materias-primas/lista";
    }
}