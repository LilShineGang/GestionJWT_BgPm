package ies.sequeros.dam.pmdm.gestionperifl.application.auth

import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.auth.TokenStorage
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi

class LoginUseCase(
    private val authApi: AuthApi,
    private val tokenStorage: TokenStorage,
) {
    suspend operator fun invoke(email: String, password: String): Result<Unit> {
        return runCatching {
            val tokens = authApi.login(email, password)
            tokenStorage.saveTokens(tokens.access_token, tokens.refresh_token)
        }
    }
}
