package com.example.bibliotecadigitalappd.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.auth0.android.jwt.JWT
import com.example.bibliotecadigitalappd.data.local.datastore.DataStoreManager
import com.example.bibliotecadigitalappd.data.remote.api.ApiService
import com.example.bibliotecadigitalappd.data.remote.dtos.LoginRequest
import com.example.bibliotecadigitalappd.data.remote.dtos.RegisterRequest
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import android.util.Log

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val apiService: ApiService,
    private val dataStoreManager: DataStoreManager
) : ViewModel() {

    var isLoggedIn by mutableStateOf(false)
        private set

    var loginState by mutableStateOf(LoginState())
        private set

    var registrationState by mutableStateOf(RegistrationState())
        private set

    private val _authEvents = MutableSharedFlow<AuthEvent>()
    val authEvents = _authEvents.asSharedFlow()

    private val _userRole = MutableStateFlow("")
    val userRole: StateFlow<String> = _userRole

    // Realiza login con email y contraseña
    fun login(email: String, contraseña: String) {
        viewModelScope.launch {
            loginState = loginState.copy(isLoading = true, error = null)
            try {
                val response = apiService.login(LoginRequest(email, contraseña))
                if (response.isSuccessful) {
                    val authResponse = response.body()
                    if (authResponse?.token != null) {
                        dataStoreManager.saveToken(authResponse.token)
                        val role = extractUserRole(authResponse.token)
                        Log.d("AuthViewModel", "Rol extraído del token: $role")  // LOG añadido
                        _userRole.value = role ?: ""
                        isLoggedIn = true
                        loginState = loginState.copy(isSuccess = true)
                        _authEvents.emit(AuthEvent.NavigateToCatalog)
                    } else {
                        loginState = loginState.copy(error = "Token no recibido")
                    }
                } else {
                    loginState = loginState.copy(
                        error = "Error ${response.code()}: ${response.errorBody()?.string() ?: "Error desconocido"}"
                    )
                }
            } catch (e: IOException) {
                loginState = loginState.copy(error = "Error de conexión: ${e.localizedMessage}")
            } catch (e: HttpException) {
                loginState = loginState.copy(error = "Error HTTP: ${e.localizedMessage}")
            } finally {
                loginState = loginState.copy(isLoading = false)
            }
        }
    }

    // Registro de usuario
    fun register(nombre: String, email: String, contraseña: String, rol: String = "USER") {
        viewModelScope.launch {
            registrationState = registrationState.copy(isLoading = true, error = null)
            try {
                val response = apiService.register(RegisterRequest(nombre, email, contraseña, rol))
                if (response.isSuccessful) {
                    registrationState = registrationState.copy(isSuccess = true)
                    _authEvents.emit(AuthEvent.ShowRegistrationSuccess)
                } else {
                    registrationState = registrationState.copy(
                        error = "Error ${response.code()}: ${response.errorBody()?.string() ?: "Error desconocido"}"
                    )
                }
            } catch (e: IOException) {
                registrationState = registrationState.copy(error = "Error de conexión: ${e.localizedMessage}")
            } catch (e: HttpException) {
                registrationState = registrationState.copy(error = "Error HTTP: ${e.localizedMessage}")
            } finally {
                registrationState = registrationState.copy(isLoading = false)
            }
        }
    }

    // Logout
    fun logout() {
        viewModelScope.launch {
            dataStoreManager.clearToken()
            isLoggedIn = false
            _userRole.value = ""
            loginState = LoginState()
            _authEvents.emit(AuthEvent.NavigateToLogin)
        }
    }

    // Extrae el rol del token JWT
    private fun extractUserRole(token: String): String? {
        return try {
            val jwt = JWT(token)
            val roleWithPrefix = jwt.getClaim("rol").asString()
            roleWithPrefix?.removePrefix("ROLE_")
        } catch (e: Exception) {
            null
        }
    }


}

data class LoginState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

data class RegistrationState(
    val isLoading: Boolean = false,
    val isSuccess: Boolean = false,
    val error: String? = null
)

sealed class AuthEvent {
    object NavigateToCatalog : AuthEvent()
    object NavigateToLogin : AuthEvent()
    object ShowRegistrationSuccess : AuthEvent()
}