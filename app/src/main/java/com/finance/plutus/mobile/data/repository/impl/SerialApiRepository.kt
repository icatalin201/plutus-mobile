package com.finance.plutus.mobile.data.repository.impl

import com.finance.plutus.mobile.data.model.Serial
import com.finance.plutus.mobile.data.network.PlutusService
import com.finance.plutus.mobile.data.network.payload.SerialUpdateRequest
import com.finance.plutus.mobile.data.repository.SerialRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class SerialApiRepository(
        private val plutusService: PlutusService
) : SerialRepository {

    companion object {
        val SERIAL_ID: UUID = UUID.fromString("2e978bc3-115d-4226-90a7-24bd24ef5054")
    }

    override fun findById(id: UUID): Single<Serial> {
        return plutusService.findSerialById(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    override fun update(id: UUID, request: SerialUpdateRequest): Completable {
        return plutusService.updateSerial(id, request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }
}