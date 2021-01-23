package com.finance.plutus.mobile.repository

import com.finance.plutus.mobile.model.*
import com.finance.plutus.mobile.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.network.payload.TransactionCreateRequest
import com.finance.plutus.mobile.network.payload.TransactionUpdateRequest
import com.finance.plutus.mobile.network.payload.UploadFileRequest
import io.reactivex.Completable
import io.reactivex.Single
import java.time.LocalDate
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface TransactionRepository {
    fun create(request: TransactionCreateRequest): Single<EntityCreatedResponse>
    fun update(id: UUID, request: TransactionUpdateRequest): Completable
    fun delete(id: UUID): Completable
    fun collect(ids: List<UUID>): Completable
    fun uploadFile(request: UploadFileRequest): Completable
    fun findAllFiltered(
        page: Int,
        size: Int = 30,
        partnerId: UUID?,
        type: TransactionType?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): Single<List<Transaction>>
}