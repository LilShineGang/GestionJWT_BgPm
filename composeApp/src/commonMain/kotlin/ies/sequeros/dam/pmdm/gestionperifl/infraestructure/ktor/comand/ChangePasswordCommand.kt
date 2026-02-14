package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.comand

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
// Comando para cambiar contraseña, igual que en el backend
// Usa los mismos nombres de campo
//
data class ChangePasswordCommand(
    @SerialName("old_password")
    val oldPassword: String,
    @SerialName("new_password")
    val newPassword: String
)