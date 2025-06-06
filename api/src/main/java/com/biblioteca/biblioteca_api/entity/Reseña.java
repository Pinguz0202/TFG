package com.biblioteca.biblioteca_api.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "resenas")
@Data
public class Reseña {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReseña;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario; // ✅ Cambiado de id_usuario a usuario

    @ManyToOne
    @JoinColumn(name = "isbn_libro", referencedColumnName = "isbn", nullable = false)
    private Libro libro;

    private int puntuacion;

    @Column(columnDefinition = "TEXT")
    private String comentario;

    private LocalDateTime fechaResena;
}