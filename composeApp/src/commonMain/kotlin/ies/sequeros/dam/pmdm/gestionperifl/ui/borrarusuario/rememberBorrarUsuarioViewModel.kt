package ies.sequeros.dam.pmdm.gestionperifl.ui.borrarusuario

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi

@Composable
fun rememberBorrarUsuarioViewModel(api: AuthApi): BorrarUsuarioViewModel {
    return remember(api) { BorrarUsuarioViewModel(api) }
}
