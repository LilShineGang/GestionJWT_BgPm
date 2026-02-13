package ies.sequeros.dam.pmdm.gestionperifl.ui.login

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue

import org.koin.compose.viewmodel.koinViewModel
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.unit.dp
import ies.sequeros.dam.pmdm.gestionperifl.ui.components.login.LoginComponent

@Composable
fun LoginScreen(
    onLogin: () -> Unit,
    onCancel: () -> Unit,
    onRegister: () -> Unit
) {
    val viewModel = koinViewModel<LoginFormViewModel>()
    //estado del formulario que es el del LoginComponent
    val state by viewModel.state.collectAsState()
    //cuando el estado pasa a ser correcto, se avisa al padre
    LaunchedEffect(state.isLoginSuccess) {
        if (state.isLoginSuccess) {
            onLogin()
        }
    }

    androidx.compose.foundation.layout.Column {
        LoginComponent(
            state,
            viewModel::onEmailChange,
            viewModel::onPasswordChange,
            {
                viewModel.login()
            },
            {
                onCancel()
            },
            {
                onRegister()
            }
        )
    }


}