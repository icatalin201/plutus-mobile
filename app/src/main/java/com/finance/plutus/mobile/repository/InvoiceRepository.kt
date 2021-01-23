package com.finance.plutus.mobile.repository

import com.finance.plutus.mobile.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.model.Invoice
import com.finance.plutus.mobile.network.payload.InvoiceCreateRequest
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface InvoiceRepository {
    fun create(request: InvoiceCreateRequest): Single<EntityCreatedResponse>
    fun findById(id: UUID): Single<Invoice>
    fun findAllByPage(page: Int, size: Int = 30): Single<List<Invoice>>
    fun delete(id: UUID): Completable
    fun collect(ids: List<UUID>): Completable
    fun download(id: UUID): Single<*>
}