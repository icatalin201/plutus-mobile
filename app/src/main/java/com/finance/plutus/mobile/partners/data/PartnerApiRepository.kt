package com.finance.plutus.mobile.partners.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.rxjava2.flowable
import com.finance.plutus.mobile.app.network.PlutusService
import com.finance.plutus.mobile.app.network.payload.EntityCreatedResponse
import com.finance.plutus.mobile.app.network.payload.PartnerUpdateRequest
import com.finance.plutus.mobile.app.network.payload.PlutusRequest
import com.finance.plutus.mobile.partners.data.model.Partner
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

    override fun findAll(): Flowable<PagingData<Partner>> {
        return Pager(
            config = PagingConfig(pageSize = 30),
            pagingSourceFactory = { PartnerDataSource(plutusService) }
        ).flowable
    }

    override fun findAllNonPaged(): Flowable<List<Partner>> {
        return plutusService.findAllPartners(0, 100)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map { response ->
                response.data
            }.toFlowable()
    }
}