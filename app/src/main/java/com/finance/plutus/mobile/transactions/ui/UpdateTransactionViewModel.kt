package com.finance.plutus.mobile.transactions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.R
import com.finance.plutus.mobile.app.network.payload.TransactionUpdateRequest
import com.finance.plutus.mobile.app.ui.BaseViewModel
import com.finance.plutus.mobile.partners.data.PartnerRepository
import com.finance.plutus.mobile.partners.data.model.Partner
import com.finance.plutus.mobile.transactions.data.TransactionRepository
import com.finance.plutus.mobile.transactions.data.model.TransactionResult
import com.finance.plutus.mobile.transactions.data.model.Transaction
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
class UpdateTransactionViewModel(
    private val transactionRepository: TransactionRepository,
    private val partnerRepository: PartnerRepository
) : BaseViewModel() {

    private val _result = MutableLiveData<TransactionResult>()
    private val _partners = MutableLiveData<List<Partner>>()
    val partners: LiveData<List<Partner>> = _partners
    val result: LiveData<TransactionResult> = _result

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
                { _result.value = TransactionResult(ok = true) },
                { error ->
                    error.printStackTrace()
                    _result.value = TransactionResult(error = R.string.save_failed)
                }
            )
        compositeDisposable.add(disposable)
    }

    private fun update(id: UUID) {
        val disposable = transactionRepository
            .update(id, updateRequest)
            .subscribe(
                { _result.value = TransactionResult(ok = true) },
                { error ->
                    error.printStackTrace()
                    _result.value = TransactionResult(error = R.string.save_failed)
                }
            )
        compositeDisposable.add(disposable)
    }

}