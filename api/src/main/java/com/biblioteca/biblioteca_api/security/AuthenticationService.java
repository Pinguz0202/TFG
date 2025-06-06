package com.biblioteca.biblioteca_api.security;

import com.biblioteca.biblioteca_api.entity.Usuario;
import com.biblioteca.biblioteca_api.repository.UsuarioRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthenticationService(AuthenticationManager authenticationManager,
                                 JwtService jwtService,
                                 UsuarioRepository usuarioRepository,
                                 PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtService = jwtService;
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String email, String contraseña) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, contraseña));
        Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return jwtService.generateToken(new UsuarioDetails(usuario));
    }

    public Usuario registrar(Usuario usuario) {
        if (usuarioRepository.findByEmail(usuario.getEmail()).isPresent()) {
            throw new RuntimeException("El correo ya está registrado");
        }

        usuario.setContraseña(passwordEncoder.encode(usuario.getContraseña()));

        String rol = usuario.getRol();
        if (rol == null || !(rol.equalsIgnoreCase("ROLE_ADMIN") || rol.equalsIgnoreCase("ROLE_USER"))) {
            usuario.setRol("ROLE_USER");
        }

        return usuarioRepository.save(usuario);
    }
}