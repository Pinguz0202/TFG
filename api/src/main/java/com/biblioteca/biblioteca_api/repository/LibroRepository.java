package com.biblioteca.biblioteca_api.repository;

import com.biblioteca.biblioteca_api.entity.Libro;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LibroRepository extends JpaRepository<Libro, String> {
    
}