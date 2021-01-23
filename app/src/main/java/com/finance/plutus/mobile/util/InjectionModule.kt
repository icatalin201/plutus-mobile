package com.finance.plutus.mobile.util

import com.finance.plutus.mobile.network.ClientBuilder
import com.finance.plutus.mobile.network.LoginService
import com.finance.plutus.mobile.network.PlutusService
import com.finance.plutus.mobile.network.interceptor.HeaderInterceptor
import com.finance.plutus.mobile.repository.*
import com.finance.plutus.mobile.repository.impl.*
import com.finance.plutus.mobile.view.login.LoginViewModel
import com.finance.plutus.mobile.view.partner.UpdatePartnerViewModel
import com.finance.plutus.mobile.view.partners.PartnersViewModel
import com.finance.plutus.mobile.view.services.ServicesViewModel
import com.finance.plutus.mobile.view.splash.SplashViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
object InjectionModule {

    val appModule = module {

        single { SharedPreferencesUtil(androidApplication()) }
        single { HeaderInterceptor(get()) }

        single<LoginService> {
            ClientBuilder.createLoginClient()
                .create(LoginService::class.java)
        }
        single<PlutusService> {
            ClientBuilder.createApiClient(get())
                .create(PlutusService::class.java)
        }

        single<TokenRepository> { TokenRepositoryImpl(get(), get()) }
        single<BankRepository> { BankApiRepository(get()) }
        single<CountryRepository> { CountryApiRepository(get()) }
        single<PartnerRepository> { PartnerApiRepository(get()) }
        single<ItemRepository> { ItemApiRepository(get()) }
        single<UserRepository> { UserApiRepository(get()) }
        single<SerialRepository> { SerialApiRepository(get()) }
        single<InvoiceRepository> { InvoiceApiRepository(get()) }
        single<TransactionRepository> { TransactionApiRepository(get()) }

        viewModel { LoginViewModel(get()) }
        viewModel { ServicesViewModel(get()) }
        viewModel { SplashViewModel(get()) }
        viewModel { PartnersViewModel(get()) }
        viewModel { UpdatePartnerViewModel(get()) }
    }

}