package com.practica.practica.controller;

import com.practica.practica.model.UsuarioRegistroDTO; // Importa el DTO
import com.practica.practica.service.UsuarioService; // Importa el Service
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model; // Importa Model si necesitas pasar datos a la plantilla
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller // Seguimos usando @Controller porque devolvemos nombres de vistas/redirecciones
public class UsuarioController {

    private final UsuarioService usuarioService;

    @Autowired // Inyección de dependencias por constructor
    public UsuarioController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    // --- MÉTODO PARA MOSTRAR EL FORMULARIO (GET) ---
    // Mapea la raíz "/" (o la URL que quieras para la página inicial)
    @GetMapping("/")
    public String mostrarFormulario(Model model) {
        // Opcional: Si usas Thymeleaf y necesitas enlazar el formulario a un objeto vacío al inicio
        // model.addAttribute("usuario", new UsuarioRegistroDTO());
        System.out.println("Sirviendo formulario.html desde templates");
        // Retorna el nombre de la plantilla (Spring buscará src/main/resources/templates/formulario.html)
        return "formulario";
    }

    // --- MÉTODO PARA MANEJAR LA PETICIÓN POST DEL FORMULARIO ---
    // Mapea la URL de acción de tu formulario HTML
    @PostMapping("/guardar-datos")
    public String registrarUsuario(@ModelAttribute UsuarioRegistroDTO datosFormulario) {
        System.out.println("Datos recibidos del formulario en POST:");
        System.out.println("Nombre: " + datosFormulario.getNombre());
        System.out.println("Email: " + datosFormulario.getEmail());
        System.out.println("Edad: " + datosFormulario.getEdad());

        try {
            // Lógica de negocio para guardar
            usuarioService.registrarNuevoUsuario(datosFormulario);

            // --- REDIRECCIÓN A LA PÁGINA DE ÉXITO ---
            // Usamos "redirect:/URL" para indicarle al navegador que haga una nueva petición GET
            // a la URL especificada. Esto es importante para evitar el reenvío accidental del formulario.
            System.out.println("Registro exitoso, redirigiendo a /exito");
            return "redirect:/exito";

        } catch (Exception e) {
            // Manejo básico de errores
            e.printStackTrace();
            System.out.println("Error en el registro, redirigiendo a /error");
            // --- REDIRECCIÓN A LA PÁGINA DE ERROR ---
            return "redirect:/error";
        }
    }

    // --- MÉTODO PARA MOSTRAR LA PÁGINA DE ÉXITO (GET) ---
    // Mapea la URL a la que redirige el POST exitoso
    @GetMapping("/exito")
    public String mostrarPaginaExito() {
        System.out.println("Sirviendo exito.html desde templates");
        // Retorna el nombre de la plantilla (Spring buscará src/main/resources/templates/exito.html)
        return "exito";
    }

    // --- MÉTODO PARA MOSTRAR LA PÁGINA DE ERROR (GET) ---
    // Mapea la URL a la que redirige el POST fallido
    @GetMapping("/error")
    public String mostrarPaginaError() {
        System.out.println("Sirviendo error.html desde templates");
        // Retorna el nombre de la plantilla (Spring buscará src/main/resources/templates/error.html)
        return "error";
    }
}