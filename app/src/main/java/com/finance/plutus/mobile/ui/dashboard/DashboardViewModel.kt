package com.finance.plutus.mobile.ui.dashboard

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.work.Data
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.finance.plutus.mobile.data.model.CurrencyRate
import com.finance.plutus.mobile.data.model.Serial
import com.finance.plutus.mobile.data.model.TransactionStat
import com.finance.plutus.mobile.data.network.payload.SerialUpdateRequest
import com.finance.plutus.mobile.data.repository.CurrencyRateRepository
import com.finance.plutus.mobile.data.repository.SerialRepository
import com.finance.plutus.mobile.data.repository.TransactionRepository
import com.finance.plutus.mobile.data.repository.impl.SerialApiRepository.Companion.SERIAL_ID
import com.finance.plutus.mobile.service.InvoicesArchiveDownloader
import com.finance.plutus.mobile.service.TransactionsReportDownloader
import com.finance.plutus.mobile.ui.BaseViewModel

/**
Plutus Finance
Created by Catalin on 1/25/2021
 **/
class DashboardViewModel(
        private val serialRepository: SerialRepository,
        private val currencyRateRepository: CurrencyRateRepository,
        private val transactionRepository: TransactionRepository
) : BaseViewModel() {

    private val _stats = MutableLiveData<List<TransactionStat>>()
    private val _serial = MutableLiveData<Serial>()
    private val _rates = MutableLiveData<List<CurrencyRate>>()
    val serial: LiveData<Serial> = _serial
    val rates: LiveData<List<CurrencyRate>> = _rates
    val stats: LiveData<List<TransactionStat>> = _stats

    init {
        fetchSerial()
        compositeDisposable.add(
                currencyRateRepository.fetchTodayRates()
                        .subscribe(
                                { rates -> _rates.value = rates },
                                { error -> error.printStackTrace() }
                        )
        )
        compositeDisposable.add(
                transactionRepository.findStats()
                        .subscribe(
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