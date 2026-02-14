package ies.sequeros.dam.pmdm.gestionperifl.application.user.changeimage

import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi

class ChangeImageUseCase(private val api: AuthApi) {
    suspend operator fun invoke(imageBytes: ByteArray, fileName: String = "profile.jpg") =
        api.changeProfileImage(imageBytes, fileName)
}
