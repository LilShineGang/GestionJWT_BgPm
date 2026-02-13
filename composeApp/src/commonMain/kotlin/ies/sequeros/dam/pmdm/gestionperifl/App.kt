package ies.sequeros.dam.pmdm.gestionperifl
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import org.jetbrains.compose.resources.painterResource

import gestionjwt.composeapp.generated.resources.Res
import gestionjwt.composeapp.generated.resources.compose_multiplatform
import ies.sequeros.dam.pmdm.gestionperifl.AppTheme
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.navigation.NavigationHost
import org.koin.compose.viewmodel.koinViewModel



@Composable
@Preview
fun App() {
    val appViewModel: AppViewModel = koinViewModel()
    AppTheme(appViewModel.isDarkMode.collectAsState()) {
        NavigationHost()
    }
}


