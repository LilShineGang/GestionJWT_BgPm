package ies.sequeros.dam.pmdm.gestionperifl.ui.borrarusuario

import androidx.compose.runtime.*
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.DeleteCommand
import kotlinx.coroutines.launch

class BorrarUsuarioViewModel(private val api: AuthApi) : ViewModel() {
    var loading by mutableStateOf(false)
    var error by mutableStateOf<String?>(null)
    var success by mutableStateOf(false)

    fun borrarUsuario(password: String) {
        loading = true
        error = null
        success = false
        viewModelScope.launch {
            try {
                val result = api.deleteUser(DeleteCommand(password))
                success = result
                if (!result) error = "No se pudo borrar el usuario"
            } catch (e: Exception) {
                error = e.message
            } finally {
                loading = false
            }
        }
    }
}
