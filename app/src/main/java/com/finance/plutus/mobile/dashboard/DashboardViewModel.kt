package com.finance.plutus.mobile.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.app.data.CurrencyRateRepository
import com.finance.plutus.mobile.app.data.SerialApiRepository.Companion.SERIAL_ID
import com.finance.plutus.mobile.app.data.SerialRepository
import com.finance.plutus.mobile.app.data.model.CurrencyRate
import com.finance.plutus.mobile.app.data.model.Serial
import com.finance.plutus.mobile.app.network.payload.SerialUpdateRequest
import com.finance.plutus.mobile.app.ui.BaseViewModel
import com.finance.plutus.mobile.transactions.data.TransactionRepository
import com.finance.plutus.mobile.transactions.data.model.TransactionFilter
import com.finance.plutus.mobile.transactions.data.model.TransactionType
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

    private val _stats = MutableLiveData<DashboardStat>()
    private val _serial = MutableLiveData<Serial>()
    private val _rates = MutableLiveData<List<CurrencyRate>>()
    val serial: LiveData<Serial> = _serial
    val rates: LiveData<List<CurrencyRate>> = _rates
    val stats: LiveData<DashboardStat> = _stats

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

    fun findDeductibleExpensesForLastYear() {
        val filter = TransactionFilter()
        val now: LocalDate = LocalDate.now().minusYears(1)
        filter.startDate = now.withMonth(1).withDayOfMonth(1)
        filter.endDate = now.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.EXPENSE
        filter.deductible = true
        findStat("Cheltuieli deductibile 2020", filter)
    }

    fun findTotalIncomeForLastYear() {
        val filter = TransactionFilter()
        val now: LocalDate = LocalDate.now().minusYears(1)
        filter.startDate = now.withMonth(1).withDayOfMonth(1)
        filter.endDate = now.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.INCOME
        findStat("Incasari 2020", filter)
    }

    fun findTotalExpenseForLastYear() {
        val filter = TransactionFilter()
        val now: LocalDate = LocalDate.now().minusYears(1)
        filter.startDate = now.withMonth(1).withDayOfMonth(1)
        filter.endDate = now.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.EXPENSE
        findStat("Cheltuieli 2020", filter)
    }

    fun findTotalIncome() {
        val filter = TransactionFilter()
        val now: LocalDate = LocalDate.now()
        filter.startDate = now.withMonth(1).withDayOfMonth(1)
        filter.endDate = now.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.INCOME
        findStat("Incasari 2021", filter)
    }

    fun findTotalExpense() {
        val filter = TransactionFilter()
        val now: LocalDate = LocalDate.now()
        filter.startDate = now.withMonth(1).withDayOfMonth(1)
        filter.endDate = now.withMonth(12).withDayOfMonth(31)
        filter.type = TransactionType.EXPENSE
        findStat("Cheltuieli 2021", filter)
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

    private fun findStat(name: String, filter: TransactionFilter) {
        compositeDisposable.add(
            transactionRepository.findStats(filter)
                .subscribe(
                    { stat ->
                        _stats.value = DashboardStat(name, stat.total, stat.number)
                    },
                    { error -> error.printStackTrace() }
                )
        )
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