package com.biblioteca.biblioteca_api.controller;

import com.biblioteca.biblioteca_api.entity.Libro;
import com.biblioteca.biblioteca_api.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    @Autowired
    private LibroService libroService;

    @GetMapping
    public List<Libro> obtenerTodos() {
        return libroService.obtenerTodos();
    }

    @GetMapping("/{isbn}")
    public ResponseEntity<Libro> obtenerPorIsbn(@PathVariable String isbn) {
        return libroService.obtenerPorIsbn(isbn)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Libro> crearLibro(@RequestBody Libro libro) {
        return ResponseEntity.ok(libroService.crearLibro(libro));
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @PutMapping("/{isbn}")
    public ResponseEntity<Libro> actualizarLibro(@PathVariable String isbn, @RequestBody Libro libro) {
        return libroService.actualizarLibro(isbn, libro)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    @DeleteMapping("/{isbn}")
    public ResponseEntity<Void> eliminarLibro(@PathVariable String isbn) {
        libroService.eliminarLibro(isbn);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/debug")
    public ResponseEntity<String> testInsert() {
        Libro libro = new Libro();
        libro.setIsbn("1111111111111");
        libro.setTitulo("Libro Test");
        libro.setAutor("Debug Autor");
        libro.setAniodepublicacion(2024);
        libro.setGenero("Debug");
        libro.setSinopsis("Este es un test.");
        libro.setPuntuacionPromedio(0.0);
        libroService.crearLibro(libro);
        return ResponseEntity.ok("Libro insertado");
    }


}