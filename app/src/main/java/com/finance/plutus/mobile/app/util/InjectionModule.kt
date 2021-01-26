package com.finance.plutus.mobile.app.util

import com.finance.plutus.mobile.app.data.*
import com.finance.plutus.mobile.app.network.ClientBuilder
import com.finance.plutus.mobile.app.network.LoginService
import com.finance.plutus.mobile.app.network.PlutusService
import com.finance.plutus.mobile.app.network.interceptor.HeaderInterceptor
import com.finance.plutus.mobile.app.ui.SplashViewModel
import com.finance.plutus.mobile.dashboard.DashboardViewModel
import com.finance.plutus.mobile.invoices.data.InvoiceApiRepository
import com.finance.plutus.mobile.invoices.data.InvoiceRepository
import com.finance.plutus.mobile.invoices.ui.InvoicesViewModel
import com.finance.plutus.mobile.invoices.ui.UpdateInvoiceViewModel
import com.finance.plutus.mobile.items.data.ItemApiRepository
import com.finance.plutus.mobile.items.data.ItemRepository
import com.finance.plutus.mobile.items.ui.ServicesViewModel
import com.finance.plutus.mobile.items.ui.UpdateItemViewModel
import com.finance.plutus.mobile.login.data.TokenRepository
import com.finance.plutus.mobile.login.data.TokenRepositoryImpl
import com.finance.plutus.mobile.login.ui.LoginViewModel
import com.finance.plutus.mobile.partners.data.PartnerApiRepository
import com.finance.plutus.mobile.partners.data.PartnerRepository
import com.finance.plutus.mobile.partners.ui.PartnersViewModel
import com.finance.plutus.mobile.partners.ui.UpdatePartnerViewModel
import com.finance.plutus.mobile.transactions.data.TransactionApiRepository
import com.finance.plutus.mobile.transactions.data.TransactionRepository
import com.finance.plutus.mobile.transactions.ui.TransactionsViewModel
import com.finance.plutus.mobile.transactions.ui.UpdateTransactionViewModel
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
        single<CurrencyRateRepository> { CurrencyRateApiRepository(get()) }

        viewModel { LoginViewModel(get()) }
        viewModel { ServicesViewModel(get()) }
        viewModel { SplashViewModel(get()) }
        viewModel { PartnersViewModel(get()) }
        viewModel { UpdatePartnerViewModel(get(), get(), get()) }
        viewModel { TransactionsViewModel(get()) }
        viewModel { UpdateTransactionViewModel(get(), get()) }
        viewModel { UpdateItemViewModel(get()) }
        viewModel { InvoicesViewModel(get()) }
        viewModel { UpdateInvoiceViewModel(get(), get(), get(), get()) }
        viewModel { DashboardViewModel(get(), get()) }
    }

}