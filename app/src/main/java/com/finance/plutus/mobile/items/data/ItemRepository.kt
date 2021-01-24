package com.finance.plutus.mobile.items.data

import androidx.paging.PagingData
import com.finance.plutus.mobile.app.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.app.network.payload.ItemUpdateRequest
import com.finance.plutus.mobile.items.data.model.Item
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface ItemRepository {

    fun create(request: ItemUpdateRequest): Single<EntityCreatedResponse>
    fun update(id: UUID, request: ItemUpdateRequest): Completable
    fun delete(id: UUID): Completable
    fun findById(id: UUID): Single<Item>
    fun findAll(): Flowable<PagingData<Item>>

}