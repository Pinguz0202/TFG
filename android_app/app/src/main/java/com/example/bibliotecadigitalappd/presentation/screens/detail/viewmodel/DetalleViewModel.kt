package com.example.bibliotecadigitalappd.presentation.screens.detail.viewmodel

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotecadigitalappd.data.remote.api.ApiService
import com.example.bibliotecadigitalappd.data.remote.dtos.LibroDto
import com.example.bibliotecadigitalappd.data.remote.dtos.ResenaDto
import com.example.bibliotecadigitalappd.data.remote.dtos.ResenaRequest
import com.example.bibliotecadigitalappd.data.local.datastore.DataStoreManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetalleViewModel @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    private val _libro = mutableStateOf<LibroDto?>(null)
    val libro: State<LibroDto?> = _libro

    private val _reseñas = mutableStateListOf<ResenaDto>()
    val reseñas: List<ResenaDto> = _reseñas

    private val _isLoading = mutableStateOf(false)
    val isLoading: State<Boolean> = _isLoading

    private val _error = mutableStateOf<String?>(null)
    val error: State<String?> = _error

    private val _esFavorito = mutableStateOf(false)
    val esFavorito: State<Boolean> = _esFavorito

    fun cargarDetalleLibro(isbn: String) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val rawToken = dataStoreManager.getToken().first()
                if (rawToken == null) {
                    _error.value = "No autenticado. Por favor, inicia sesión."
                    _isLoading.value = false
                    return@launch
                }
                // Añade el prefijo "Bearer "
                val prefixedToken = "Bearer $rawToken"

                val responseLibro = apiService.getLibroDetalle(prefixedToken, isbn) // Usando prefixedToken
                if (responseLibro.isSuccessful) {
                    _libro.value = responseLibro.body()
                } else {
                    _error.value = "Error al cargar detalle del libro: ${responseLibro.message()}"
                }

                // Las siguientes llamadas a funciones también necesitan el token prefijado.
                // Es mejor que cada función obtenga su propio token o se lo pasen como parámetro
                // para asegurar que siempre se use el más reciente y con el formato correcto.
                cargarResenasDelLibro(isbn)
                checkFavorito(isbn)

            } catch (e: Exception) {
                _error.value = "Error de conexión: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun cargarResenasDelLibro(isbn: String) {
        viewModelScope.launch {
            val rawToken = dataStoreManager.getToken().first()
            if (rawToken == null) {
                _error.value = "No autenticado para cargar reseñas."
                return@launch
            }
            val prefixedToken = "Bearer $rawToken" // Añade el prefijo "Bearer "
            try {
                val response = apiService.getResenasLibro(prefixedToken, isbn) // Usando prefixedToken
                if (response.isSuccessful) {
                    _reseñas.clear()
                    response.body()?.let { _reseñas.addAll(it) }
                } else {
                    _error.value = "Error al cargar reseñas: ${response.message()}"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión al cargar reseñas: ${e.message}"
            }
        }
    }

    fun enviarResena(isbnLibro: String, comentario: String, puntuacion: Int) {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            try {
                val rawToken = dataStoreManager.getToken().first()
                if (rawToken == null) {
                    _error.value = "No autenticado para enviar reseña. Inicia sesión."
                    _isLoading.value = false
                    return@launch
                }
                val prefixedToken = "Bearer $rawToken" // Añade el prefijo "Bearer "

                val request = ResenaRequest(isbnLibro, puntuacion, comentario)
                val response = apiService.crearOActualizarResena(prefixedToken, request) // Usando prefixedToken

                if (response.isSuccessful) {
                    _error.value = null
                    cargarResenasDelLibro(isbnLibro)
                } else {
                    val errorBody = response.errorBody()?.string() ?: "Error desconocido"
                    _error.value = "Error al enviar reseña: ${response.code()} - $errorBody"
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión al enviar reseña: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun toggleFavorito(libroId: String) {
        viewModelScope.launch {
            val rawToken = dataStoreManager.getToken().first()
            if (rawToken == null) {
                _error.value = "No autenticado."
                return@launch
            }
            val prefixedToken = "Bearer $rawToken"
            try {
                if (_esFavorito.value) {
                    // Si ya es favorito, quitarlo y actualizar el estado inmediatamente
                    val response = apiService.eliminarFavorito(prefixedToken, libroId)
                    if (response.isSuccessful) {
                        _esFavorito.value = false
                    } else {
                        _error.value = "Error al eliminar favorito: ${response.message()}"
                    }
                } else {
                    // Si no es favorito, agregarlo y actualizar el estado inmediatamente
                    val response = apiService.agregarFavorito(prefixedToken, libroId)
                    if (response.isSuccessful) {
                        _esFavorito.value = true
                    } else {
                        _error.value = "Error al agregar favorito: ${response.message()}"
                    }
                }
            } catch (e: Exception) {
                _error.value = "Error de conexión al gestionar favorito: ${e.message}"
            }
        }
    }

    fun checkFavorito(libroId: String) {
        viewModelScope.launch {
            try {
                val rawToken = dataStoreManager.getToken().first()
                val prefixedToken = if (rawToken != null) "Bearer $rawToken" else null

                if (prefixedToken == null) {
                    _esFavorito.value = false
                    return@launch
                }

                val response = apiService.checkFavorito(prefixedToken, libroId)
                _esFavorito.value = response.isSuccessful && (response.body() == true)
            } catch (e: Exception) {
                _esFavorito.value = false
            }
        }
    }
}