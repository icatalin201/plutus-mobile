package com.finance.plutus.mobile.items.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.finance.plutus.mobile.app.network.PlutusService
import com.finance.plutus.mobile.app.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.app.network.payload.ItemUpdateRequest
import com.finance.plutus.mobile.app.network.payload.PlutusRequest
import com.finance.plutus.mobile.items.data.model.Item
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
class ItemApiRepository(
    private val plutusService: PlutusService
) : ItemRepository {

    override fun create(request: ItemUpdateRequest): Single<EntityCreatedResponse> {
        return plutusService.createItem(PlutusRequest(request))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun update(id: UUID, request: ItemUpdateRequest): Completable {
        return plutusService.updateItem(id, PlutusRequest(request))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun delete(id: UUID): Completable {
        return plutusService.deleteItem(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun findById(id: UUID): Single<Item> {
        return plutusService.findItemById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }
    }

    override fun findAll(): Flowable<PagingData<Item>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { ItemDataSource(plutusService) }
        ).flowable
    }

    override fun findAllNonPaged(): Flowable<List<Item>> {
        return plutusService.findAllItems(0, 100)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }.toFlowable()
    }
}