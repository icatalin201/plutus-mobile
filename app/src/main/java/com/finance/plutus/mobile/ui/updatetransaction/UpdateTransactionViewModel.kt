package com.finance.plutus.mobile.ui.updatetransaction

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.data.model.Partner
import com.finance.plutus.mobile.data.model.Result
import com.finance.plutus.mobile.data.model.Transaction
import com.finance.plutus.mobile.data.network.payload.TransactionUpdateRequest
import com.finance.plutus.mobile.data.repository.PartnerRepository
import com.finance.plutus.mobile.data.repository.TransactionRepository
import com.finance.plutus.mobile.ui.BaseViewModel
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
class UpdateTransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val partnerRepository: PartnerRepository
) : BaseViewModel() {

    private val _result = MutableLiveData<Result>()
    private val _partners = MutableLiveData<List<Partner>>()
    val partners: LiveData<List<Partner>> = _partners
    val result: LiveData<Result> = _result

    val updateRequest = TransactionUpdateRequest()
    var transaction: Transaction? = null
        set(value) {
            field = value
            updateRequest.sync(value)
        }

    init {
        val disposable = partnerRepository.findAllNonPaged()
            .subscribe(
                { partners ->
                    _partners.value = partners
                },
                { error -> error.printStackTrace() }
            )
        compositeDisposable.add(disposable)
    }

    fun save() {
        if (transaction != null) {
            update(transaction!!.id)
        } else {
            create()
        }
    }

    private fun create() {
        val disposable = transactionRepository
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
        val disposable = transactionRepository
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