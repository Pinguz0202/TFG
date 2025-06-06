package com.example.bibliotecadigitalappd.data.repository

import com.example.bibliotecadigitalappd.data.local.datastore.DataStoreManager
import com.example.bibliotecadigitalappd.data.remote.api.ApiService
import com.example.bibliotecadigitalappd.data.remote.dtos.ResenaDto
import com.example.bibliotecadigitalappd.data.remote.dtos.ResenaRequest
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class ResenaRepository @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) {

    suspend fun getResenaById(id: Long): ResenaDto {
        val token = dataStoreManager.getToken().first() ?: throw Exception("No token disponible")
        val response = apiService.getUserResenas(token = "Bearer $token")
        if (response.isSuccessful) {
            val lista = response.body()
            val resena = lista?.find { it.id == id }
            if (resena != null) return resena
            else throw Exception("Reseña no encontrada")
        } else {
            throw Exception("Error al obtener reseña: ${response.code()}")
        }
    }

    suspend fun updateResena(id: Long, isbnLibro: String, puntuacion: Int, comentario: String) {
        val token = dataStoreManager.getToken().first() ?: throw Exception("No token disponible")

        val resenaRequest = ResenaRequest(
            isbnLibro = isbnLibro,
            puntuacion = puntuacion,
            comentario = comentario
        )

        val response = apiService.updateResena(
            token = "Bearer $token",
            id = id,
            request = resenaRequest
        )

        if (!response.isSuccessful) {
            throw Exception("Error al actualizar reseña: ${response.code()}")
        }
    }

}