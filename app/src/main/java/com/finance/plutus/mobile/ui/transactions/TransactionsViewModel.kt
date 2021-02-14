package com.finance.plutus.mobile.ui.transactions

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.finance.plutus.mobile.data.model.Transaction
import com.finance.plutus.mobile.data.model.TransactionFilter
import com.finance.plutus.mobile.data.model.TransactionMonth
import com.finance.plutus.mobile.data.repository.TransactionRepository
import com.finance.plutus.mobile.ui.BaseViewModel
import java.time.LocalDate

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
class TransactionsViewModel(
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _transactions = MutableLiveData<PagingData<Transaction>>()
    private val _months = MutableLiveData<List<TransactionMonth>>()
    val transactions: LiveData<PagingData<Transaction>> = _transactions
    val months: LiveData<List<TransactionMonth>> = _months

    var month: TransactionMonth? = null

    init {
        generateMonths()
    }

    fun fetchTransactions() {
        month?.let {
            val filter = TransactionFilter()
            filter.startDate = it.date.withDayOfMonth(1)
            filter.endDate =
                it.date.withDayOfMonth(it.date.lengthOfMonth())
            val disposable = transactionRepository.findAllFiltered(filter)
                .subscribe(
                    { transactions -> _transactions.value = transactions },
                    { error -> error.printStackTrace() }
                )
            compositeDisposable.add(disposable)
        }
    }

    fun collect(transaction: Transaction) {
        compositeDisposable.add(
            transactionRepository.collect(listOf(transaction.id))
                .onErrorComplete()
                .subscribe { fetchTransactions() }
        )
    }

    fun delete(transaction: Transaction) {
        val disposable = transactionRepository.delete(transaction.id)
            .onErrorComplete()
            .subscribe { fetchTransactions() }
        compositeDisposable.add(disposable)
    }

    private fun generateMonths() {
        val months = mutableListOf<TransactionMonth>()
        val endingDate = LocalDate.of(2019, 1, 1)
        var startingDate = LocalDate.now()
        while (startingDate.isAfter(endingDate)) {
            val year = startingDate.year
            val title = startingDate.month.name
            months.add(TransactionMonth("$title $year", startingDate, false))
            startingDate = startingDate.minusMonths(1L)
        }
        _months.value = months
    }

}