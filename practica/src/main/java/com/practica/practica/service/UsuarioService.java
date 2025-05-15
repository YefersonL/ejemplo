package com.practica.practica.service;

import com.practica.practica.model.Usuario;
import com.practica.practica.model.UsuarioRegistroDTO; // Importa el DTO
import com.practica.practica.repository.UsuarioRepository; // Importa el Repository
import org.springframework.beans.factory.annotation.Autowired; // Importa Autowired si lo prefieres
import org.springframework.stereotype.Service;

@Service // Indica que esta clase es un componente de Servicio gestionado por Spring
public class UsuarioService {

    private final UsuarioRepository usuarioRepository; // Declara la dependencia al Repository

    // Inyección de Dependencias por Constructor (forma recomendada)
    // Spring detectará este constructor y le inyectará la instancia del Repository
    @Autowired // Opcional si solo hay un constructor, pero aclara la intención
    public UsuarioService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    // Método para manejar la lógica de registrar un nuevo usuario
    public Usuario registrarNuevoUsuario(UsuarioRegistroDTO datosRegistro) {
        // --- Aquí va la lógica de negocio ---
        // 1. Podrías añadir validaciones aquí (ej: si el email ya existe usando un método findByEmail en el Repository)
        // 2. Mapear el DTO a la Entidad Usuario
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setNombre(datosRegistro.getNombre());
        nuevoUsuario.setEmail(datosRegistro.getEmail());
        nuevoUsuario.setEdad(datosRegistro.getEdad());
        // 3. Podrías realizar otras operaciones (ej: encriptar una contraseña si la hubiera)

        // 4. Llamar al Repository para guardar la Entidad en la base de datos
        // El método save() inserta si el ID es null, o actualiza si el ID existe
        return usuarioRepository.save(nuevoUsuario);
    }

    // Puedes añadir otros métodos aquí para otras operaciones relacionadas con usuarios
    // public List<Usuario> obtenerTodosUsuarios() { ... }
    // public Optional<Usuario> obtenerUsuarioPorId(Long id) { ... }
}