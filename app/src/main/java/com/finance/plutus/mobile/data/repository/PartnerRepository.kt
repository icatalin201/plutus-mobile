package com.finance.plutus.mobile.data.repository

import androidx.paging.PagingData
import com.finance.plutus.mobile.data.model.Partner
import com.finance.plutus.mobile.data.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.data.network.payload.PartnerUpdateRequest
import io.reactivex.Completable
import io.reactivex.Flowable
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
    fun findAll(): Flowable<PagingData<Partner>>
    fun findAllNonPaged(): Flowable<List<Partner>>

}