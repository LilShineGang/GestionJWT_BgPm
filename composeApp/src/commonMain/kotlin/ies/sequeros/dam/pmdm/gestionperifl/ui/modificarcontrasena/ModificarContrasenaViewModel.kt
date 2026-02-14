package ies.sequeros.dam.pmdm.gestionperifl.ui.modificarcontrasena

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.user.changepassword.ChangePasswordUseCase
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.comand.ChangePasswordCommand
import kotlinx.coroutines.launch

class ModificarContrasenaViewModel(private val useCase: ChangePasswordUseCase) : ViewModel() {
    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var success by mutableStateOf(false)

    fun modificarContrasena(oldPassword: String, newPassword: String) {
        loading = true
        error = null
        success = false
        viewModelScope.launch {
            try {
                val result = useCase(ChangePasswordCommand(oldPassword, newPassword))
                success = result
                if (!result) error = "No se pudo cambiar la contraseña"
            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }
}
