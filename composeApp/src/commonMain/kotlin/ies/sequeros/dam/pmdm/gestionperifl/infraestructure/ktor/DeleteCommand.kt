package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor

import kotlinx.serialization.Serializable

@Serializable
// Comando para borrar usuario, igual que en el backend
// Solo requiere la contraseña
// Puedes expandirlo si el backend lo requiere
// pero por ahora solo password
//
data class DeleteCommand(val password: String)
