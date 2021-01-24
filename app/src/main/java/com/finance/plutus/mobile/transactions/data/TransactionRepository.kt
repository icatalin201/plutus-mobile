package com.finance.plutus.mobile.transactions.data

import androidx.paging.PagingData
import com.finance.plutus.mobile.app.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.app.network.payload.TransactionUpdateRequest
import com.finance.plutus.mobile.app.network.payload.UploadFileRequest
import com.finance.plutus.mobile.transactions.data.model.Transaction
import com.finance.plutus.mobile.transactions.data.model.TransactionFilter
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
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
}