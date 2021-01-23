package com.finance.plutus.mobile.repository.impl

import com.finance.plutus.mobile.model.Partner
import com.finance.plutus.mobile.network.payload.PartnerUpdateRequest
import com.finance.plutus.mobile.network.PlutusService
import com.finance.plutus.mobile.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.network.payload.PlutusRequest
import com.finance.plutus.mobile.repository.PartnerRepository
import io.reactivex.Completable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
class PartnerApiRepository(
    private val plutusService: PlutusService
) : PartnerRepository {

    override fun create(request: PartnerUpdateRequest): Single<EntityCreatedResponse> {
        return plutusService.createPartner(PlutusRequest(request))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun update(id: UUID, request: PartnerUpdateRequest): Completable {
        return plutusService.updatePartner(id, PlutusRequest(request))
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun delete(id: UUID): Completable {
        return plutusService.deletePartner(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
    }

    override fun findById(id: UUID): Single<Partner> {
        return plutusService.findPartnerById(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }
    }

    override fun findAllByPage(page: Int, size: Int): Single<List<Partner>> {
        return plutusService.findAllPartners(page, size)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }
    }
}