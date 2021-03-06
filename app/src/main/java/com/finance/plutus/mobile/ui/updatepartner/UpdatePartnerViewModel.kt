package com.finance.plutus.mobile.ui.updatepartner

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.Bank
import com.finance.plutus.mobile.data.model.Country
import com.finance.plutus.mobile.data.model.Partner
import com.finance.plutus.mobile.data.model.Result
import com.finance.plutus.mobile.data.network.payload.PartnerUpdateRequest
import com.finance.plutus.mobile.data.repository.BankRepository
import com.finance.plutus.mobile.data.repository.CountryRepository
import com.finance.plutus.mobile.data.repository.PartnerRepository
import com.finance.plutus.mobile.ui.BaseViewModel
import java.util.*

/**
 * Plutus Finance
 * Created by Catalin on 1/24/2021
 **/
class UpdatePartnerViewModel(
    private val partnerRepository: PartnerRepository,
    private val countryRepository: CountryRepository,
    private val bankRepository: BankRepository,
) : BaseViewModel() {

    private val _result = MutableLiveData<Result>()
    private val _banks = MutableLiveData<List<Bank>>()
    private val _countries = MutableLiveData<List<Country>>()

    val result: LiveData<Result> = _result
    val banks: LiveData<List<Bank>> = _banks
    val countries: LiveData<List<Country>> = _countries

    val updateRequest = PartnerUpdateRequest()
    var partner: Partner? = null
        set(value) {
            field = value
            updateRequest.sync(value)
        }

    init {
        val banksDisposable = bankRepository.findAll()
            .subscribe(
                { banks ->
                    _banks.value = banks
                },
                { error -> error.printStackTrace() }
            )
        val countriesDisposable = countryRepository.findAll()
            .subscribe(
                { countries ->
                    _countries.value = countries
                },
                { error -> error.printStackTrace() }
            )
        compositeDisposable.add(banksDisposable)
        compositeDisposable.add(countriesDisposable)
    }

    fun save() {
        if (partner != null) {
            update(partner!!.id)
        } else {
            create()
        }
    }

    private fun create() {
        val disposable = partnerRepository
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
        val disposable = partnerRepository
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

}