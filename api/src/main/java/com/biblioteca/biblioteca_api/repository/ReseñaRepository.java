package com.biblioteca.biblioteca_api.repository;

import com.biblioteca.biblioteca_api.entity.Libro;
import com.biblioteca.biblioteca_api.entity.Reseña;
import com.biblioteca.biblioteca_api.entity.Usuario;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReseñaRepository extends JpaRepository<Reseña, Long> {
    List<Reseña> findByLibroIsbn(String isbn);
    List<Reseña> findByUsuario_Idusuario(Long idUsuario);
    Optional<Reseña> findByUsuarioAndLibro(Usuario usuario, Libro libro);

}