package com.finance.plutus.mobile.app.data

import com.finance.plutus.mobile.app.data.model.Serial
import com.finance.plutus.mobile.app.network.PlutusService
import com.finance.plutus.mobile.app.network.payload.PlutusRequest
import com.finance.plutus.mobile.app.network.payload.SerialUpdateRequest
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
            .map { response ->
                response.data
            }
    }

    override fun update(id: UUID, request: SerialUpdateRequest): Completable {
        return plutusService.updateSerial(id, PlutusRequest(request))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}