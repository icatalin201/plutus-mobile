package com.finance.plutus.mobile.data.repository

import androidx.paging.PagingData
import com.finance.plutus.mobile.data.model.Invoice
import com.finance.plutus.mobile.data.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.data.network.payload.InvoiceUpdateRequest
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.io.InputStream
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface InvoiceRepository {
    fun create(request: InvoiceUpdateRequest): Single<EntityCreatedResponse>
    fun update(id: UUID, request: InvoiceUpdateRequest): Completable
    fun findById(id: UUID): Single<Invoice>
    fun findAll(): Flowable<PagingData<Invoice>>
    fun delete(id: UUID): Completable
    fun collect(ids: List<UUID>): Completable
    fun download(id: UUID): Single<InputStream>
    fun downloadArchive(): Single<InputStream>
}