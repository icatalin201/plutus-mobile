package com.finance.plutus.mobile.transactions.data

import androidx.paging.rxjava2.RxPagingSource
import com.finance.plutus.mobile.app.network.PlutusService
import com.finance.plutus.mobile.app.util.PaginationConstants.PAGE_SIZE
import com.finance.plutus.mobile.app.util.PaginationConstants.STARTING_PAGE
import com.finance.plutus.mobile.transactions.data.model.Transaction
import com.finance.plutus.mobile.transactions.data.model.TransactionFilter
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
class TransactionDataSource(
    private val plutusService: PlutusService,
    private val filter: TransactionFilter
) : RxPagingSource<Int, Transaction>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Transaction>> {
        val page = params.key ?: STARTING_PAGE
        val size = PAGE_SIZE
        return plutusService.findAllTransactions(
            page,
            size,
            filter.partnerId,
            filter.type,
            filter.deductible,
            filter.startDate,
            filter.endDate
        ).observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                LoadResult.Page(
                    data = response.data,
                    prevKey = if (page == STARTING_PAGE) null else page - 1,
                    nextKey = if (response.data.isEmpty()) null else page + 1
                )
            }
    }
}