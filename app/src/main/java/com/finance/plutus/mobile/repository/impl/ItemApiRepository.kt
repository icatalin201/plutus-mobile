package com.finance.plutus.mobile.repository.impl

import com.finance.plutus.mobile.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.model.Item
import com.finance.plutus.mobile.network.payload.ItemUpdateRequest
import com.finance.plutus.mobile.network.PlutusService
import com.finance.plutus.mobile.network.payload.PlutusRequest
import com.finance.plutus.mobile.repository.ItemRepository
import io.reactivex.Completable
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

    override fun findAllByPage(page: Int, size: Int): Single<List<Item>> {
        return plutusService.findAllItems(page, size)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }
    }
}