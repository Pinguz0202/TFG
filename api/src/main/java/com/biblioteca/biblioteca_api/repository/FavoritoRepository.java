package com.biblioteca.biblioteca_api.repository;

import com.biblioteca.biblioteca_api.entity.Favorito;
import com.biblioteca.biblioteca_api.entity.Usuario;

import jakarta.transaction.Transactional;

import com.biblioteca.biblioteca_api.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface FavoritoRepository extends JpaRepository<Favorito, Long> {
    List<Favorito> findByUsuarioIdusuario(Long idUsuario);
    Optional<Favorito> findByUsuarioAndLibro(Usuario usuario, Libro libro);
    @Transactional
    void deleteByUsuarioAndLibro(Usuario usuario, Libro libro);
}