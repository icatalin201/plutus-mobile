package com.finance.plutus.mobile.data.repository

import androidx.paging.PagingData
import com.finance.plutus.mobile.data.model.Transaction
import com.finance.plutus.mobile.data.model.TransactionFilter
import com.finance.plutus.mobile.data.model.TransactionStat
import com.finance.plutus.mobile.data.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.data.network.payload.TransactionUpdateRequest
import com.finance.plutus.mobile.data.network.payload.UploadFileRequest
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.io.InputStream
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface TransactionRepository {
    fun create(request: TransactionUpdateRequest): Single<EntityCreatedResponse>
    fun update(id: UUID, request: TransactionUpdateRequest): Completable
    fun delete(id: UUID): Completable
    fun collect(ids: List<UUID>): Completable
    fun uploadFile(request: UploadFileRequest): Completable
    fun findAllFiltered(filter: TransactionFilter): Flowable<PagingData<Transaction>>
    fun findStats(): Single<List<TransactionStat>>
    fun downloadDocument(year: String): Single<InputStream>
}