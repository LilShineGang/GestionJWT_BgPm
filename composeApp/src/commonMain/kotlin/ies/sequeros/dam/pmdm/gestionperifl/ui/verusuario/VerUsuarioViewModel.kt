package ies.sequeros.dam.pmdm.gestionperifl.ui.verusuario

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

import ies.sequeros.dam.pmdm.gestionperifl.application.auth.VerUsuarioUseCase


class VerUsuarioViewModel(
    private val verUsuarioUseCase: VerUsuarioUseCase
) : ViewModel() {

    var name by mutableStateOf("")
    var email by mutableStateOf("")
    var image by mutableStateOf<String?>(null)
    var status by mutableStateOf("Cargando...")

    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)

    init {
        cargarDatos()
    }

    private fun cargarDatos() {
        viewModelScope.launch {
            isLoading = true
            errorMessage = null

            val perfil = verUsuarioUseCase.execute()

            if (perfil != null) {
                name = perfil.name ?: "Sin Nombre"
                email = perfil.email
                image = perfil.image
                status = perfil.status
            } else {
                errorMessage = "No se ha podido cargar el perfil."
            }

            isLoading = false
        }
    }
}