package com.biblioteca.biblioteca_api.controller;

import com.biblioteca.biblioteca_api.dto.ReseñaDTO;
import com.biblioteca.biblioteca_api.dto.ReseñaRequestDTO;
import com.biblioteca.biblioteca_api.entity.Reseña;
import com.biblioteca.biblioteca_api.service.ReseñaService;
import com.biblioteca.biblioteca_api.service.UsuarioService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/resenas")
public class ReseñaController {

    @Autowired
    private ReseñaService reseñaService;

    @Autowired
    private UsuarioService usuarioService;

    //Crea una nueva reseña o actualiza una existente para un libro por parte de un usuario autenticado.
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReseñaDTO> crearOActualizarReseña(
            @Valid @RequestBody ReseñaRequestDTO requestDTO,
            Authentication authentication
    ) {
        String email = authentication.getName();

        // El servicio maneja la lógica de crear o actualizar y lanzar excepciones si el usuario/libro no existe
        Reseña reseñaGuardada = reseñaService.crearOActualizarReseña(
                email,
                requestDTO.getIsbnLibro(),
                requestDTO.getPuntuacion(),
                requestDTO.getComentario()
        );

        // Retorna la reseña guardada/actualizada mapeada a un DTO de respuesta
        return ResponseEntity.status(HttpStatus.CREATED).body(convertirAResenaDTO(reseñaGuardada));
    }


    //Obtiene todas las reseñas de un libro específico por su ISBN.
    @GetMapping("/libro/{isbn}")
    public ResponseEntity<List<ReseñaDTO>> obtenerResenasPorLibro(@PathVariable String isbn) {
        List<Reseña> reseñas = reseñaService.obtenerPorLibro(isbn);
        List<ReseñaDTO> reseñasDTO = reseñas.stream()
                                                .map(this::convertirAResenaDTO)
                                                .collect(Collectors.toList());
        return ResponseEntity.ok(reseñasDTO);
    }

    //Obtiene todas las reseñas de un usuario autenticado.
    @GetMapping("/usuario")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReseñaDTO>> obtenerResenasPorUsuario(Authentication authentication) {
        String email = authentication.getName();
        Long idUsuario = usuarioService.obtenerUsuarioPorEmail(email)
                                        .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado en DB"))
                                        .getId_usuario();

        List<Reseña> reseñas = reseñaService.obtenerPorUsuario(idUsuario);
        List<ReseñaDTO> reseñasDTO = reseñas.stream()
                                                .map(this::convertirAResenaDTO)
                                                .collect(Collectors.toList());
        return ResponseEntity.ok(reseñasDTO);
    }

    //Actualiza una reseña existente por su ID.
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<ReseñaDTO> actualizarReseña(
            @PathVariable Long id,
            @Valid @RequestBody ReseñaRequestDTO requestDTO,
            Authentication authentication
    ) {
        String email = authentication.getName();
        Reseña reseñaActualizada = reseñaService.actualizarReseñaExistente(
                id,
                email,
                requestDTO.getIsbnLibro(),
                requestDTO.getPuntuacion(),
                requestDTO.getComentario()
        );

        return ResponseEntity.ok(convertirAResenaDTO(reseñaActualizada));
    }

    //Elimina una reseña por su ID. Solo el propietario puede eliminarla./
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<Void> eliminarReseña(@PathVariable Long id, Authentication authentication) {
        String email = authentication.getName();
        reseñaService.eliminarReseña(id, email);
        return ResponseEntity.noContent().build();
    }

    //Método de ayuda para convertir una entidad Reseña a un ReseñaDTO.
    private ReseñaDTO convertirAResenaDTO(Reseña reseña) {
        if (reseña == null) {
            return null;
        }
        ReseñaDTO dto = new ReseñaDTO();
        dto.setId(reseña.getIdReseña()); // ✅ ¡CAMBIO AQUÍ! Usa getIdReseña()
        dto.setPuntuacion(reseña.getPuntuacion());
        dto.setComentario(reseña.getComentario());
        dto.setFechaResena(reseña.getFechaResena());

        // Asegúrate de que el usuario y el libro no son nulos antes de acceder a ellos
        dto.setNombreUsuario(reseña.getUsuario() != null ? reseña.getUsuario().getNombre() : "Anónimo");
        dto.setTituloLibro(reseña.getLibro() != null ? reseña.getLibro().getTitulo() : "Libro Desconocido");
        dto.setIsbnLibro(reseña.getLibro() != null ? reseña.getLibro().getIsbn() : null);

        return dto;
    }
}