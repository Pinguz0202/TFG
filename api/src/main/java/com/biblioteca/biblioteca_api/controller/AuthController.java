package com.biblioteca.biblioteca_api.controller;

import com.biblioteca.biblioteca_api.entity.Usuario;
import com.biblioteca.biblioteca_api.security.AuthenticationService;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationService authService;

    public AuthController(AuthenticationService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        String token = authService.login(loginRequest.getEmail(), loginRequest.getContraseña());
        return ResponseEntity.ok(new LoginResponse(token));
    }

    @PostMapping("/register")
    public ResponseEntity<Usuario> register(@RequestBody Usuario usuario) {
        Usuario nuevoUsuario = authService.registrar(usuario);
        return ResponseEntity.ok(nuevoUsuario);
    }

    @Data
    public static class LoginRequest {
        private String email;
        private String contraseña;
    }

    @Data
    @AllArgsConstructor
    public static class LoginResponse {
        private final String token;
    }
}