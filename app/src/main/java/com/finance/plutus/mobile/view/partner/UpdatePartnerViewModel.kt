package com.finance.plutus.mobile.view.partner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.network.payload.PartnerUpdateRequest
import com.finance.plutus.mobile.repository.PartnerRepository
import com.finance.plutus.mobile.view.BaseViewModel
import java.util.*

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
class UpdatePartnerViewModel(
    private val partnerRepository: PartnerRepository
) : BaseViewModel() {

    private val _result = MutableLiveData<PartnerResult>()
    val result: LiveData<PartnerResult> = _result

    fun create(request: PartnerUpdateRequest) {
        val disposable = partnerRepository
            .create(request)
            .subscribe(
                { _result.value = PartnerResult(ok = true) },
                { error ->
                    error.printStackTrace()
                    _result.value = PartnerResult(error = R.string.partner_save_failed)
                }
            )
        compositeDisposable.add(disposable)
    }

    fun update(id: UUID, request: PartnerUpdateRequest) {
        val disposable = partnerRepository
            .update(id, request)
            .subscribe(
                { _result.value = PartnerResult(ok = true) },
                { error ->
                    error.printStackTrace()
                    _result.value = PartnerResult(error = R.string.partner_save_failed)
                }
            )
        compositeDisposable.add(disposable)
    }

}