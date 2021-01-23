package com.finance.plutus.mobile.view.partners

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.model.Partner
import com.finance.plutus.mobile.repository.PartnerRepository
import com.finance.plutus.mobile.view.BaseViewModel

/**
 * Plutus Finance
 * Created by Catalin on 1/23/2021
 **/
class PartnersViewModel(
    private val partnerRepository: PartnerRepository
) : BaseViewModel() {

    private val _partners = MutableLiveData<List<Partner>>()
    val partners: LiveData<List<Partner>> = _partners

    fun fetchPartners() {
        val disposable = partnerRepository.findAllByPage(0, 100)
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