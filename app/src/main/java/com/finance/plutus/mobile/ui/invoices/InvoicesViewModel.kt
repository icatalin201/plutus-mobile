package com.finance.plutus.mobile.ui.invoices

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.finance.plutus.mobile.data.model.Invoice
import com.finance.plutus.mobile.data.repository.InvoiceRepository
import com.finance.plutus.mobile.ui.BaseViewModel

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
class InvoicesViewModel(
    private val invoiceRepository: InvoiceRepository
) : BaseViewModel() {

    private val _invoices = MutableLiveData<PagingData<Invoice>>()
    val invoices: LiveData<PagingData<Invoice>> = _invoices

    fun fetchInvoices() {
        val disposable = invoiceRepository.findAll()
            .subscribe(
                { invoices -> _invoices.value = invoices },
                { error -> error.printStackTrace() }
            )
        compositeDisposable.add(disposable)
    }

    fun collect(invoice: Invoice) {
        compositeDisposable.add(
            invoiceRepository.collect(listOf(invoice.id))
                .onErrorComplete()
                .subscribe {
                    fetchInvoices()
                }
        )
    }

    fun delete(invoice: Invoice) {
        val disposable = invoiceRepository.delete(invoice.id)
            .onErrorComplete()
            .subscribe {
                fetchInvoices()
            }
        compositeDisposable.add(disposable)
    }

}