package com.finance.plutus.mobile.repository.impl

import com.finance.plutus.mobile.model.*
import com.finance.plutus.mobile.network.PlutusService
import com.finance.plutus.mobile.network.payload.*
import com.finance.plutus.mobile.repository.TransactionRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.time.LocalDate
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class TransactionApiRepository(
    private val plutusService: PlutusService
) : TransactionRepository {

    override fun create(request: TransactionCreateRequest): Single<EntityCreatedResponse> {
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

    override fun findAllFiltered(
        page: Int,
        size: Int,
        partnerId: UUID?,
        type: TransactionType?,
        startDate: LocalDate?,
        endDate: LocalDate?
    ): Single<List<Transaction>> {
        return plutusService.findAllTransactions(page, size, partnerId, type, startDate, endDate)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }
    }
}