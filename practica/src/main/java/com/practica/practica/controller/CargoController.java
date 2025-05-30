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

    // Método para mostrar el formulario de registro de un nuevo cargo
    @GetMapping("/cargos/registro") // Mapea la URL para mostrar el formulario
    public String mostrarFormularioRegistroCargo(Model model) {
        model.addAttribute("datosFormularioCargo", new CargoRegistroDTO()); // Añade un nuevo DTO al modelo
        System.out.println("Sirviendo formulario-cargo-registro.html desde templates");
        return "formulario-cargo-registro"; // Retorna el nombre de tu plantilla HTML para el formulario
    }

    // Método para procesar el envío del formulario de registro de un nuevo cargo
    @PostMapping("/cargos/guardar") // Mapea la URL de POST para guardar el cargo
    public String registrarNuevoCargo(@ModelAttribute("datosFormularioCargo") CargoRegistroDTO datosFormulario,
                                      RedirectAttributes redirectAttributes) {
        System.out.println("Datos recibidos del formulario de Cargo en POST:");
        System.out.println("Nombre_Cargo: " + datosFormulario.getNombreCargo());
        System.out.println("Salario_Base: " + datosFormulario.getSalarioBase());

        try {
            cargoService.registrarNuevoCargo(datosFormulario); // Asume que este método existe en CargoService
            redirectAttributes.addFlashAttribute("mensaje", "Cargo registrado exitosamente!");
            System.out.println("Registro de cargo exitoso, redirigiendo a /cargos/lista");
            return "redirect:/cargos/lista"; // Redirige a la lista de cargos
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al registrar el cargo: " + e.getMessage());
            System.out.println("Error en el registro de cargo, redirigiendo al formulario");
            return "redirect:/cargos/registro"; // Redirige de vuelta al formulario con el error
        }
    }


    // Listar todos los cargos
    @GetMapping("/cargos/lista")
    public String listarCargos(Model model) {
        List<Cargo1> cargos = cargoService.obtenerTodosLosCargos();
        model.addAttribute("cargos", cargos);
        return "lista-cargos";
    }

    // Mostrar formulario para editar un cargo existente
    @GetMapping("/cargos/editar/{id}")
    public String mostrarFormularioEdicionCargo(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<Cargo1> cargoOptional = cargoService.obtenerCargoPorId(id);

        if (cargoOptional.isPresent()) {
            Cargo1 cargo = cargoOptional.get();
            CargoRegistroDTO datosFormulario = new CargoRegistroDTO(
                    cargo.getNombre_Cargo(),
                    cargo.getSalario_Base()
            );
            model.addAttribute("cargoId", id);
            model.addAttribute("datosFormularioCargo", datosFormulario);
            return "formulario-cargo-editar";
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
        Cargo1 cargoActualizado = cargoService.actualizarCargo(id, datosActualizacion);

        if (cargoActualizado != null) {
            redirectAttributes.addFlashAttribute("mensaje", "Cargo actualizado exitosamente!");
            return "redirect:/cargos/lista";
        } else {
            redirectAttributes.addFlashAttribute("error", "Error al actualizar el cargo con ID " + id + ". Posiblemente no existe.");
            return "redirect:/cargos/editar/" + id;
        }
    }

    // Método para eliminar un cargo (añadido para completar el CRUD básico)
    @PostMapping("/cargos/eliminar/{id}")
    public String eliminarCargo(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        boolean eliminado = cargoService.eliminarCargoPorId(id); // Asume este método en CargoService

        if (eliminado) {
            redirectAttributes.addFlashAttribute("mensaje", "Cargo eliminado exitosamente.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar el cargo con ID " + id + ". Posiblemente no existe.");
        }
        return "redirect:/cargos/lista";
    }
}
