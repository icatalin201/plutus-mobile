package com.finance.plutus.mobile.data.repository.source

import androidx.paging.rxjava2.RxPagingSource
import com.finance.plutus.mobile.data.model.Invoice
import com.finance.plutus.mobile.data.network.PlutusService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
class InvoiceDataSource(
        private val plutusService: PlutusService
) : RxPagingSource<Int, Invoice>() {

    companion object {
        private const val STARTING_PAGE = 0
    }

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Invoice>> {
        val page = params.key ?: STARTING_PAGE
        val size = params.loadSize
        return plutusService.findAllInvoices(
                page,
                size
        ).observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .map { response ->
                    LoadResult.Page(
                            data = response,
                            prevKey = if (page == STARTING_PAGE) null else page - 1,
                            nextKey = if (response.isEmpty()) null else page + 1
                    )
                }
    }
}