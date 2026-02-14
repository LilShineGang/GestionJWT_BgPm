package ies.sequeros.dam.pmdm.gestionperifl.ui.verusuario

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp

import coil3.compose.AsyncImage
import org.koin.compose.viewmodel.koinViewModel



@Composable
fun VerUsuarioScreen() {
    // Pillamos el ViewModel
    val viewModel = koinViewModel<VerUsuarioViewModel>()

    Column(
        modifier = Modifier.fillMaxSize().padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("Mi Perfil", style = MaterialTheme.typography.headlineMedium)

        Spacer(Modifier.height(32.dp))

        if (viewModel.isLoading) {
            CircularProgressIndicator() // pal circulito de pensar
        } else if (viewModel.errorMessage != null) {
            Text(text = viewModel.errorMessage!!, color = Color.Red)
        } else {
            // imagen (esperemos que no explote)
            if (viewModel.image != null) {
                AsyncImage(
                    model = viewModel.image,
                    contentDescription = "Foto de perfil",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape) // redondito me gusta
                        .background(Color.LightGray)
                )
            } else {
                // Si no tiene imagen, ponemos un circulo gris ratero con sus iniciales o texto
                Box(
                    modifier = Modifier
                        .size(120.dp)
                        .clip(CircleShape)
                        .background(Color.Gray),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Sin Foto", color = Color.White)
                }
            }

            Spacer(Modifier.height(24.dp))

            // Ahora si datos del usuario fuap
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text("Nombre de Usuario:", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                    Text(viewModel.name, style = MaterialTheme.typography.bodyLarge)

                    Spacer(Modifier.height(16.dp))

                    Text("Correo Electrónico:", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                    Text(viewModel.email, style = MaterialTheme.typography.bodyLarge)

                    Spacer(Modifier.height(16.dp))

                    Text("Estado de la Cuenta:", style = MaterialTheme.typography.labelMedium, color = Color.Gray)
                    // Le ponemos un colorcito dependiendo de si está activo o pending
                    val statusColor = if (viewModel.status == "active") Color(0xFF4CAF50) else Color(0xFFFF9800)
                    Text(viewModel.status.uppercase(), style = MaterialTheme.typography.bodyLarge, color = statusColor)
                }
            }
        }
    }
}
