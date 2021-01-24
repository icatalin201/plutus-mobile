package com.finance.plutus.mobile.invoices.data

import androidx.paging.PagingData
import com.finance.plutus.mobile.app.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.app.network.payload.InvoiceUpdateRequest
import com.finance.plutus.mobile.invoices.data.model.Invoice
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface InvoiceRepository {
    fun create(request: InvoiceUpdateRequest): Single<EntityCreatedResponse>
    fun findById(id: UUID): Single<Invoice>
    fun findAll(): Flowable<PagingData<Invoice>>
    fun delete(id: UUID): Completable
    fun collect(ids: List<UUID>): Completable
    fun download(id: UUID): Single<*>
}