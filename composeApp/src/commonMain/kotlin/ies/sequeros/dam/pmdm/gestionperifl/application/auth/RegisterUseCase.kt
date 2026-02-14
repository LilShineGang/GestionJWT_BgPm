package ies.sequeros.dam.pmdm.gestionperifl.application.auth

import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.RegisterRequest

class RegisterUseCase(
    private val api: AuthApi
) {
    suspend fun execute(request: RegisterRequest): Boolean {
        return api.register(request)
    }
}