package ies.sequeros.dam.pmdm.gestionperifl.infraestructure.auth

import com.russhwolf.settings.Settings

class SettingsTokenStorage(
    private val settings: Settings,
) : TokenStorage {
    override fun getAccessToken(): String? = settings.getStringOrNull(KEY_ACCESS)

    override fun getRefreshToken(): String? = settings.getStringOrNull(KEY_REFRESH)

    override fun getIdToken(): String? = settings.getStringOrNull(KEY_ID)

    override fun saveTokens(accessToken: String, refreshToken: String, idToken: String?) {
        settings.putString(KEY_ACCESS, accessToken)
        settings.putString(KEY_REFRESH, refreshToken)
        if (idToken != null) settings.putString(KEY_ID, idToken) else settings.remove(KEY_ID)
    }

    override fun clear() {
        settings.remove(KEY_ACCESS)
        settings.remove(KEY_REFRESH)
        settings.remove(KEY_ID)
    }

    private companion object {
        const val KEY_ACCESS = "access_token"
        const val KEY_REFRESH = "refresh_token"
        const val KEY_ID = "id_token"
    }
}
