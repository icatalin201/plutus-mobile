package com.finance.plutus.mobile.ui.updateinvoice

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.*
import com.finance.plutus.mobile.data.network.payload.InvoiceUpdateRequest
import com.finance.plutus.mobile.data.repository.InvoiceRepository
import com.finance.plutus.mobile.data.repository.ItemRepository
import com.finance.plutus.mobile.data.repository.PartnerRepository
import com.finance.plutus.mobile.data.repository.SerialRepository
import com.finance.plutus.mobile.data.repository.impl.SerialApiRepository
import com.finance.plutus.mobile.ui.BaseViewModel
import java.util.*

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
class UpdateInvoiceViewModel(
    private val invoiceRepository: InvoiceRepository,
    private val partnerRepository: PartnerRepository,
    private val serialRepository: SerialRepository,
    private val itemRepository: ItemRepository
) : BaseViewModel() {

    private val _result = MutableLiveData<Result>()
    private val _serial = MutableLiveData<String>()
    private val _partners = MutableLiveData<List<Partner>>()
    private val _items = MutableLiveData<List<Item>>()
    val serial: LiveData<String> = _serial
    val partners: LiveData<List<Partner>> = _partners
    val items: LiveData<List<Item>> = _items
    val result: LiveData<Result> = _result

    val updateRequest = InvoiceUpdateRequest()
    var invoice: Invoice? = null

    init {
        compositeDisposable.add(
            partnerRepository.findAllNonPaged()
                .subscribe(
                    { partners -> _partners.value = partners },
                    { error -> error.printStackTrace() }
                )
        )
        compositeDisposable.add(
            itemRepository.findAllNonPaged()
                .subscribe(
                    { items -> _items.value = items },
                    { error -> error.printStackTrace() }
                )
        )
    }

    fun submitInvoice(invoice: Invoice?) {
        this.invoice = invoice
        this.updateRequest.sync(invoice)
        if (invoice != null) {
            _serial.value = invoice.name
        } else {
            compositeDisposable.add(
                serialRepository.findById(SerialApiRepository.SERIAL_ID)
                    .subscribe(
                        { serial -> _serial.value = prepareSerialName(serial) },
                        { error -> error.printStackTrace() }
                    )
            )
        }
    }

    fun save() {
        if (invoice == null) {
            create()
        } else {
            update(invoice!!.id)
        }
    }

    private fun create() {
        val disposable = invoiceRepository
            .create(updateRequest)
            .subscribe(
                { _result.value = Result(ok = true) },
                { error ->
                    error.printStackTrace()
                    _result.value = Result(error = R.string.save_failed)
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun update(id: UUID) {
        val disposable = invoiceRepository
            .update(id, updateRequest)
            .subscribe(
                { _result.value = Result(ok = true) },
                { error ->
                    error.printStackTrace()
                    _result.value = Result(error = R.string.save_failed)
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun prepareSerialName(serial: Serial): String {
        val size = serial.nextNumber.toString().length
        val formatter = "%s%0" + (size + (4 - size)).toString() + "d"
        return String.format(formatter, serial.name, serial.nextNumber)
    }

}