package com.finance.plutus.mobile.invoices.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.data.SerialApiRepository
import com.finance.plutus.mobile.app.data.SerialRepository
import com.finance.plutus.mobile.app.data.model.Serial
import com.finance.plutus.mobile.app.network.payload.InvoiceUpdateRequest
import com.finance.plutus.mobile.app.ui.BaseViewModel
import com.finance.plutus.mobile.invoices.data.InvoiceRepository
import com.finance.plutus.mobile.invoices.data.model.InvoiceResult
import com.finance.plutus.mobile.items.data.ItemRepository
import com.finance.plutus.mobile.items.data.model.Item
import com.finance.plutus.mobile.partners.data.PartnerRepository
import com.finance.plutus.mobile.partners.data.model.Partner

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

    private val _result = MutableLiveData<InvoiceResult>()
    private val _serial = MutableLiveData<String>()
    private val _partners = MutableLiveData<List<Partner>>()
    private val _items = MutableLiveData<List<Item>>()
    val serial: LiveData<String> = _serial
    val partners: LiveData<List<Partner>> = _partners
    val items: LiveData<List<Item>> = _items
    val result: LiveData<InvoiceResult> = _result

    val updateRequest = InvoiceUpdateRequest()

    init {
        compositeDisposable.add(
            partnerRepository.findAllNonPaged()
                .subscribe(
                    { partners -> _partners.value = partners },
                    { error -> error.printStackTrace() }
                )
        )
        compositeDisposable.add(
            serialRepository.findById(SerialApiRepository.SERIAL_ID)
                .subscribe(
                    { serial -> _serial.value = prepareSerialName(serial) },
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

    fun save() {
        create()
    }

    private fun create() {
        val disposable = invoiceRepository
            .create(updateRequest)
            .subscribe(
                { _result.value = InvoiceResult(ok = true) },
                { error ->
                    error.printStackTrace()
                    _result.value = InvoiceResult(error = R.string.save_failed)
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