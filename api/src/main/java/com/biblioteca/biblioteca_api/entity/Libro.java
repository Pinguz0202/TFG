package com.biblioteca.biblioteca_api.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "libros")
@Data
public class Libro {
   @Id
   private String isbn;

   private String titulo;
   private String autor;

   @Column(name = "año_publicacion")
   private int aniodepublicacion;

   private String genero;

    @Column(columnDefinition = "TEXT")
    private String sinopsis;

   @Column(name = "puntuacion_promedio")
   private Double puntuacionPromedio;

   @Column(name = "portada_url")
    private String portadaUrl;
}