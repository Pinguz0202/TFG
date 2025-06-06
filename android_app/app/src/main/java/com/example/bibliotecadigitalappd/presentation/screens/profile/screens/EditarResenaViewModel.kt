package com.example.bibliotecadigitalappd.presentation.screens.profile.screens

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bibliotecadigitalappd.data.repository.ResenaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class EditarResenaViewModel @Inject constructor(
    private val resenaRepository: ResenaRepository
): ViewModel() {

    private val _puntuacion = MutableStateFlow(0f)
    val puntuacion: StateFlow<Float> = _puntuacion

    private val _comentario = MutableStateFlow("")
    val comentario: StateFlow<String> = _comentario

    private val _isbnLibro = MutableStateFlow("")
    val isbnLibro: StateFlow<String> = _isbnLibro

    private val _loading = MutableStateFlow(false)
    val loading: StateFlow<Boolean> = _loading

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage

    fun loadResena(id: Long) {
        viewModelScope.launch {
            _loading.value = true
            try {
                val resena = resenaRepository.getResenaById(id)
                _puntuacion.value = resena.puntuacion.toFloat()
                _comentario.value = resena.comentario ?: ""
                _isbnLibro.value = resena.isbnLibro ?: "" // Aquí asignamos el ISBN directo
            } catch (e: Exception) {
                _errorMessage.value = "Error al cargar la reseña"
            }
            _loading.value = false
        }
    }

    fun onPuntuacionChange(nueva: Float) {
        _puntuacion.value = nueva
    }

    fun onComentarioChange(nuevo: String) {
        _comentario.value = nuevo
    }

    fun guardarCambios(id: Long, onSuccess: () -> Unit, onError: (String) -> Unit) {
        viewModelScope.launch {
            _loading.value = true
            try {
                resenaRepository.updateResena(
                    id,
                    isbnLibro = _isbnLibro.value,
                    puntuacion = _puntuacion.value.toInt(),
                    comentario = _comentario.value
                )
                onSuccess()
            } catch (e: Exception) {
                onError("Error al guardar la reseña")
            }
            _loading.value = false
        }
    }
}