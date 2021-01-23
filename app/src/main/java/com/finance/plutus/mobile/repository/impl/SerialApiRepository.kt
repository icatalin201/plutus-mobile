package com.finance.plutus.mobile.repository.impl

import com.finance.plutus.mobile.model.Serial
import com.finance.plutus.mobile.network.payload.SerialUpdateRequest
import com.finance.plutus.mobile.network.PlutusService
import com.finance.plutus.mobile.network.payload.PlutusRequest
import com.finance.plutus.mobile.repository.SerialRepository
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