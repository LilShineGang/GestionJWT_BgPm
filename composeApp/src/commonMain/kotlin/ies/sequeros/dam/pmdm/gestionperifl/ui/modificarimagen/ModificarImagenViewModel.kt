package ies.sequeros.dam.pmdm.gestionperifl.ui.modificarimagen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ies.sequeros.dam.pmdm.gestionperifl.application.user.changeimage.ChangeImageUseCase
import io.github.vinceglb.filekit.PlatformFile
import io.github.vinceglb.filekit.name
import io.github.vinceglb.filekit.readBytes
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ModificarImagenViewModel(private val changeImageUseCase: ChangeImageUseCase) : ViewModel() {
    var selectedFile by mutableStateOf<PlatformFile?>(null)
    var isLoading by mutableStateOf(false)
    var errorMessage by mutableStateOf<String?>(null)
    var success by mutableStateOf(false)

    fun onFileSelected(file: PlatformFile?) {
        selectedFile = file
        errorMessage = null
        success = false
    }

    fun onConfirm() {
        val file = selectedFile ?: return
        isLoading = true
        errorMessage = null
        success = false
        CoroutineScope(Dispatchers.Main).launch {
            try {
                val bytes = file.readBytes()
                val response = changeImageUseCase(bytes, file.name ?: "profile.jpg")
                if (response != null) {
                    success = true
                } else {
                    errorMessage = "No se pudo actualizar la imagen."
                }
            } catch (e: Exception) {
                errorMessage = e.message
            } finally {
                isLoading = false
            }
        }
    }
}
