package ies.sequeros.dam.pmdm.gestionperifl.ui.modificarusuario

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import ies.sequeros.dam.pmdm.gestionperifl.application.auth.VerUsuarioUseCase
import ies.sequeros.dam.pmdm.gestionperifl.application.auth.ModificarUsuarioUseCase
import kotlinx.coroutines.launch

class ModificarUsuarioViewModel(
    private val verUsuarioUseCase: VerUsuarioUseCase, // datos antiguos
    private val modificarUsuarioUseCase: ModificarUsuarioUseCase // nuevos datos
) : ViewModel() {

    var nombre by mutableStateOf("")
    var estado by mutableStateOf("pending")

    // lista estados
    val opcionesEstado = listOf("active", "pending", "inactive", "suspended")

    var isLoading by mutableStateOf(false)
    var mensajeFeedback by mutableStateOf<String?>(null) // si ok o no
    var esError by mutableStateOf(false) // entonces rojo o verde

    init {
        cargarDatosActuales()
    }

    private fun cargarDatosActuales() {
        viewModelScope.launch {
            isLoading = true
            val perfil = verUsuarioUseCase.execute()
            if (perfil != null) {
                nombre = perfil.name ?: ""
                estado = perfil.status
            }
            isLoading = false
        }
    }

    fun guardarCambios() {
        if (nombre.isBlank()) {
            mensajeFeedback = "El nombre no puede estar vacío"
            esError = true
            return
        }

        viewModelScope.launch {
            isLoading = true
            mensajeFeedback = null

            val exito = modificarUsuarioUseCase.execute(nombre, estado)

            if (exito) {
                mensajeFeedback = "¡Usuario modificado con éxito!"
                esError = false
            } else {
                mensajeFeedback = "Error al guardar los cambios."
                esError = true
            }
            isLoading = false
        }
    }
}