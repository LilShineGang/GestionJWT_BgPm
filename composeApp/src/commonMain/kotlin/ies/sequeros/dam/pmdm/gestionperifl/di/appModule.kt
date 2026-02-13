package ies.sequeros.dam.pmdm.gestionperifl.di
import ies.sequeros.dam.pmdm.gestionperifl.ui.main.MainViewModel

import ies.sequeros.dam.pmdm.gestionperifl.application.auth.LoginUseCase
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.auth.SettingsTokenStorage
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.auth.TokenStorage
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.AuthApi
import ies.sequeros.dam.pmdm.gestionperifl.infraestructure.ktor.createHttpClient
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppSettings
import ies.sequeros.dam.pmdm.gestionperifl.ui.appsettings.AppViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.login.LoginFormViewModel
import ies.sequeros.dam.pmdm.gestionperifl.ui.register.RegisterFormViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val appModulo = module {
    val baseUrl = "http://localhost:8080"

    /**
     * infraestructura
     */
    single<TokenStorage> {
        SettingsTokenStorage(get())
    }
    single {
        createHttpClient(
            get(),
            "$baseUrl/api/public/refresh"
        )
    }
    single { AuthApi(get(), baseUrl) }
    single { LoginUseCase(get(), get()) }
    //almacenamiento del token
    //repositorios
    /**
    capa de aplicación
    el sesion manager,
    el origen de los datos, se encarga de transforar el tokenstorage para trabajar con user
    casos de uso
     **/

    /**
    capa de presentación
     **/
    single { AppSettings() }
    viewModel { AppViewModel(get()) }
    viewModel { LoginFormViewModel(get()) }
    viewModel { MainViewModel() }
    viewModel { RegisterFormViewModel(get()) }
}