package com.finance.plutus.mobile.transactions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.finance.plutus.mobile.transactions.data.model.TransactionFilter
import com.finance.plutus.mobile.transactions.data.TransactionRepository
import com.finance.plutus.mobile.transactions.data.model.Transaction
import com.finance.plutus.mobile.app.ui.BaseViewModel

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
class TransactionsViewModel(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _transactions = MutableLiveData<PagingData<Transaction>>()
    val transactions: LiveData<PagingData<Transaction>> = _transactions

    val filter = TransactionFilter()

    fun fetchTransactions() {
        val disposable = transactionRepository.findAllFiltered(filter)
            .subscribe(
                { transactions -> _transactions.value = transactions },
                { error -> error.printStackTrace() }
            )
        compositeDisposable.add(disposable)
    }

    fun delete(transaction: Transaction) {
        val disposable = transactionRepository.delete(transaction.id)
            .subscribe {
                fetchTransactions()
            }
        compositeDisposable.add(disposable)
    }

}