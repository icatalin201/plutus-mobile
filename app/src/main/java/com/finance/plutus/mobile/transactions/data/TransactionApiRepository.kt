package com.finance.plutus.mobile.transactions.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.finance.plutus.mobile.app.network.PlutusService
import com.finance.plutus.mobile.app.network.payload.*
import com.finance.plutus.mobile.transactions.data.model.Transaction
import com.finance.plutus.mobile.transactions.data.model.TransactionFilter
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class TransactionApiRepository(
    private val plutusService: PlutusService
) : TransactionRepository {

    override fun create(request: TransactionUpdateRequest): Single<EntityCreatedResponse> {
        return plutusService.createTransaction(PlutusRequest(request))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun update(id: UUID, request: TransactionUpdateRequest): Completable {
        return plutusService.updateTransaction(id, PlutusRequest(request))
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
        return plutusService.uploadTransactionsFile(PlutusRequest(request))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun findAllFiltered(filter: TransactionFilter): Flowable<PagingData<Transaction>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { TransactionDataSource(plutusService, filter) }
        ).flowable
    }
}