package ies.sequeros.dam.pmdm.gestionperifl

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.graphics.Color

private val LightColors = lightColorScheme(
    primary = Color(0xFF9B7BB8), // Morado suave
    onPrimary = Color.White,
    secondary = Color(0xFF7B5B9B),
    onSecondary = Color.White,
    tertiary = Color(0xFFB8A3D1),
    onTertiary = Color.White,
    background = Color(0xFFFDFDFD),
    onBackground = Color(0xFF1C1B1F),
    surface = Color(0xFFF5F0FA),
    onSurface = Color(0xFF1C1B1F),
    surfaceVariant = Color(0xFFE8E0F0),
    onSurfaceVariant = Color(0xFF49454F)
)

private val DarkColors = darkColorScheme(
    primary = Color(0xFFB8A3D1), // Morado suave claro
    onPrimary = Color(0xFF2D1F3D),
    secondary = Color(0xFFD4C3E8),
    onSecondary = Color(0xFF1F152D),
    tertiary = Color(0xFF9B7BB8),
    onTertiary = Color.White,
    background = Color(0xFF1C1B1F),
    onBackground = Color(0xFFE5E1E6),
    surface = Color(0xFF2D2640),
    onSurface = Color(0xFFE5E1E6),
    surfaceVariant = Color(0xFF3D3555),
    onSurfaceVariant = Color(0xFFCAC4D0)
)

@Composable
fun AppTheme(
    darkTheme: State<Boolean>,
    content: @Composable () -> Unit
) {
    val colors = if (darkTheme.value) DarkColors else LightColors

    MaterialTheme(
        colorScheme = colors,

        content = content
    )
}