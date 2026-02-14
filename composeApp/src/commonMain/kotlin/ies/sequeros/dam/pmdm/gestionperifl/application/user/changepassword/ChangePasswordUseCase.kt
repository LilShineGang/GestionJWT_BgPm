package ies.sequeros.dam.pmdm.gestionperifl.application.user.changepassword

import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.ChangePasswordCommand


class ChangePasswordUseCase(private val api: AuthApi) {
    suspend operator fun invoke(command: ChangePasswordCommand): Boolean {
        return api.changePassword(command = command)
    }
}
