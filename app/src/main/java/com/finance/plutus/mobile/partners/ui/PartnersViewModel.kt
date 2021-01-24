package com.finance.plutus.mobile.partners.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.finance.plutus.mobile.app.ui.BaseViewModel
import com.finance.plutus.mobile.partners.data.PartnerRepository
import com.finance.plutus.mobile.partners.data.model.Partner

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class PartnersViewModel(
    private val partnerRepository: PartnerRepository
) : BaseViewModel() {

    private val _partners = MutableLiveData<PagingData<Partner>>()
    val partners: LiveData<PagingData<Partner>> = _partners

    fun fetchPartners() {
        val disposable = partnerRepository.findAll()
            .subscribe(
                { partners -> _partners.value = partners },
                { error -> error.printStackTrace() }
            )
        compositeDisposable.add(disposable)
    }

    fun delete(partner: Partner) {
        val disposable = partnerRepository.delete(partner.id)
            .subscribe {
                fetchPartners()
            }
        compositeDisposable.add(disposable)
    }

}