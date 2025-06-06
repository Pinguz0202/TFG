package com.biblioteca.biblioteca_api.service;

import com.biblioteca.biblioteca_api.entity.Libro;
import com.biblioteca.biblioteca_api.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LibroService {

    @Autowired
    private LibroRepository libroRepository;

    public List<Libro> obtenerTodos() {
        return libroRepository.findAll();
    }

    public Optional<Libro> obtenerPorIsbn(String isbn) {
        return libroRepository.findById(isbn);
    }

    public Libro crearLibro(Libro libro) {
        return libroRepository.save(libro);
    }

    public Optional<Libro> actualizarLibro(String isbn, Libro libroActualizado) {
        return libroRepository.findById(isbn).map(libro -> {
            libro.setTitulo(libroActualizado.getTitulo());
            libro.setAutor(libroActualizado.getAutor());
            libro.setAniodepublicacion(libroActualizado.getAniodepublicacion());
            libro.setGenero(libroActualizado.getGenero());
            libro.setSinopsis(libroActualizado.getSinopsis());
            return libroRepository.save(libro);
        });
    }

    public void eliminarLibro(String isbn) {
        libroRepository.deleteById(isbn);
    }
}