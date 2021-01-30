package com.finance.plutus.mobile.transactions.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.paging.PagingData
import com.finance.plutus.mobile.app.ui.BaseViewModel
import com.finance.plutus.mobile.transactions.data.TransactionRepository
import com.finance.plutus.mobile.transactions.data.model.Transaction
import com.finance.plutus.mobile.transactions.data.model.TransactionFilter
import com.finance.plutus.mobile.transactions.data.model.TransactionMonth
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

    init {
        generateMonths()
    }

    fun fetchTransactions(transactionMonth: TransactionMonth) {
        val filter = TransactionFilter()
        filter.startDate = transactionMonth.date.withDayOfMonth(1)
        filter.endDate = transactionMonth.date.withDayOfMonth(transactionMonth.date.lengthOfMonth())
        val disposable = transactionRepository.findAllFiltered(filter)
            .subscribe(
                { transactions -> _transactions.value = transactions },
                { error -> error.printStackTrace() }
            )
        compositeDisposable.add(disposable)
    }

    fun collect(transaction: Transaction) {
        compositeDisposable.add(
            transactionRepository.collect(listOf(transaction.id))
                .subscribe { }
        )
    }

    fun delete(transaction: Transaction) {
        val disposable = transactionRepository.delete(transaction.id)
            .subscribe { }
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