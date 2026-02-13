package ies.sequeros.dam.pmdm.gestionperifl.ui.borrarusuario

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import org.koin.compose.koinInject
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.auth.TokenStorage

@Composable
fun BorrarUsuarioScreen(viewModel: BorrarUsuarioViewModel, onLogout: (() -> Unit)? = null) {
    var password by remember { mutableStateOf("") }
    val tokenStorage: TokenStorage = koinInject()

    // Cerrar sesión automáticamente si éxito
    LaunchedEffect(viewModel.success) {
        if (viewModel.success) {
            tokenStorage.clear()
            onLogout?.invoke()
        }
    }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("Borrar Usuario", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { viewModel.borrarUsuario(password) },
            enabled = !viewModel.loading && password.isNotBlank()
        ) {
            Text("Borrar cuenta")
        }
        if (viewModel.loading) {
            CircularProgressIndicator(Modifier.padding(top = 16.dp))
        }
        viewModel.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 16.dp))
        }
        if (viewModel.success) {
            Text("Usuario borrado correctamente", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 16.dp))
        }
    }
}
