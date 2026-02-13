package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.auth

interface TokenStorage {
    fun getAccessToken(): String?
    fun getRefreshToken(): String?
    fun getIdToken(): String?
    fun saveTokens(accessToken: String, refreshToken: String, idToken: String?)
    fun clear()
}
