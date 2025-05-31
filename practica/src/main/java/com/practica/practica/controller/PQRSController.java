package com.practica.practica.controller;

import com.practica.practica.model.PQRS;
import com.practica.practica.model.PQRSRegistroDTO;
import com.practica.practica.model.Restaurante;
import com.practica.practica.repository.RestauranteRepository;
import com.practica.practica.service.PQRSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
public class PQRSController {

    private final PQRSService pqrsService;
    private final RestauranteRepository restauranteRepository;

    @Autowired
    public PQRSController(PQRSService pqrsService, RestauranteRepository restauranteRepository) {
        this.pqrsService = pqrsService;
        this.restauranteRepository = restauranteRepository;
    }

    // Mostrar el formulario de registro de una nueva PQRS
    @GetMapping("/pqrs/registro")
    public String mostrarFormularioPQRS(Model model) {
        // Obtener todos los restaurantes
        List<Restaurante> restaurantes = restauranteRepository.findAll();

        model.addAttribute("restaurantes", restaurantes);
        model.addAttribute("datosFormularioPQRS", new PQRSRegistroDTO());
        System.out.println("Sirviendo formulario-pqrs-registro.html desde templates");
        return "formulario-pqrs-registro";
    }

    // Procesar el envío del formulario de registro de PQRS
    @PostMapping("/pqrs/guardar")
    public String registrarPQRS(@ModelAttribute("datosFormularioPQRS") PQRSRegistroDTO datosFormulario,
                                RedirectAttributes redirectAttributes) {
        System.out.println("Datos recibidos del formulario de PQRS en POST:");
        System.out.println("RUT: " + datosFormulario.getRut());
        System.out.println("Tipo_Solicitud: " + datosFormulario.getTipoSolicitud());
        System.out.println("Descripcion: " + datosFormulario.getDescripcion());
        System.out.println("Fecha: " + datosFormulario.getFecha());

        try {
            if (datosFormulario.getFecha() == null) {
                datosFormulario.setFecha(LocalDate.now());
            }
            pqrsService.registrarNuevaPQRS(datosFormulario);
            redirectAttributes.addFlashAttribute("mensaje", "PQRS registrada exitosamente!");
            System.out.println("Registro de PQRS exitoso, redirigiendo a /pqrs/lista");
            return "redirect:/pqrs/lista";
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al registrar la PQRS: " + e.getMessage());
            System.out.println("Error en el registro de PQRS, redirigiendo a /pqrs/registro");
            return "redirect:/pqrs/registro";
        }
    }

    // Listar todas las PQRS
    @GetMapping("/pqrs/lista")
    public String listarPQRS(Model model) {
        List<PQRS> pqrsList = pqrsService.obtenerTodasLasPQRS();
        model.addAttribute("pqrsList", pqrsList);
        System.out.println("Sirviendo lista-pqrs.html desde templates");
        return "lista-pqrs";
    }

    // Mostrar formulario para editar una PQRS existente
    @GetMapping("/pqrs/editar/{id}")
    public String mostrarFormularioEdicionPQRS(@PathVariable("id") Long id, Model model, RedirectAttributes redirectAttributes) {
        Optional<PQRS> pqrsOptional = pqrsService.obtenerPQRSPorId(id);

        if (pqrsOptional.isPresent()) {
            // Obtener todos los restaurantes para el dropdown
            List<Restaurante> restaurantes = restauranteRepository.findAll();

            PQRS pqrs = pqrsOptional.get();
            PQRSRegistroDTO datosFormulario = new PQRSRegistroDTO(
                    pqrs.getRestaurante().getRUT(),
                    pqrs.getTipoSolicitud(),
                    pqrs.getDescripcion(),
                    pqrs.getFecha()
            );
            model.addAttribute("restaurantes", restaurantes);
            model.addAttribute("pqrsId", id);
            model.addAttribute("datosFormularioPQRS", datosFormulario);
            System.out.println("Sirviendo formulario-pqrs-editar.html para PQRS ID: " + id);
            return "formulario-pqrs-editar";
        } else {
            redirectAttributes.addFlashAttribute("error", "PQRS no encontrada para editar.");
            return "redirect:/pqrs/lista";
        }
    }

    // Procesar la actualización de una PQRS
    @PostMapping("/pqrs/actualizar/{id}")
    public String actualizarPQRS(@PathVariable("id") Long id,
                                 @ModelAttribute("datosFormularioPQRS") PQRSRegistroDTO datosActualizacion,
                                 RedirectAttributes redirectAttributes) {
        System.out.println("Intentando actualizar PQRS con ID: " + id);
        System.out.println("Nuevos datos: RUT=" + datosActualizacion.getRut() + ", Tipo=" + datosActualizacion.getTipoSolicitud());

        try {
            PQRS pqrsActualizada = pqrsService.actualizarPQRS(id, datosActualizacion);

            if (pqrsActualizada != null) {
                redirectAttributes.addFlashAttribute("mensaje", "PQRS actualizada exitosamente!");
                System.out.println("PQRS con ID " + id + " actualizada.");
                return "redirect:/pqrs/lista";
            } else {
                redirectAttributes.addFlashAttribute("error", "Error al actualizar la PQRS con ID " + id + ". Posiblemente no existe.");
                System.out.println("Error al actualizar PQRS con ID " + id + ".");
                return "redirect:/pqrs/editar/" + id;
            }
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addFlashAttribute("error", "Error al actualizar la PQRS: " + e.getMessage());
            return "redirect:/pqrs/editar/" + id;
        }
    }

    // Eliminar una PQRS
    @PostMapping("/pqrs/eliminar/{id}")
    public String eliminarPQRS(@PathVariable("id") Long id, RedirectAttributes redirectAttributes) {
        System.out.println("Intentando eliminar PQRS con ID: " + id);
        boolean eliminado = pqrsService.eliminarPQRSPorId(id);

        if (eliminado) {
            redirectAttributes.addFlashAttribute("mensaje", "PQRS eliminada exitosamente.");
            System.out.println("PQRS con ID " + id + " eliminada.");
        } else {
            redirectAttributes.addFlashAttribute("error", "No se pudo eliminar la PQRS con ID " + id + ". Posiblemente no existe.");
            System.out.println("No se encontró la PQRS con ID " + id + " para eliminar.");
        }
        return "redirect:/pqrs/lista";
    }
}