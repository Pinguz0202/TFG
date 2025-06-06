package com.example.bibliotecadigitalappd.data.repository

import android.util.Log
import com.example.bibliotecadigitalappd.data.local.datastore.DataStoreManager
import com.example.bibliotecadigitalappd.data.remote.api.ApiService
import com.example.bibliotecadigitalappd.data.remote.dtos.LibroDto
import com.example.bibliotecadigitalappd.util.JwtUtils
import kotlinx.coroutines.flow.first
import retrofit2.Response
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LibroRepository @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) {

    suspend fun getLibros(): Response<List<LibroDto>> {
        val token = dataStoreManager.getToken().first() ?: ""
        Log.d("LibroRepository", "Token para obtener libros: $token")

        val rol = JwtUtils.getRolFromToken(token)
        Log.d("LibroRepository", "Rol extraído del token: $rol")

        return apiService.getLibros(token = "Bearer $token")
    }

    suspend fun crearLibro(libro: LibroDto): Response<LibroDto> {
        val token = dataStoreManager.getToken().first() ?: ""
        Log.d("LibroRepository", "Token para crear libro: $token")

        val rol = JwtUtils.getRolFromToken(token)
        Log.d("LibroRepository", "Rol extraído del token: $rol")

        return apiService.crearLibro(token = "Bearer $token", libro)
    }

    suspend fun editarLibro(isbn: String, libro: LibroDto): Response<LibroDto> {
        val token = dataStoreManager.getToken().first() ?: ""
        Log.d("LibroRepository", "Token para editar libro: $token")

        val rol = JwtUtils.getRolFromToken(token)
        Log.d("LibroRepository", "Rol extraído del token: $rol")

        return apiService.actualizarLibro(token = "Bearer $token", isbn, libro)
    }

    suspend fun eliminarLibro(isbn: String): Response<Unit> {
        val token = dataStoreManager.getToken().first() ?: ""
        Log.d("LibroRepository", "Token para eliminar libro: $token")

        val rol = JwtUtils.getRolFromToken(token)
        Log.d("LibroRepository", "Rol extraído del token: $rol")

        return apiService.eliminarLibro(token = "Bearer $token", isbn)
    }
}