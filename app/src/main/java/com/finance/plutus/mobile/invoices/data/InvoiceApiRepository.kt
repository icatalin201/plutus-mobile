package com.finance.plutus.mobile.invoices.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.finance.plutus.mobile.app.network.PlutusService
import com.finance.plutus.mobile.app.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.app.network.payload.InvoiceCreateRequest
import com.finance.plutus.mobile.app.network.payload.PlutusRequest
import com.finance.plutus.mobile.invoices.data.model.Invoice
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

    override fun findAll(): Flowable<PagingData<Invoice>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { InvoiceDataSource(plutusService) }
        ).flowable
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