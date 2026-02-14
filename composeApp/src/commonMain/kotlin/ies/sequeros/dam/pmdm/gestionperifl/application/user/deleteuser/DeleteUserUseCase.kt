package ies.sequeros.dam.pmdm.gestionperifl.application.user.deleteuser

import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.comand.DeleteCommand

class DeleteUserUseCase(private val api: AuthApi) {
    suspend operator fun invoke(command: DeleteCommand): Boolean {
        return api.deleteUser(command)
    }
}
