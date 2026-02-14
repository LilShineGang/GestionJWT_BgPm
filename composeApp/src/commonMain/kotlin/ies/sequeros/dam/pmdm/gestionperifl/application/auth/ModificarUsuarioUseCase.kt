package ies.sequeros.dam.pmdm.gestionperifl.application.auth

import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.UpdateUserRequest

class ModificarUsuarioUseCase(
    private val api: AuthApi
) {
    suspend fun execute(name: String, status: String): Boolean {
        val request = UpdateUserRequest(name = name, status = status)
        return api.updateUserProfile(request)
    }
}