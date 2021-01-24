package com.finance.plutus.mobile.app.data

import com.finance.plutus.mobile.app.data.model.Serial
import com.finance.plutus.mobile.app.network.payload.SerialUpdateRequest
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface SerialRepository {
    fun findById(id: UUID): Single<Serial>
    fun update(id: UUID, request: SerialUpdateRequest): Completable
}