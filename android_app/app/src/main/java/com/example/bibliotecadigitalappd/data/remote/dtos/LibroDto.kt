package com.example.bibliotecadigitalappd.data.remote.dtos

import com.google.gson.annotations.SerializedName

// --- Login ---
data class LoginRequest(
    val email: String,
    val contraseña: String
)

data class LoginResponse(
    val token: String
)

// --- Registro ---
data class RegisterRequest(
    val nombre: String,
    val email: String,
    val contraseña: String,
    val rol: String
)

data class RegisterResponse(
    val mensaje: String
)

// --- Libro ---
data class LibroDto(
    val isbn: String,
    val titulo: String?,
    val autor: String,
    @SerializedName("aniodepublicacion")
    val anioPublicacion: Int,
    val genero: String? = null,
    val sinopsis: String? = null,
    val puntuacionPromedio: Double? = null,
    val portadaUrl: String? = null
) {
    init {
        require(isbn.isNotEmpty()) { "ISBN no puede estar vacío" }
        require(autor.isNotEmpty()) { "Autor no puede estar vacío" }
    }
    fun getSafeRating(): Float = puntuacionPromedio?.toFloat() ?: 0f
    fun getSafePortadaUrl(): String? = portadaUrl?.takeIf { it.isNotBlank() }
    fun getSafeTitulo(): String =
        titulo?.takeIf { it.isNotBlank() }?.cleanEncoding() ?: "Sin título"
    fun getSafeGenero(): String = genero?.cleanEncoding() ?: "Sin género"
    fun getSafeSinopsis(): String = sinopsis?.cleanEncoding() ?: "Sin sinopsis disponible"
}

// --- Usuario ---
data class UsuarioDto(
    val nombre: String,
    val email: String,
    val contraseña: String,
    val fechaRegistro: String,
    val rol: String,
    @SerializedName("id_usuario")
    val idUsuario: Int
)

// --- Favoritos ---
data class LibroFavorito(
    val idFavorito: Int,
    val usuario: UsuarioDto,
    val libro: LibroDto
)

// --- Reseñas ---
data class ResenaDto(
    val id: Long,
    val puntuacion: Int,
    val comentario: String,
    @SerializedName("fechaResena")
    val fechaResena: String,
    @SerializedName("nombreUsuario")
    val nombreUsuario: String,
    @SerializedName("tituloLibro")
    val tituloLibro: String,
    @SerializedName("isbnLibro")
    val isbnLibro: String?
)
data class FavoritoDto(
    val idFavorito: Long,
    val usuario: UsuarioDto,
    val libro: LibroDto
)

data class ResenaRequest(
    val isbnLibro: String,
    val puntuacion: Int,
    val comentario: String
)

// --- Extensión para limpiar codificación ---
fun String?.cleanEncoding(): String {
    if (this == null) return ""
    return this.replace("Ã±", "ñ")
        .replace("Ã­", "í")
        .replace("Ã©", "é")
        .replace("Ã³", "ó")
        .replace("Ãº", "ú")
        .replace("Ã¡", "á")
}