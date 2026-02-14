package ies.sequeros.dam.pmdm.gestionperifl.ui.register

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.auth.RegisterUseCase

import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.RegisterRequest
import kotlinx.coroutines.launch

class RegisterFormViewModel(
    private val registerUseCase: RegisterUseCase
) : ViewModel() {

    // inputs
    var username by mutableStateOf("")
    var email by mutableStateOf("")
    var password by mutableStateOf("")
    var confirmPassword by mutableStateOf("")

    // control UI
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var isRegisterSuccess by mutableStateOf(false)

    fun onRegister() {
        errorMessage = null // errores --

        //validaciones
        if (username.isBlank() || email.isBlank() || password.isBlank()) {
            errorMessage = "No puedes dejar campos vacíos"
            return
        }
        if (password != confirmPassword) {
            errorMessage = "Las contraseñas no coinciden"
            return
        }
        if (password.length < 4) {
            errorMessage = "Pon una contraseña un poco mas larguita, mas de 4 si es posible"
            return
        }

        // llamada api
        viewModelScope.launch {
            isLoading = true
            try {
                val request = RegisterRequest(
                    username = username,
                    email = email,
                    password = password
                )

                val success = registerUseCase.execute(request)

                if (success) {
                    print("Registro Correcto")
                    isRegisterSuccess = true
                } else {
                    print("Error al registrar")
                }
            } catch (e: Exception) {
                errorMessage = "Fallo de conexión: ${e.message}"
            } finally {
                isLoading = false
            }
        }
    }

}