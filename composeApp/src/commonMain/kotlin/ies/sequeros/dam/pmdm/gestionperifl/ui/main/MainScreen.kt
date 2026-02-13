package ies.sequeros.dam.pmdm.gestionperifl.ui.main

import androidx.compose.runtime.Composable
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel

// ViewModel para MainScreen
class MainViewModel : ViewModel() {
    // Aquí puedes añadir estado y lógica
}

// View para MainScreen
@Composable
fun MainScreen(viewModel: MainViewModel, onLogout: () -> Unit) {
    Row(Modifier.fillMaxSize()) {
        // Sidebar dummy
        Column(
            Modifier.width(180.dp).fillMaxHeight().padding(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text("Menú", style = MaterialTheme.typography.titleMedium)
            Spacer(Modifier.height(16.dp))
            Button(onClick = { /* Dummy: no navega */ }) { Text("Productos (dummy)") }
            Button(onClick = { /* Dummy: no navega */ }) { Text("Categorías (dummy)") }
            Spacer(Modifier.weight(1f))
            Button(onClick = onLogout) { Text("Cerrar sesión") }
        }
        // Contenido principal dummy
        Column(
            Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Pantalla principal (dummy)")
        }
    }
}
