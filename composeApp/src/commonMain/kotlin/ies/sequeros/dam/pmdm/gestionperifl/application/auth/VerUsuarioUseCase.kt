package ies.sequeros.dam.pmdm.gestionperifl.application.auth

import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.ProfileResponse

class VerUsuarioUseCase(
    private val api: AuthApi
) {
    suspend fun execute(): ProfileResponse? {
        return api.getMyProfile()
    }
}