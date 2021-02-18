package com.finance.plutus.mobile.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.finance.plutus.mobile.data.model.*
import com.finance.plutus.mobile.data.network.payload.SerialUpdateRequest
import com.finance.plutus.mobile.data.repository.CurrencyRateRepository
import com.finance.plutus.mobile.data.repository.SerialRepository
import com.finance.plutus.mobile.data.repository.TransactionRepository
import com.finance.plutus.mobile.data.repository.impl.SerialApiRepository.Companion.SERIAL_ID
import com.finance.plutus.mobile.service.InvoicesArchiveDownloader
import com.finance.plutus.mobile.service.TransactionsReportDownloader
import com.finance.plutus.mobile.ui.BaseViewModel
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
                Single.zip(prepareTransactionStatRequests())
                { responses ->
                    val stats = mutableListOf<DashboardStat>()
                    responses.forEachIndexed { index, element ->
                        val name = when (index) {
                            0 -> "Cheltuieli deductibile 2021"
                            1 -> "Cheltuieli deductibile 2020"
                            2 -> "Cheltuieli deductibile 2019"
                            3 -> "Cheltuieli 2021"
                            4 -> "Cheltuieli 2020"
                            5 -> "Cheltuieli 2019"
                            6 -> "Incasari 2021"
                            7 -> "Incasari 2020"
                            8 -> "Incasari 2019"
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

    private fun prepareTransactionStatRequests(): List<Single<TransactionStat>> {
        val now = LocalDate.now()
        return mutableListOf(
                findDeductibleExpenses(now),
                findDeductibleExpenses(now.minusYears(1)),
                findDeductibleExpenses(now.minusYears(2)),
                findTotalExpense(now),
                findTotalExpense(now.minusYears(1)),
                findTotalExpense(now.minusYears(2)),
                findTotalIncome(now),
                findTotalIncome(now.minusYears(1)),
                findTotalIncome(now.minusYears(2)),
        )
    }

    private fun findTotalIncome(date: LocalDate): Single<TransactionStat> {
        val filter = TransactionFilter()
        filter.startDate = date.withMonth(1).withDayOfMonth(1)
        filter.endDate = date.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.INCOME
        return findStat(filter)
    }

    private fun findTotalExpense(date: LocalDate): Single<TransactionStat> {
        val filter = TransactionFilter()
        filter.startDate = date.withMonth(1).withDayOfMonth(1)
        filter.endDate = date.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.EXPENSE
        return findStat(filter)
    }

    private fun findDeductibleExpenses(date: LocalDate): Single<TransactionStat> {
        val filter = TransactionFilter()
        filter.startDate = date.withMonth(1).withDayOfMonth(1)
        filter.endDate = date.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.EXPENSE
        filter.deductible = true
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