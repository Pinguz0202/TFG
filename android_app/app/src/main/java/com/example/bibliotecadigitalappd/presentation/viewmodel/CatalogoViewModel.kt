package com.example.bibliotecadigitalappd.presentation.viewmodel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotecadigitalappd.data.local.datastore.DataStoreManager
import com.example.bibliotecadigitalappd.data.remote.api.ApiService
import com.example.bibliotecadigitalappd.data.remote.dtos.LibroDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatalogoViewModel @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _libros = MutableStateFlow<List<LibroDto>>(emptyList())
    val libros: StateFlow<List<LibroDto>> = _libros

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error

    fun cargarLibros() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null

            try {
                val token = dataStoreManager.getToken().first()

                if (!token.isNullOrBlank()) {
                    val response = apiService.getLibros("Bearer $token")

                    if (response.isSuccessful) {
                        _libros.value = response.body() ?: emptyList()
                    } else {
                        _error.value = "Error ${response.code()}: ${response.errorBody()?.string()}"
                    }
                } else {
                    _error.value = "No hay token de autenticación"
                }

            } catch (e: Exception) {
                _error.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}