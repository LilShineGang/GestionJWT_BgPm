package ies.sequeros.dam.pmdm.gestionperifl.ui.navigation

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.login.LoginScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.main.MainScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.main.MainViewModel
import org.koin.compose.viewmodel.koinViewModel
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.auth.TokenStorage
import org.koin.compose.koinInject
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.TokenJwt
enum class Screen { LOGIN, REGISTER, MAIN }

@Composable
fun NavigationHost() {
    val tokenStorage: TokenStorage = koinInject()
    var currentScreen by rememberSaveable { mutableStateOf<Screen?>(null) }
    var didAutoLogin by rememberSaveable { mutableStateOf(false) }

    // Autologin: solo al iniciar la app
    LaunchedEffect(Unit) {
        if (!didAutoLogin) {
            val token = tokenStorage.getAccessToken()
            currentScreen = if (token != null) {
                try {
                    val jwt = TokenJwt(token)
                    if (jwt.isSessionValid()) Screen.MAIN else Screen.LOGIN
                } catch (_: Exception) {
                    Screen.LOGIN
                }
            } else {
                Screen.LOGIN
            }
            didAutoLogin = true
        }
    }

    when (currentScreen) {
        Screen.LOGIN -> LoginScreen(
            onLogin = { currentScreen = Screen.MAIN },
            onRegister = { currentScreen = Screen.REGISTER },
            onCancel = { currentScreen = Screen.LOGIN }
        )
        Screen.REGISTER -> ies.sequeros.dam.pmdm.gestionperifl.ui.register.RegisterScreen(
            onNavigateToLogin = { currentScreen = Screen.LOGIN }
        )
        Screen.MAIN -> {
            val mainViewModel: MainViewModel = koinViewModel()
            MainScreen(mainViewModel, onLogout = {
                tokenStorage.clear()
                currentScreen = Screen.LOGIN
            })
        }
        null -> {} // Esperando comprobación
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
