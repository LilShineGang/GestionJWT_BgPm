package ies.sequeros.dam.pmdm.gestionperifl.ui.modificarusuario

import ies.sequeros.com.dam.pmdm.administrador.ui.productos.form.ComboBox

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun ModificarUsuarioScreen() {
    val viewModel = koinViewModel<ModificarUsuarioViewModel>()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Modificar Usuario", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(32.dp))

        // si carga sin nombre aún, dejo la ruedita
        if (viewModel.isLoading && viewModel.nombre.isEmpty()) {
            CircularProgressIndicator()
        } else {
            // nombre
            OutlinedTextField(
                value = viewModel.nombre,
                onValueChange = { viewModel.nombre = it },
                label = { Text("Nombre") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(Modifier.height(16.dp))

            // combobox hilado al que nos daba pedro
            ComboBox(
                items = viewModel.opcionesEstado,
                label = "Estado de la cuenta",
                current = viewModel.estado,
                itemLabel = { it },
                onSelect = { nuevoEstado -> viewModel.estado = nuevoEstado },
                editable = true
            )

            Spacer(Modifier.height(32.dp))

            // feedback
            if (viewModel.mensajeFeedback != null) {
                Text(
                    text = viewModel.mensajeFeedback!!,
                    color = if (viewModel.esError) Color.Red else Color(0xFF4CAF50),
                    modifier = Modifier.padding(bottom = 16.dp)
                )
            }

            // save
            Button(
                onClick = { viewModel.guardarCambios() },
                enabled = !viewModel.isLoading,
                modifier = Modifier.fillMaxWidth()
            ) {
                if (viewModel.isLoading) {
                    CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
                } else {
                    Text("Guardar Cambios")
                }
            }
        }
    }
}