package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor

import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.comand.ChangePasswordCommand
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.comand.DeleteCommand
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.post
import io.ktor.client.request.put
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.client.utils.EmptyContent.contentType

import io.ktor.http.ContentType
import io.ktor.http.contentType

import io.ktor.http.isSuccess

class AuthApi(
    private val client: HttpClient,
    private val baseUrl: String,
) {
    suspend fun changePassword(command: ChangePasswordCommand): Boolean {
        try {
            val response = client.put("$baseUrl/api/users/me/password") {
                contentType(ContentType.Application.Json)
                setBody(command)
            }
            return response.status.value in 200..299
        } catch (e: Exception) {
            println("Error al cambiar contraseña: ${e.message}")
            e.printStackTrace()
            return false
        }
    }
    suspend fun login(email: String, password: String): AuthTokensResponse {
        val response = client.post("$baseUrl/api/public/login") {
            setBody(LoginRequest(email = email, password = password))
        }

        if (!response.status.isSuccess()) {
            val errorBody = response.bodyAsText()
            throw IllegalStateException("Login failed: ${response.status.value} ${response.status.description}. $errorBody")
        }

        return response.body()
    }


    suspend fun register(datos: RegisterRequest): Boolean {
        try {
            val response = client.post("$baseUrl/api/public/register") {
                contentType(ContentType.Application.Json)
                setBody(datos)
            }
            return response.status.value in 200..299
        } catch (e: Exception) {
            println("Error al registrar usuario: ${e.message}")
            e.printStackTrace()
            return false
        }
    }

    suspend fun refresh(refreshToken: String): AuthTokensResponse {
        val response = client.post("$baseUrl/api/public/refresh") {
            setBody(RefreshRequest(refresh_token = refreshToken))
        }

        if (!response.status.isSuccess()) {
            val errorBody = response.bodyAsText()
            throw IllegalStateException("Refresh failed: ${response.status.value} ${response.status.description}. $errorBody")
        }

        return response.body()
    }

    suspend fun deleteUser(deleteCommand: DeleteCommand): Boolean {
        try {
            val response = client.delete("$baseUrl/api/users/me") {
                contentType(ContentType.Application.Json)
                setBody(deleteCommand)
            }
            return response.status.value in 200..299
        } catch (e: Exception) {
            println("Error al borrar usuario: ${e.message}")
            e.printStackTrace()
            return false
        }
    }
}
