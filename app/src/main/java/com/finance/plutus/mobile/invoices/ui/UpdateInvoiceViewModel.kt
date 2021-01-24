package com.finance.plutus.mobile.invoices.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.app.data.SerialRepository
import com.finance.plutus.mobile.app.data.model.Serial
import com.finance.plutus.mobile.app.network.payload.InvoiceUpdateRequest
import com.finance.plutus.mobile.app.ui.BaseViewModel
import com.finance.plutus.mobile.invoices.data.InvoiceRepository
import com.finance.plutus.mobile.invoices.data.model.InvoiceResult
import com.finance.plutus.mobile.partners.data.PartnerRepository
import com.finance.plutus.mobile.partners.data.model.Partner
import java.util.*

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
class UpdateInvoiceViewModel(
    private val invoiceRepository: InvoiceRepository,
    private val partnerRepository: PartnerRepository,
    private val serialRepository: SerialRepository
) : BaseViewModel() {

    private val _result = MutableLiveData<InvoiceResult>()
    private val _serial = MutableLiveData<Serial>()
    private val _partners = MutableLiveData<List<Partner>>()
    val serial: LiveData<Serial> = _serial
    val partners: LiveData<List<Partner>> = _partners
    val result: LiveData<InvoiceResult> = _result

    val updateRequest = InvoiceUpdateRequest()

    init {
        compositeDisposable.add(
            partnerRepository.findAllNonPaged()
                .subscribe(
                    { partners ->
                        _partners.value = partners
                    },
                    { error -> error.printStackTrace() }
                )
        )
        compositeDisposable.add(
            serialRepository.findById(UUID.fromString("2e978bc3-115d-4226-90a7-24bd24ef5054"))
                .subscribe(
                    { serial ->
                        _serial.value = serial
                    },
                    { error -> error.printStackTrace() }
                )
        )
    }

    fun save() {

    }

}