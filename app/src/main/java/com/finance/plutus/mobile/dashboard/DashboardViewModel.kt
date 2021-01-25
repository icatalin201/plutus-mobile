package com.finance.plutus.mobile.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.finance.plutus.mobile.app.data.SerialApiRepository.Companion.SERIAL_ID
import com.finance.plutus.mobile.app.data.SerialRepository
import com.finance.plutus.mobile.app.data.model.Serial
import com.finance.plutus.mobile.app.network.payload.SerialUpdateRequest
import com.finance.plutus.mobile.app.ui.BaseViewModel

/**
Plutus Finance
Created by Catalin on 1/25/2021
 **/
class DashboardViewModel(
    private val serialRepository: SerialRepository
) : BaseViewModel() {

    private val _serial = MutableLiveData<Serial>()
    val serial: LiveData<Serial> = _serial

    init {
        fetchSerial()
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