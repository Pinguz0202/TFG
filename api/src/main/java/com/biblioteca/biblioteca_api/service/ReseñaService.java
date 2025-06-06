package com.biblioteca.biblioteca_api.service;

import com.biblioteca.biblioteca_api.entity.Libro;
import com.biblioteca.biblioteca_api.entity.Reseña;
import com.biblioteca.biblioteca_api.entity.Usuario;
import com.biblioteca.biblioteca_api.repository.LibroRepository;
import com.biblioteca.biblioteca_api.repository.ReseñaRepository;
import com.biblioteca.biblioteca_api.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ReseñaService {

    @Autowired
    private ReseñaRepository reseñaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private LibroRepository libroRepository;

    //Crea una nueva reseña o actualiza una existente si el usuario ya ha reseñado este libro.
    public Reseña crearOActualizarReseña(String email, String isbn, int puntuacion, String comentario) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Libro libro = libroRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        Optional<Reseña> reseñaExistente = reseñaRepository.findByUsuarioAndLibro(usuario, libro);

        Reseña reseña;
        if (reseñaExistente.isPresent()) {
            reseña = reseñaExistente.get();
            reseña.setPuntuacion(puntuacion);
            reseña.setComentario(comentario);
        } else {
            reseña = new Reseña();
            reseña.setUsuario(usuario);
            reseña.setLibro(libro);
            reseña.setPuntuacion(puntuacion);
            reseña.setComentario(comentario);
            reseña.setFechaResena(LocalDateTime.now());
        }

        Reseña reseñaGuardada = reseñaRepository.save(reseña);

        // Recalcula y actualiza la puntuación promedio del libro
        recalcularPuntuacionPromedio(libro.getIsbn());

        return reseñaGuardada;
    }

    // --- Métodos existentes (sin cambios significativos en la lógica de negocio) ---

    public List<Reseña> obtenerPorLibro(String isbn) {
        return reseñaRepository.findByLibroIsbn(isbn);
    }

    public List<Reseña> obtenerPorUsuario(Long idUsuario) {
        return reseñaRepository.findByUsuario_Idusuario(idUsuario);
    }

    //Este método ahora solo se usaría si quieres actualizar una reseña por su ID
    public Reseña actualizarReseñaExistente(Long id, String email, String isbn, int puntuacion, String comentario) {
        Reseña reseña = reseñaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con ID: " + id));

        // Verificar si la reseña es del usuario autenticado
        if (!reseña.getUsuario().getEmail().equals(email)) {
            throw new RuntimeException("Acceso denegado: No tienes permiso para actualizar esta reseña.");
        }

        Libro libro = libroRepository.findById(isbn)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado"));

        // String isbnLibroOriginal = reseña.getLibro().getIsbn(); // ✅ ELIMINA O COMENTA ESTA LÍNEA

        reseña.setLibro(libro);
        reseña.setPuntuacion(puntuacion);
        reseña.setComentario(comentario);

        Reseña reseñaActualizada = reseñaRepository.save(reseña);

        recalcularPuntuacionPromedio(libro.getIsbn());

        return reseñaActualizada;
    }

    public boolean eliminarReseña(Long id, String email) {
        Reseña reseña = reseñaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reseña no encontrada con ID: " + id));

        // Verificar si la reseña es del usuario autenticado
        if (!reseña.getUsuario().getEmail().equals(email)) {
            throw new RuntimeException("Acceso denegado: No tienes permiso para eliminar esta reseña.");
        }

        String isbnLibroAfectado = reseña.getLibro().getIsbn(); // Obtener ISBN antes de eliminar
        reseñaRepository.delete(reseña); // Eliminar la reseña

        // Recalcular y actualizar la puntuación promedio del libro
        recalcularPuntuacionPromedio(isbnLibroAfectado);

        return true;
    }

    // --- Método de ayuda (sin cambios) ---
    private void recalcularPuntuacionPromedio(String isbnLibro) {
        Libro libro = libroRepository.findById(isbnLibro)
                .orElseThrow(() -> new RuntimeException("Libro no encontrado durante recálculo de puntuación"));

        List<Reseña> reseñasDelLibro = reseñaRepository.findByLibroIsbn(isbnLibro);

        if (reseñasDelLibro.isEmpty()) {
            libro.setPuntuacionPromedio(0.0);
        } else {
            double sumaPuntuaciones = reseñasDelLibro.stream()
                    .mapToInt(Reseña::getPuntuacion)
                    .sum();
            double promedio = (double) sumaPuntuaciones / reseñasDelLibro.size();
            libro.setPuntuacionPromedio(promedio);
        }
        libroRepository.save(libro);
    }
}