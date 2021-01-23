package com.finance.plutus.mobile.repository

import com.finance.plutus.mobile.model.Serial
import com.finance.plutus.mobile.network.payload.SerialUpdateRequest
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