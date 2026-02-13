package ies.sequeros.dam.pmdm.gestionperifl.ui.register

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.unit.dp

import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.annotation.KoinExperimentalAPI

@Composable
fun RegisterScreen(
    onNavigateToLogin: () -> Unit
) {
    val viewModel = koinViewModel<RegisterFormViewModel>()

    LaunchedEffect(viewModel.isRegisterSuccess) {
        onNavigateToLogin()

        viewModel.isRegisterSuccess = false
    }
    Column (
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // cuidado con el import de text que puede no ser el correcto
        Text("Registro", style = MaterialTheme.typography.headlineLarge) // headlineLarge??

        Spacer(Modifier.height(16.dp))

        // username
        OutlinedTextField(
            value = viewModel.username,
            onValueChange = { viewModel.username = it },
            label = { Text("Nombre de usuario") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // email
        OutlinedTextField(
            value = viewModel.email,
            onValueChange = {viewModel.email = it},
            label = { Text("Email") },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.password,
            onValueChange = { viewModel.password = it },
            label = { Text("Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(Modifier.height(8.dp))

        // confirmar contraseña
        OutlinedTextField(
            value = viewModel.confirmPassword,
            onValueChange = { viewModel.confirmPassword = it},
            label = { Text("Repetir Contraseña") },
            visualTransformation = PasswordVisualTransformation(),
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            isError = viewModel.password != viewModel.confirmPassword && viewModel.confirmPassword.isNotEmpty()
        )

        // por si no coinciden las contraseñas
        if (viewModel.password != viewModel.confirmPassword && viewModel.confirmPassword.isNotEmpty()) {
            Text("Las contraseñas no coinciden", color = Color.Red, style = MaterialTheme.typography.headlineSmall)
        }

        Spacer(Modifier.height(16.dp))

        // error por si por ej falla api
        if (viewModel.errorMessage != null) {
            Text(
                text = viewModel.errorMessage ?: "",
                color = Color.Red,
                modifier = Modifier.padding(bottom = 8.dp)
            )
        }

        // boton register (aviso por si no esta correcto el import del Button)
        Button(
            onClick = { viewModel.onRegister() },
            enabled = !viewModel.isLoading,
            modifier = Modifier.fillMaxWidth()
        ) {
            if (viewModel.isLoading) {
                CircularProgressIndicator(color = Color.White, modifier = Modifier.size(24.dp))
            } else {
                Text("Registrar")
            }
        }

        Spacer(Modifier.height(8.dp))

        // volver al login
        TextButton(onClick = onNavigateToLogin) {
            Text("¿Ya tienes una cuenta? Inicia sesión")
        }
    }
}

