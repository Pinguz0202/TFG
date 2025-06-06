package com.biblioteca.biblioteca_api.service;

import com.biblioteca.biblioteca_api.entity.Usuario;
import com.biblioteca.biblioteca_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional; // Aunque no siempre necesario para solo lectura, es buena práctica si el servicio tiene más lógica

import java.util.Optional;

@Service
@Transactional(readOnly = true) // Generalmente para métodos de solo lectura
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    /**
     * Obtiene un usuario por su dirección de email.
     * @param email La dirección de email del usuario.
     * @return Un Optional que contiene el Usuario si se encuentra, o un Optional vacío si no.
     */
    public Optional<Usuario> obtenerUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email);
    }

    // Aquí podrías añadir otros métodos relacionados con la lógica de negocio de Usuario,
    // como actualizar perfil, cambiar contraseña, etc., que luego serían utilizados por UsuarioController.

    // Ejemplo: obtener el ID del usuario directamente (útil para el ReseñaController)
    public Long getIdUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email)
                .map(Usuario::getId_usuario) // Asumo que el campo ID de tu entidad Usuario se llama id_usuario
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email: " + email));
    }
}