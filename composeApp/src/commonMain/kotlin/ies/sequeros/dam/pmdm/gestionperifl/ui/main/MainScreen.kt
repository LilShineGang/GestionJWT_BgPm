package ies.sequeros.dam.pmdm.gestionperifl.ui.main

import androidx.compose.foundation.background
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Image
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import ies.sequeros.dam.pmdm.gestionperifl.ui.verusuario.VerUsuarioScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.modificarusuario.ModificarUsuarioScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.borrarusuario.BorrarUsuarioScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.modificarcontrasena.ModificarContrasenaScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.modificarimagen.ModificarImagenScreen
import ies.sequeros.dam.pmdm.gestionperifl.ui.home.HomeScreen

sealed class Destino(val ruta: String) {
    object Home : Destino("home")
    object VerUsuario : Destino("ver_usuario")
    object ModificarUsuario : Destino("modificar_usuario")
    object BorrarUsuario : Destino("borrar_usuario")
    object ModificarContrasena : Destino("modificar_contrasena")
    object ModificarImagen : Destino("modificar_imagen")
}

class MainViewModel : ViewModel() {
    // Hacer luego?
}

@Composable
fun MainScreen(viewModel: MainViewModel, onLogout: () -> Unit) {
    val navController = rememberNavController()

    Row(Modifier.fillMaxSize()) {
        // --- Sidebar ---
        Column(
            Modifier
                .width(180.dp)
                .fillMaxHeight()
                .background(MaterialTheme.colorScheme.surfaceVariant)
                .padding(12.dp),
            horizontalAlignment = Alignment.Start
        ) {

            Text(
                text = "MENU",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )

            HorizontalDivider(
                color = Color.LightGray,
                thickness = 1.dp,
                modifier = Modifier.padding(vertical = 12.dp)
            )

            // Home
            Button(
                onClick = { navController.navigate(Destino.Home.ruta) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Home, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Inicio")
            }
            Spacer(Modifier.height(8.dp))

            // Ver usuario
            Button(
                onClick = { navController.navigate(Destino.VerUsuario.ruta) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Person, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Ver usuario")
            }
            Spacer(Modifier.height(8.dp))

            // Modificar usuario
            Button(
                onClick = { navController.navigate(Destino.ModificarUsuario.ruta) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Edit, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Modificar usuario")
            }
            Spacer(Modifier.height(8.dp))

            // Borrar usuario
            Button(
                onClick = { navController.navigate(Destino.BorrarUsuario.ruta) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Delete, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Borrar usuario")
            }
            Spacer(Modifier.height(8.dp))

            // Modificar contrasena
            Button(
                onClick = { navController.navigate(Destino.ModificarContrasena.ruta) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Lock, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Modificar contrasena")
            }
            Spacer(Modifier.height(8.dp))

            // Modificar imagen
            Button(
                onClick = { navController.navigate(Destino.ModificarImagen.ruta) },
                modifier = Modifier.fillMaxWidth(),
                shape = MaterialTheme.shapes.medium
            ) {
                Icon(Icons.Default.Image, contentDescription = null)
                Spacer(Modifier.width(8.dp))
                Text("Modificar imagen")
            }

            Spacer(Modifier.weight(1f))

            Button(
                onClick = onLogout,
                shape = MaterialTheme.shapes.medium,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Cerrar sesion")
            }
        }

        Column(
            Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            NavHost(
                navController = navController,
                startDestination = Destino.Home.ruta
            ) {
                composable(Destino.Home.ruta) { HomeScreen() }
                composable(Destino.VerUsuario.ruta) { VerUsuarioScreen() }
                composable(Destino.ModificarUsuario.ruta) { ModificarUsuarioScreen() }
                composable(Destino.BorrarUsuario.ruta) { BorrarUsuarioScreen() }
                composable(Destino.ModificarContrasena.ruta) { ModificarContrasenaScreen() }
                composable(Destino.ModificarImagen.ruta) { ModificarImagenScreen() }
            }
        }
    }
}