package com.finance.plutus.mobile.data.repository.impl

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.finance.plutus.mobile.data.model.Transaction
import com.finance.plutus.mobile.data.model.TransactionFilter
import com.finance.plutus.mobile.data.model.TransactionStat
import com.finance.plutus.mobile.data.network.PlutusService
import com.finance.plutus.mobile.data.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.data.network.payload.TransactionUpdateRequest
import com.finance.plutus.mobile.data.network.payload.UploadFileRequest
import com.finance.plutus.mobile.data.repository.TransactionRepository
import com.finance.plutus.mobile.data.repository.source.TransactionDataSource
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.io.InputStream
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class TransactionApiRepository(
        private val plutusService: PlutusService
) : TransactionRepository {

    override fun create(request: TransactionUpdateRequest): Single<EntityCreatedResponse> {
        return plutusService.createTransaction(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    override fun update(id: UUID, request: TransactionUpdateRequest): Completable {
        return plutusService.updateTransaction(id, request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    override fun delete(id: UUID): Completable {
        return plutusService.deleteTransaction(id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    override fun collect(ids: List<UUID>): Completable {
        return plutusService.collectTransactions(ids)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    override fun uploadFile(request: UploadFileRequest): Completable {
        return plutusService.uploadTransactionsFile(request)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    override fun findAllFiltered(filter: TransactionFilter): Flowable<PagingData<Transaction>> {
        return Pager(
                config = PagingConfig(pageSize = 30),
                pagingSourceFactory = { TransactionDataSource(plutusService, filter) }
        ).flowable
    }

    override fun findStats(): Single<List<TransactionStat>> {
        return plutusService.findStats()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
    }

    override fun downloadDocument(year: String): Single<InputStream> {
        return plutusService.downloadTransactionsReport(year)
                .map { response ->
                    response.byteStream()
                }
    }
}