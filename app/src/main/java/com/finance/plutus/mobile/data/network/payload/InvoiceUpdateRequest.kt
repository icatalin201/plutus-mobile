package com.finance.plutus.mobile.data.network.payload

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import com.finance.plutus.mobile.BR
import com.finance.plutus.mobile.data.model.Currency
import com.finance.plutus.mobile.data.model.Invoice
import com.finance.plutus.mobile.data.repository.impl.SerialApiRepository
import java.time.LocalDate
import java.util.*
import java.util.stream.Collectors

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class InvoiceUpdateRequest : BaseObservable() {

    @get:Bindable
    var partnerId: UUID? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.partnerId)
        }

    @get:Bindable
    var serialId: UUID = SerialApiRepository.SERIAL_ID

    @get:Bindable
    var currency: Currency = Currency.USD
        set(value) {
            field = value
            notifyPropertyChanged(BR.currency)
        }

    @get:Bindable
    var date: String = LocalDate.now().toString()
        set(value) {
            field = value
            notifyPropertyChanged(BR.date)
        }

    @get:Bindable
    var dueDate: String? = null
        set(value) {
            field = value
            notifyPropertyChanged(BR.dueDate)
        }

    @get:Bindable
    var lines: MutableList<InvoiceLineUpdateRequest> = mutableListOf(InvoiceLineUpdateRequest())

    fun sync(invoice: Invoice?) {
        invoice?.let {
            date = invoice.date
            dueDate = invoice.dueDate
            currency = invoice.currency?.value ?: Currency.USD
            partnerId = invoice.customer.id
            lines.clear()
            lines.addAll(
                invoice.lines
                    .stream()
                    .map { line ->
                        val lineRequest = InvoiceLineUpdateRequest()
                        lineRequest.sync(line)
                        lineRequest
                    }.collect(Collectors.toList())
            )
        }
    }

}
