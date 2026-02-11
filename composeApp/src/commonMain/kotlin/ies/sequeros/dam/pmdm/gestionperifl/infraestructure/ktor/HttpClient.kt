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
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

fun createHttpClient(
    tokenStorage: TokenStorage,
    refreshUrl: String,
): HttpClient {
    return HttpClient { // Puedes usar HttpClient(CIO), HttpClient(Darwin), etc.
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
            level = LogLevel.ALL // O LogLevel.ALL para ver todo
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
                    request.url.encodedPath.startsWith("/api/users/")
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
                        return@refreshTokens null
                    }

                    val newTokens = try {
                        client.post(refreshUrl) {
                            markAsRefreshTokenRequest()
                            setBody(RefreshRequest(refreshToken))
                        }.body<AuthTokensResponse>()
                    } catch (ex: Exception) {
                        tokenStorage.clear()
                        return@refreshTokens null
                    }

                    tokenStorage.saveTokens(
                        newTokens.access_token,
                        newTokens.refresh_token,
                    )
                    BearerTokens(newTokens.access_token, newTokens.refresh_token)
                }
            }
        }

        // Timeout
        install(HttpTimeout) {
            requestTimeoutMillis = 15000
        }
    }
}