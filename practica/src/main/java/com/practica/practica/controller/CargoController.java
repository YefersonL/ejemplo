package com.practica.practica.controller;

import com.practica.practica.model.Cargo1; // <-- Importa Cargo1
import com.practica.practica.model.CargoRegistroDTO;
import com.practica.practica.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal; // Si estás usando BigDecimal
import java.util.List;
import java.util.Optional;

@Controller
public class CargoController {

    private final CargoService cargoService;

    @Autowired
    public CargoController(CargoService cargoService) {
        this.cargoService = cargoService;
    }

    // Listar todos los cargos
    @GetMapping("/cargos/lista")
    public String listarCargos(Model model) {
        List<Cargo1> cargos = cargoService.obtenerTodosLosCargos(); // Lista de Cargo1
        model.addAttribute("cargos", cargos);
        return "lista-cargos"; // Plantilla HTML
    }

    // Mostrar formulario para editar un cargo existente
    @GetMapping("/cargos/editar/{id}")
    public String mostrarFormularioEdicionCargo(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Cargo1> cargoOptional = cargoService.obtenerCargoPorId(id); // Optional de Cargo1

        if (cargoOptional.isPresent()) {
            Cargo1 cargo = cargoOptional.get(); // Instancia de Cargo1
            CargoRegistroDTO datosFormulario = new CargoRegistroDTO(
                    cargo.getNombre_Cargo(),
                    cargo.getSalario_Base()
            );
            model.addAttribute("cargoId", id);
            model.addAttribute("datosFormularioCargo", datosFormulario);
            return "formulario-cargo-editar"; // Plantilla HTML
        } else {
            redirectAttributes.addFlashAttribute("error", "Cargo no encontrado para editar.");
            return "redirect:/cargos/lista";
        }
    }

    // Procesar la actualización de un cargo
    @PostMapping("/cargos/actualizar/{id}")
    public String actualizarCargo(@PathVariable("id") Long id,
                                  @ModelAttribute("datosFormularioCargo") CargoRegistroDTO datosActualizacion,
                                  RedirectAttributes redirectAttributes) {
        Cargo1 cargoActualizado = cargoService.actualizarCargo(id, datosActualizacion); // Retorna Cargo1

        if (cargoActualizado != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Cargo actualizado exitosamente!");
            return "redirect:/cargos/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el cargo con ID " + id + ". Posiblemente no existe.");
            return "redirect:/cargos/editar/" + id;
        }
    }
}