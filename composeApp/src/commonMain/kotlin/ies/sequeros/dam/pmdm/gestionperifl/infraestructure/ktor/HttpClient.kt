package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor

import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.auth.TokenStorage
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.DefaultRequest
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.auth.Auth
import io.ktor.client.plugins.auth.providers.BearerTokens
import io.ktor.client.plugins.auth.providers.bearer
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.encodedPath
import io.ktor.http.isSuccess
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(
    tokenStorage: TokenStorage,
    refreshUrl: String,
): HttpClient {
    return HttpClient {
        install(DefaultRequest) {
            header(HttpHeaders.ContentType, ContentType.Application.Json)
        }
        //logs
        install(Logging) {

            //logger = Logger.DEFAULT
            logger = object : Logger {
                override fun log(message: String) {
                    println("KTOR CLIENT LOG: $message")
                }
            }
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                prettyPrint = true
                isLenient = true
            })
        }
        install(Auth) {
            bearer {
                sendWithoutRequest { request ->
                    val path = request.url.encodedPath
                    val refreshPath = io.ktor.http.Url(refreshUrl).encodedPath
                    path.startsWith("/api/") && !path.startsWith("/api/public/") && path != refreshPath
                }
                loadTokens {
                    val accessToken = tokenStorage.getAccessToken()
                    val refreshToken = tokenStorage.getRefreshToken()
                    if (accessToken.isNullOrBlank()) {
                        null
                    } else {
                        BearerTokens(accessToken, refreshToken.orEmpty())
                    }
                }

                // configurar el refresco
                refreshTokens {
                    val refreshToken = tokenStorage.getRefreshToken()
                    if (refreshToken.isNullOrBlank()) {
                        println("KTOR CLIENT LOG: refresh skipped (no refresh token)")
                        return@refreshTokens null
                    }

                    val response = try {
                        println("KTOR CLIENT LOG: attempting token refresh")
                        client.post(refreshUrl) {
                            markAsRefreshTokenRequest()
                            setBody(mapOf("refresh_token" to refreshToken))
                        }
                    } catch (ex: Exception) {
                        println("KTOR CLIENT LOG: token refresh failed: ${ex.message}")
                        tokenStorage.clear()
                        return@refreshTokens null
                    }

                    if (!response.status.isSuccess()) {
                        println("KTOR CLIENT LOG: token refresh rejected: ${response.status}")
                        return@refreshTokens null
                    }

                    val data = response.body<Map<String, String>>()
                    val newAccess = data["access_token"].orEmpty()
                    val newRefresh = data["refresh_token"] ?: refreshToken
                    val idToken = data["id_token"]

                    if (newAccess.isBlank()) {
                        println("KTOR CLIENT LOG: token refresh missing access_token")
                        return@refreshTokens null
                    }

                    tokenStorage.saveTokens(
                        newAccess,
                        newRefresh,
                        idToken
                    )
                    println("KTOR CLIENT LOG: token refresh succeeded")
                    BearerTokens(newAccess, newRefresh)
                }
            }
        }

        // Timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
    }
}