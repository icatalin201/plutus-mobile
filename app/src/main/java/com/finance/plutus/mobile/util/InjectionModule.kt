package com.finance.plutus.mobile.util

import com.finance.plutus.mobile.data.network.ClientBuilder
import com.finance.plutus.mobile.data.network.LoginService
import com.finance.plutus.mobile.data.network.PlutusService
import com.finance.plutus.mobile.data.network.interceptor.HeaderInterceptor
import com.finance.plutus.mobile.data.repository.*
import com.finance.plutus.mobile.data.repository.impl.*
import com.finance.plutus.mobile.ui.dashboard.DashboardViewModel
import com.finance.plutus.mobile.ui.invoices.InvoicesViewModel
import com.finance.plutus.mobile.ui.items.ServicesViewModel
import com.finance.plutus.mobile.ui.login.LoginViewModel
import com.finance.plutus.mobile.ui.partners.PartnersViewModel
import com.finance.plutus.mobile.ui.transactions.TransactionsViewModel
import com.finance.plutus.mobile.ui.updateinvoice.UpdateInvoiceViewModel
import com.finance.plutus.mobile.ui.updateitem.UpdateItemViewModel
import com.finance.plutus.mobile.ui.updatepartner.UpdatePartnerViewModel
import com.finance.plutus.mobile.ui.updatetransaction.UpdateTransactionViewModel
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
        viewModel { PartnersViewModel(get()) }
        viewModel { UpdatePartnerViewModel(get(), get(), get()) }
        viewModel { TransactionsViewModel(get()) }
        viewModel { UpdateTransactionViewModel(get(), get()) }
        viewModel { UpdateItemViewModel(get()) }
        viewModel { InvoicesViewModel(get()) }
        viewModel { UpdateInvoiceViewModel(get(), get(), get(), get()) }
        viewModel { DashboardViewModel(get(), get(), get()) }
    }

}