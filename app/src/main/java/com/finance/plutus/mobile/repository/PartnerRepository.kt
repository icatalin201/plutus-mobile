package com.finance.plutus.mobile.repository

import com.finance.plutus.mobile.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.model.Partner
import com.finance.plutus.mobile.network.payload.PartnerUpdateRequest
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface PartnerRepository {

    fun create(request: PartnerUpdateRequest): Single<EntityCreatedResponse>
    fun update(id: UUID, request: PartnerUpdateRequest): Completable
    fun delete(id: UUID): Completable
    fun findById(id: UUID): Single<Partner>
    fun findAllByPage(page: Int, size: Int = 30): Single<List<Partner>>

}