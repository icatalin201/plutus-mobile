package com.finance.plutus.mobile.ui.partners

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.finance.plutus.mobile.data.model.Partner
import com.finance.plutus.mobile.data.repository.PartnerRepository
import com.finance.plutus.mobile.ui.BaseViewModel

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