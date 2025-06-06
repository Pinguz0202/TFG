package com.example.bibliotecadigitalappd.data.remote.api

import com.example.bibliotecadigitalappd.data.remote.dtos.*
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    // Autenticación
    @POST("/api/auth/login")
    suspend fun login(
        @Body request: LoginRequest
    ): Response<LoginResponse>

    @POST("/api/auth/register")
    suspend fun register(
        @Body request: RegisterRequest
    ): Response<RegisterResponse>

    // Libros
    @GET("/api/libros")
    suspend fun getLibros(
        @Header("Authorization") token: String
    ): Response<List<LibroDto>>

    @GET("/api/libros/{id}")
    suspend fun getLibroDetalle(
        @Header("Authorization") token: String,
        @Path("id") libroId: String
    ): Response<LibroDto>

    // Nuevos servicios para libros
    @POST("/api/libros")
    suspend fun crearLibro(
        @Header("Authorization") token: String,
        @Body libro: LibroDto
    ): Response<LibroDto>

    @PUT("/api/libros/{isbn}")
    suspend fun actualizarLibro(
        @Header("Authorization") token: String,
        @Path("isbn") isbn: String,
        @Body libro: LibroDto
    ): Response<LibroDto>

    @DELETE("/api/libros/{isbn}")
    suspend fun eliminarLibro(
        @Header("Authorization") token: String,
        @Path("isbn") isbn: String
    ): Response<Unit>

    // Reseñas
    @GET("/api/resenas/libro/{libroId}")
    suspend fun getResenasLibro(
        @Header("Authorization") token: String,
        @Path("libroId") libroId: String
    ): Response<List<ResenaDto>>

    @POST("/api/resenas")
    suspend fun crearOActualizarResena(
        @Header("Authorization") token: String,
        @Body request: ResenaRequest
    ): Response<ResenaDto>

    @PUT("/api/resenas/{id}")
    suspend fun updateResena(
        @Header("Authorization") token: String,
        @Path("id") id: Long,
        @Body request: ResenaRequest
    ): Response<ResenaDto>

    @DELETE("/api/resenas/{id}")
    suspend fun deleteResena(
        @Header("Authorization") token: String,
        @Path("id") id: Long
    ): Response<Unit>

    // Favoritos
    @GET("/api/favoritos/{libroId}/check")
    suspend fun checkFavorito(
        @Header("Authorization") token: String,
        @Path("libroId") libroId: String
    ): Response<Boolean>

    @POST("/api/favoritos/{libroId}")
    suspend fun agregarFavorito(
        @Header("Authorization") token: String,
        @Path("libroId") libroId: String
    ): Response<Unit>

    @DELETE("/api/favoritos/{libroId}")
    suspend fun eliminarFavorito(
        @Header("Authorization") token: String,
        @Path("libroId") libroId: String
    ): Response<Unit>

    @GET("/api/favoritos")
    suspend fun getFavoritos(
        @Header("Authorization") token: String
    ): Response<List<FavoritoDto>>

    // Usuario
    @GET("/api/usuarios/me")
    suspend fun getUserProfile(
        @Header("Authorization") token: String
    ): Response<UsuarioDto>

    @GET("/api/resenas/usuario")
    suspend fun getUserResenas(
        @Header("Authorization") token: String
    ): Response<List<ResenaDto>>
}