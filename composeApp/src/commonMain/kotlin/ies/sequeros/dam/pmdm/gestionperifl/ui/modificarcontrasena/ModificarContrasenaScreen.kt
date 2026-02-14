package ies.sequeros.dam.pmdm.gestionperifl.ui.modificarcontrasena

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.material3.OutlinedTextField
import androidx.compose.ui.text.input.PasswordVisualTransformation
import org.koin.compose.koinInject
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.auth.TokenStorage
import ies.sequeros.dam.pmdm.gestionperifl.application.user.changepassword.ChangePasswordUseCase
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi
import androidx.compose.runtime.remember

@Composable
fun ModificarContrasenaScreen(onLogout: (() -> Unit)? = null) {
    val api = koinInject<AuthApi>()
    val useCase = remember { ChangePasswordUseCase(api) }
    val viewModel = remember { ModificarContrasenaViewModel(useCase) }
    var oldPassword by remember { mutableStateOf("") }
    var newPassword by remember { mutableStateOf("") }
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
        Text("Modificar Contraseña", style = MaterialTheme.typography.headlineMedium)
        Spacer(Modifier.height(16.dp))
        OutlinedTextField(
            value = oldPassword,
            onValueChange = { oldPassword = it },
            label = { Text("Contraseña actual") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(8.dp))
        OutlinedTextField(
            value = newPassword,
            onValueChange = { newPassword = it },
            label = { Text("Nueva contraseña") },
            visualTransformation = PasswordVisualTransformation()
        )
        Spacer(Modifier.height(16.dp))
        Button(
            onClick = { viewModel.modificarContrasena(oldPassword, newPassword) },
            enabled = !viewModel.loading && oldPassword.isNotBlank() && newPassword.isNotBlank()
        ) {
            Text("Cambiar contraseña")
        }
        if (viewModel.loading) {
            CircularProgressIndicator(Modifier.padding(top = 16.dp))
        }
        viewModel.error?.let {
            Text(it, color = MaterialTheme.colorScheme.error, modifier = Modifier.padding(top = 16.dp))
        }
        if (viewModel.success) {
            Text("Contraseña cambiada correctamente. Se cerrará la sesión.", color = MaterialTheme.colorScheme.primary, modifier = Modifier.padding(top = 16.dp))
        }
    }
}
