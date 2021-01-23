package com.finance.plutus.mobile.repository

import com.finance.plutus.mobile.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.model.Item
import com.finance.plutus.mobile.network.payload.ItemUpdateRequest
import io.reactivex.Completable
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
    fun findAllByPage(page: Int, size: Int = 30): Single<List<Item>>

}