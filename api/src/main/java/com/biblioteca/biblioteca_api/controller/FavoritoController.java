package com.biblioteca.biblioteca_api.controller;

import com.biblioteca.biblioteca_api.entity.Favorito;
import com.biblioteca.biblioteca_api.service.FavoritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/favoritos")
public class FavoritoController {

    @Autowired
    private FavoritoService favoritoService;

    @PostMapping("/{isbn}")
    public ResponseEntity<String> añadirFavorito(@PathVariable String isbn, Authentication auth) {
        favoritoService.añadirFavorito(auth.getName(), isbn);
        return ResponseEntity.ok("Libro añadido a favoritos");
    }

    @DeleteMapping("/{isbn}")
    public ResponseEntity<String> eliminarFavorito(@PathVariable String isbn, Authentication auth) {
        favoritoService.eliminarFavorito(auth.getName(), isbn);
        return ResponseEntity.ok("Libro eliminado de favoritos");
    }

    @GetMapping
    public List<Favorito> obtenerFavoritos(Authentication auth) {
        return favoritoService.obtenerFavoritos(auth.getName());
    }

    @GetMapping("/{isbn}/check")
    public ResponseEntity<Boolean> esFavorito(@PathVariable String isbn, Authentication auth) {
        boolean favorito = favoritoService.esFavorito(auth.getName(), isbn);
        return ResponseEntity.ok(favorito);
    }


}