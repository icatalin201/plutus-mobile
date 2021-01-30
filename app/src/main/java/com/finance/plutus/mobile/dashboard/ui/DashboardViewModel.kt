package com.finance.plutus.mobile.dashboard.ui

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.finance.plutus.mobile.app.data.CurrencyRateRepository
import com.finance.plutus.mobile.app.data.SerialApiRepository.Companion.SERIAL_ID
import com.finance.plutus.mobile.app.data.SerialRepository
import com.finance.plutus.mobile.app.data.model.CurrencyRate
import com.finance.plutus.mobile.app.data.model.Serial
import com.finance.plutus.mobile.app.data.model.TransactionStat
import com.finance.plutus.mobile.app.network.payload.SerialUpdateRequest
import com.finance.plutus.mobile.app.ui.BaseViewModel
import com.finance.plutus.mobile.dashboard.data.DashboardStat
import com.finance.plutus.mobile.dashboard.data.InvoicesArchiveDownloader
import com.finance.plutus.mobile.dashboard.data.TransactionsReportDownloader
import com.finance.plutus.mobile.transactions.data.TransactionRepository
import com.finance.plutus.mobile.transactions.data.model.TransactionFilter
import com.finance.plutus.mobile.transactions.data.model.TransactionType
import io.reactivex.Single
import java.time.LocalDate

/**
Plutus Finance
Created by Catalin on 1/25/2021
 **/
class DashboardViewModel(
    private val serialRepository: SerialRepository,
    private val currencyRateRepository: CurrencyRateRepository,
    private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _stats = MutableLiveData<List<DashboardStat>>()
    private val _serial = MutableLiveData<Serial>()
    private val _rates = MutableLiveData<List<CurrencyRate>>()
    val serial: LiveData<Serial> = _serial
    val rates: LiveData<List<CurrencyRate>> = _rates
    val stats: LiveData<List<DashboardStat>> = _stats

    init {
        fetchSerial()
        compositeDisposable.add(
            currencyRateRepository.fetchTodayRates()
                .subscribe(
                    { rates -> _rates.value = rates },
                    { error -> error.printStackTrace() }
                )
        )
    }

    fun getStats() {
        compositeDisposable.add(
            Single.zip(
                mutableListOf(
                    findTotalIncome(),
                    findTotalExpense(),
                    findTotalIncomeForLastYear(),
                    findTotalExpenseForLastYear(),
                    findDeductibleExpensesForLastYear()
                )
            ) { responses ->
                val stats = mutableListOf<DashboardStat>()
                responses.forEachIndexed { index, element ->
                    val name = when (index) {
                        0 -> "Incasari 2021"
                        1 -> "Cheltuieli 2021"
                        2 -> "Incasari 2020"
                        3 -> "Cheltuieli 2020"
                        4 -> "Cheltuieli deductibile 2020"
                        else -> ""
                    }
                    val stat = element as TransactionStat
                    stats.add(DashboardStat(name, stat.total, stat.number))
                }
                stats
            }.subscribe(
                { stats -> _stats.value = stats },
                { error -> error.printStackTrace() }
            )
        )
    }

    fun downloadTransactionsReport(context: Context, year: String) {
        val manager = WorkManager.getInstance(context)
        val workRequest = OneTimeWorkRequest
            .Builder(TransactionsReportDownloader::class.java)
            .setInputData(Data.Builder().putString("year", year).build())
            .build()
        manager.enqueue(workRequest)
    }

    fun downloadInvoicesArchive(context: Context) {
        val manager = WorkManager.getInstance(context)
        val workRequest = OneTimeWorkRequest
            .Builder(InvoicesArchiveDownloader::class.java)
            .build()
        manager.enqueue(workRequest)
    }

    fun updateSerial(serial: Serial, value: String) {
        val newValue = when (value.isNotBlank()) {
            true -> value.toInt()
            else -> serial.currentNumber
        }
        val request = SerialUpdateRequest(newValue)
        compositeDisposable.add(
            serialRepository.update(SERIAL_ID, request)
                .subscribe(
                    { fetchSerial() },
                    { error -> error.printStackTrace() }
                )
        )
    }

    fun prepareSerialName(serial: Serial): String {
        val size = serial.nextNumber.toString().length
        val formatter = "%s%0" + (size + (4 - size)).toString() + "d"
        return String.format(formatter, serial.name, serial.nextNumber)
    }

    private fun findDeductibleExpensesForLastYear(): Single<TransactionStat> {
        val filter = TransactionFilter()
        val now: LocalDate = LocalDate.now().minusYears(1)
        filter.startDate = now.withMonth(1).withDayOfMonth(1)
        filter.endDate = now.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.EXPENSE
        filter.deductible = true
        return findStat(filter)
    }

    private fun findTotalIncomeForLastYear(): Single<TransactionStat> {
        val filter = TransactionFilter()
        val now: LocalDate = LocalDate.now().minusYears(1)
        filter.startDate = now.withMonth(1).withDayOfMonth(1)
        filter.endDate = now.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.INCOME
        return findStat(filter)
    }

    private fun findTotalExpenseForLastYear(): Single<TransactionStat> {
        val filter = TransactionFilter()
        val now: LocalDate = LocalDate.now().minusYears(1)
        filter.startDate = now.withMonth(1).withDayOfMonth(1)
        filter.endDate = now.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.EXPENSE
        return findStat(filter)
    }

    private fun findTotalIncome(): Single<TransactionStat> {
        val filter = TransactionFilter()
        val now: LocalDate = LocalDate.now()
        filter.startDate = now.withMonth(1).withDayOfMonth(1)
        filter.endDate = now.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.INCOME
        return findStat(filter)
    }

    private fun findTotalExpense(): Single<TransactionStat> {
        val filter = TransactionFilter()
        val now: LocalDate = LocalDate.now()
        filter.startDate = now.withMonth(1).withDayOfMonth(1)
        filter.endDate = now.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.EXPENSE
        return findStat(filter)
    }

    private fun findStat(filter: TransactionFilter): Single<TransactionStat> {
        return transactionRepository.findStats(filter)
    }

    private fun fetchSerial() {
        compositeDisposable.add(
            serialRepository.findById(SERIAL_ID)
                .subscribe(
                    { serial -> _serial.value = serial },
                    { error -> error.printStackTrace() }
                )
        )
    }

}