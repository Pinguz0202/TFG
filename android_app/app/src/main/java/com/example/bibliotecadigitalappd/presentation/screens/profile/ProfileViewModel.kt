package com.example.bibliotecadigitalappd.presentation.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotecadigitalappd.data.remote.api.ApiService
import com.example.bibliotecadigitalappd.data.local.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject
import android.util.Log
import com.example.bibliotecadigitalappd.data.remote.dtos.LibroDto
import com.example.bibliotecadigitalappd.data.remote.dtos.ResenaDto
import com.example.bibliotecadigitalappd.data.remote.dtos.UsuarioDto
import com.example.bibliotecadigitalappd.data.remote.dtos.FavoritoDto

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _user = MutableStateFlow<UsuarioDto?>(null)
    val user: StateFlow<UsuarioDto?> = _user

    private val _favoriteBooks = MutableStateFlow<List<LibroDto>>(emptyList())
    val favoriteBooks: StateFlow<List<LibroDto>> = _favoriteBooks

    private val _userResenas = MutableStateFlow<List<ResenaDto>>(emptyList())
    val userResenas: StateFlow<List<ResenaDto>> = _userResenas

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    private var currentToken: String? = null

    init {
        loadTokenAndData()
    }

    private fun loadTokenAndData() {
        viewModelScope.launch {
            dataStoreManager.getToken().collect { token ->
                currentToken = token
                if (token != null) {
                    refreshUserData()
                } else {
                    _errorMessage.value = "No se encontró token de sesión"
                }
            }
        }
    }

    fun refreshUserData() {
        val token = currentToken ?: return
        val bearer = "Bearer $token"

        viewModelScope.launch {
            try {
                loadUserProfile(bearer)
                loadFavoriteBooks(bearer)
                loadUserReviews(bearer)
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar datos: ${e.localizedMessage}"
                Log.e("ProfileViewModel", "Error en refreshUserData", e)
            }
        }
    }

    private suspend fun loadUserProfile(bearer: String) {
        val response = apiService.getUserProfile(bearer)
        if (response.isSuccessful) {
            _user.value = response.body()
        } else {
            _errorMessage.value = "Error al cargar perfil: ${response.code()}"
        }
    }

    private suspend fun loadFavoriteBooks(bearer: String) {
        try {
            val response = apiService.getFavoritos(bearer)
            if (response.isSuccessful) {
                val favoritos = response.body() ?: emptyList()
                val librosFavoritos = favoritos.map { it.libro }  // <- aquí el mapeo correcto
                _favoriteBooks.value = librosFavoritos
                Log.d("ProfileViewModel", "Libros favoritos recibidos: $librosFavoritos")
            } else {
                _errorMessage.value = "Error al cargar favoritos: ${response.code()}"
            }
        } catch (e: Exception) {
            _errorMessage.value = "Error cargando libros favoritos"
            Log.e("ProfileViewModel", "Error en loadFavoriteBooks", e)
        }
    }

    private suspend fun loadUserReviews(bearer: String) {
        val response = apiService.getUserResenas(bearer)
        if (response.isSuccessful) {
            _userResenas.value = response.body() ?: emptyList()
        } else {
            _errorMessage.value = "Error al cargar reseñas: ${response.code()}"
        }
    }

    fun deleteResena(resenaId: Long) {
        val token = currentToken ?: return
        viewModelScope.launch {
            try {
                val response = apiService.deleteResena("Bearer $token", resenaId)
                if (response.isSuccessful) {
                    _userResenas.value = _userResenas.value.filter { it.id != resenaId }
                } else {
                    _errorMessage.value = "Error al eliminar reseña"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Error: ${e.localizedMessage}"
            }
        }
    }
}