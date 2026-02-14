package ies.sequeros.dam.pmdm.gestionperifl.ui.modificarimagen

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.collectAsState
import org.koin.compose.koinInject
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.ImagePickerPreviewComponent
import ies.sequeros.dam.pmdm.gestionperifl.ui.modificarimagen.ModificarImagenViewModel
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun ModificarImagenScreen(viewModel: ModificarImagenViewModel = koinInject()) {
    val selectedFile = viewModel.selectedFile
    val isLoading = viewModel.isLoading
    val errorMessage = viewModel.errorMessage
    val success = viewModel.success

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Modificar Imagen", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        ImagePickerPreviewComponent(
            imageUrl = null, // Puedes pasar la URL actual del usuario si la tienes
            selectedFile = selectedFile,
            onFileSelected = { viewModel.onFileSelected(it) },
            onConfirm = { viewModel.onConfirm() }
        )
        if (isLoading) {
            CircularProgressIndicator(Modifier.padding(top = 16.dp))
        }
        if (errorMessage != null) {
            Text(errorMessage, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 8.dp))
        }
        if (success) {
            Text("Imagen actualizada correctamente", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 8.dp))
        }
    }
}
