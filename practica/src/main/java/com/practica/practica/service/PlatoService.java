package com.practica.practica.service;

import com.practica.practica.model.Plato;
import com.practica.practica.model.PlatoRegistroDTO; // Importa el DTO
import com.practica.practica.repository.PlatoRepository; // Importa el Repository
import org.springframework.beans.factory.annotation.Autowired; // Importa Autowired si lo prefieres
import org.springframework.stereotype.Service;

@Service // Indica que esta clase es un componente de Servicio gestionado por Spring
public class PlatoService {

    private final PlatoRepository PlatoRepository; // Declara la dependencia al Repository

    // Inyección de Dependencias por Constructor (forma recomendada)
    // Spring detectará este constructor y le inyectará la instancia del Repository
    @Autowired // Opcional si solo hay un constructor, pero aclara la intención
    public PlatoService(PlatoRepository PlatoRepository) {
        this.PlatoRepository = PlatoRepository;
    }

    // Método para manejar la lógica de registrar un nuevo Plato
    public Plato registrarNuevoPlato(PlatoRegistroDTO datosRegistro) {
        // --- Aquí va la lógica de negocio ---
        // 1. Podrías añadir validaciones aquí (ej: si el Descripcion ya existe usando un método findByDescripcion en el Repository)
        // 2. Mapear el DTO a la Entidad Plato
        Plato nuevoPlato = new Plato();
        nuevoPlato.setNombre_Plato(datosRegistro.getNombre_Plato());
        nuevoPlato.setDescripcion(datosRegistro.getDescripcion());
        nuevoPlato.setPrecio(datosRegistro.getPrecio());
        // 3. Podrías realizar otras operaciones (ej: encriptar una contraseña si la hubiera)

        // 4. Llamar al Repository para guardar la Entidad en la base de datos
        // El método save() inserta si el ID es null, o actualiza si el ID existe
        return PlatoRepository.save(nuevoPlato);
    }

    // Puedes añadir otros métodos aquí para otras operaciones relacionadas con Platos
    // public List<Plato> obtenerTodosPlatos() { ... }
    // public Optional<Plato> obtenerPlatoPorId(Long id) { ... }
}