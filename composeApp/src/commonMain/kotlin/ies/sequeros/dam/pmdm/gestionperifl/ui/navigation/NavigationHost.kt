package ies.sequeros.dam.pmdm.gestionperifl.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.login.LoginScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.main.MainScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.main.MainViewModel
import org.koin.compose.viewmodel.koinViewModel

enum class Screen { LOGIN, REGISTER, MAIN }

@Composable
fun NavigationHost() {
    var currentScreen by remember { mutableStateOf(Screen.LOGIN) }

    when (currentScreen) {
        Screen.LOGIN -> LoginScreen(
            onLogin = { currentScreen = Screen.MAIN },
            onRegister = { currentScreen = Screen.REGISTER },
            onCancel = { currentScreen = Screen.LOGIN }
        )
        Screen.REGISTER -> RegisterScreen(
            onRegisterSuccess = { currentScreen = Screen.MAIN },
            onCancel = { currentScreen = Screen.LOGIN }
        )
        Screen.MAIN -> {
            val mainViewModel: MainViewModel = koinViewModel()
            MainScreen(mainViewModel, onLogout = { currentScreen = Screen.LOGIN })
        }
    }
@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onRegister: () -> Unit,
    onCancel: () -> Unit
) {
    Column(androidx.compose.ui.Modifier.fillMaxSize(), horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        androidx.compose.material3.Text("Pantalla de login")
        androidx.compose.material3.Button(onClick = onLogin) { androidx.compose.material3.Text("Entrar (dummy)") }
        androidx.compose.material3.Button(onClick = onRegister) { androidx.compose.material3.Text("Ir a registro") }
        androidx.compose.material3.Button(onClick = onCancel) { androidx.compose.material3.Text("Cancelar") }
    }
}
}

@Composable
fun RegisterScreen(onRegisterSuccess: () -> Unit, onCancel: () -> Unit) {
    Column(androidx.compose.ui.Modifier.fillMaxSize(), horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally) {
        androidx.compose.material3.Text("Pantalla de registro")
        androidx.compose.material3.Button(onClick = { onRegisterSuccess() }) { androidx.compose.material3.Text("Registrar y entrar (dummy)") }
        androidx.compose.material3.Button(onClick = onCancel) { androidx.compose.material3.Text("Cancelar") }
    }
}
