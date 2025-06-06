package com.biblioteca.biblioteca_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "favoritos")
@Data
public class Favorito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idFavorito;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "isbn_libro", referencedColumnName = "isbn", nullable = false)
    private Libro libro;
}