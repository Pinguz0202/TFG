package com.example.bibliotecadigitalappd.data.repository

import com.example.bibliotecadigitalappd.data.local.datastore.DataStoreManager
import com.example.bibliotecadigitalappd.data.remote.api.ApiService
import com.example.bibliotecadigitalappd.data.remote.dtos.FavoritoDto
import com.example.bibliotecadigitalappd.data.remote.dtos.LibroDto
import kotlinx.coroutines.flow.first
import retrofit2.Response

class FavoritosRepository(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) {
    suspend fun obtenerFavoritos(): Response<List<FavoritoDto>> {
        val token = dataStoreManager.getToken().first() ?: ""
        return apiService.getFavoritos(token = "Bearer $token")
    }
}