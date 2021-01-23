package com.finance.plutus.mobile.repository.impl

import com.finance.plutus.mobile.model.Invoice
import com.finance.plutus.mobile.network.payload.InvoiceCreateRequest
import com.finance.plutus.mobile.network.PlutusService
import com.finance.plutus.mobile.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.network.payload.PlutusRequest
import com.finance.plutus.mobile.repository.InvoiceRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class InvoiceApiRepository(
    private val plutusService: PlutusService
) : InvoiceRepository {

    override fun create(request: InvoiceCreateRequest): Single<EntityCreatedResponse> {
        return plutusService.createInvoice(PlutusRequest(request))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun findById(id: UUID): Single<Invoice> {
        return plutusService.findInvoiceById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }
    }

    override fun findAllByPage(page: Int, size: Int): Single<List<Invoice>> {
        return plutusService.findAllInvoices(page, size)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }
    }

    override fun delete(id: UUID): Completable {
        return plutusService.deleteInvoice(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun collect(ids: List<UUID>): Completable {
        return plutusService.collectInvoices(ids)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun download(id: UUID): Single<*> {
        return plutusService.downloadInvoice(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }
}