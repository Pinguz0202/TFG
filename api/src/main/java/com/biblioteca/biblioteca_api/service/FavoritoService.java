package com.biblioteca.biblioteca_api.service;

import com.biblioteca.biblioteca_api.entity.Favorito;
import com.biblioteca.biblioteca_api.entity.Libro;
import com.biblioteca.biblioteca_api.entity.Usuario;
import com.biblioteca.biblioteca_api.repository.FavoritoRepository;
import com.biblioteca.biblioteca_api.repository.LibroRepository;
import com.biblioteca.biblioteca_api.repository.UsuarioRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class FavoritoService {

    @Autowired
    private FavoritoRepository favoritoRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    public void añadirFavorito(String email, String isbn) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Libro libro = libroRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        favoritoRepository.findByUsuarioAndLibro(usuario, libro)
                .ifPresent(f -> { throw new RuntimeException("Ya está en favoritos"); });

        Favorito favorito = new Favorito();
        favorito.setUsuario(usuario);
        favorito.setLibro(libro);
        favoritoRepository.save(favorito);
    }

    public void eliminarFavorito(String email, String isbn) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Libro libro = libroRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        favoritoRepository.deleteByUsuarioAndLibro(usuario, libro);
    }

    public List<Favorito> obtenerFavoritos(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return favoritoRepository.findByUsuarioIdusuario(usuario.getId_usuario());
    }

    public boolean esFavorito(String email, String isbn) {
    Usuario usuario = usuarioRepository.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    Libro libro = libroRepository.findById(isbn)
            .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

    return favoritoRepository.findByUsuarioAndLibro(usuario, libro).isPresent();
}


}