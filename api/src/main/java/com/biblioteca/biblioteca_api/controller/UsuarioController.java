package com.biblioteca.biblioteca_api.controller;

import com.biblioteca.biblioteca_api.entity.Usuario;
import com.biblioteca.biblioteca_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    // Ver todos los usuarios (solo admin)
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public List<Usuario> getAllUsuarios() {
        return usuarioRepository.findAll();
    }

    // Obtener tus propios datos (autenticado)
    @GetMapping("/me")
    public ResponseEntity<Usuario> getUsuarioActual(Authentication authentication) {
        String email = authentication.getName(); // El email extraído del token

        Optional<Usuario> usuarioOpt = usuarioRepository.findByEmail(email);
        return usuarioOpt
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Cambiar el rol de un usuario (solo admin)
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/rol")
    public ResponseEntity<?> cambiarRol(@PathVariable Long id, @RequestBody Map<String, String> body) {
        String nuevoRol = body.get("rol");

        if (nuevoRol == null || (!nuevoRol.equalsIgnoreCase("ADMIN") && !nuevoRol.equalsIgnoreCase("USER"))) {
            return ResponseEntity.badRequest().body("Rol inválido. Usa 'ADMIN' o 'USER'");
        }

        return usuarioRepository.findById(id).map(usuario -> {
            usuario.setRol(nuevoRol.toUpperCase());
            usuarioRepository.save(usuario);
            return ResponseEntity.ok("Rol actualizado a " + nuevoRol.toUpperCase());
        }).orElse(ResponseEntity.notFound().build());
    }
}