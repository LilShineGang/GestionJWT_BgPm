package ies.sequeros.dam.pmdm.gestionperifl.ui.navigation

import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
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
}
