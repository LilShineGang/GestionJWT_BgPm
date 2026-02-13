package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
<<<<<<< HEAD
=======
import io.ktor.http.ContentType
import io.ktor.http.contentType
>>>>>>> 5ec71a149f3f85a970ccc10e32a96a2ba3ebecfd
import io.ktor.http.isSuccess

class AuthApi(
    private val client: HttpClient,
    private val baseUrl: String,
) {
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
<<<<<<< HEAD
=======

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
>>>>>>> 5ec71a149f3f85a970ccc10e32a96a2ba3ebecfd
}
