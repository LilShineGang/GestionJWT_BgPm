package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor

import kotlinx.serialization.Serializable

@Serializable
data class RefreshRequest(
    val refresh_token: String,
)

@Serializable
data class LoginRequest(
    val email: String,
    val password: String,
)

@Serializable
data class AuthTokensResponse(
    val access_token: String,
    val id_token: String? = null,
    val expires_in: Long? = null,
    val token_type: String? = null,
    val refresh_token: String,
)
@Serializable
data class RegisterRequest(
    val username: String,
    val email: String,
    val password: String
)

// para pillar datos del perfil
@Serializable
data class ProfileResponse(
    val id: String,
    val name: String? = null,
    val email: String,
    val image: String? = null,
    val status: String
)