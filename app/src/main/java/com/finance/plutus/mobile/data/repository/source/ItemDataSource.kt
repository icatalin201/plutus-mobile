package com.finance.plutus.mobile.data.repository.source

import androidx.paging.rxjava2.RxPagingSource
import com.finance.plutus.mobile.data.model.Item
import com.finance.plutus.mobile.data.network.PlutusService
import com.finance.plutus.mobile.util.PaginationConstants.PAGE_SIZE
import com.finance.plutus.mobile.util.PaginationConstants.STARTING_PAGE
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
Plutus Finance
Created by Catalin on 1/24/2021
 **/
class ItemDataSource(
        private val plutusService: PlutusService
) : RxPagingSource<Int, Item>() {

    override fun loadSingle(params: LoadParams<Int>): Single<LoadResult<Int, Item>> {
        val page = params.key ?: STARTING_PAGE
        val size = PAGE_SIZE
        return plutusService.findAllItems(
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