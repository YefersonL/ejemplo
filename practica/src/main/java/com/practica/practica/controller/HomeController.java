package com.practica.practica.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/") // Cuando el usuario accede a http://localhost:8080/
    public String home() {
        return "index"; // Esto hará que Spring Boot busque src/main/resources/templates/index.html
    }

    // Asegúrate de que ningún otro controlador tenga un @GetMapping("/") también.
    // Si tu PlatoController tenía uno, elimínalo o cámbialo a una ruta diferente.
}