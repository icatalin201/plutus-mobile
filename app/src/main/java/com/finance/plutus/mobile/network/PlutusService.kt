package com.finance.plutus.mobile.network

import com.finance.plutus.mobile.model.*
import com.finance.plutus.mobile.network.payload.*
import io.reactivex.Completable
import io.reactivex.Single
import retrofit2.http.*
import java.time.LocalDate
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface PlutusService {

    @GET("banks")
    fun findAllBanks(): Single<PlutusResponse<List<Bank>>>

    @GET("countries")
    fun findAllCountries(): Single<PlutusResponse<List<Country>>>

    @GET("users/business")
    fun getBusiness(): Single<PlutusResponse<Business>>

    @POST("partners")
    fun createPartner(@Body request: PlutusRequest<PartnerUpdateRequest>): Single<EntityCreatedResponse>

    @PUT("partners/{id}")
    fun updatePartner(
        @Path("id") id: UUID,
        @Body request: PlutusRequest<PartnerUpdateRequest>
    ): Completable

    @DELETE("partners/{id}")
    fun deletePartner(@Path("id") id: UUID): Completable

    @GET("partners/{id}")
    fun findPartnerById(@Path("id") id: UUID): Single<PlutusResponse<Partner>>

    @GET("partners")
    fun findAllPartners(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<PlutusResponse<List<Partner>>>

    @POST("items")
    fun createItem(@Body request: PlutusRequest<ItemUpdateRequest>): Single<EntityCreatedResponse>

    @PUT("items/{id}")
    fun updateItem(
        @Path("id") id: UUID,
        @Body request: PlutusRequest<ItemUpdateRequest>
    ): Completable

    @DELETE("items/{id}")
    fun deleteItem(@Path("id") id: UUID): Completable

    @GET("items/{id}")
    fun findItemById(@Path("id") id: UUID): Single<PlutusResponse<Item>>

    @GET("items")
    fun findAllItems(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<PlutusResponse<List<Item>>>

    @PUT("serials/{id}")
    fun updateSerial(
        @Path("id") id: UUID,
        @Body request: PlutusRequest<SerialUpdateRequest>
    ): Completable

    @GET("serials/{id}")
    fun findSerialById(@Path("id") id: UUID): Single<PlutusResponse<Serial>>

    @POST("invoices")
    fun createInvoice(@Body request: PlutusRequest<InvoiceCreateRequest>): Single<EntityCreatedResponse>

    @DELETE("invoices/{id}")
    fun deleteInvoice(@Path("id") id: UUID): Completable

    @GET("invoices/{id}")
    fun findInvoiceById(@Path("id") id: UUID): Single<PlutusResponse<Invoice>>

    @GET("invoices")
    fun findAllInvoices(
        @Query("page") page: Int,
        @Query("size") size: Int
    ): Single<PlutusResponse<List<Invoice>>>

    @POST("invoices/cashing")
    fun collectInvoices(@Query("ids") ids: List<UUID>): Completable

    @GET("invoices/{id}/pdf")
    fun downloadInvoice(@Path("id") id: UUID): Single<*>

    @POST("transactions")
    fun createTransaction(@Body request: PlutusRequest<TransactionCreateRequest>): Single<EntityCreatedResponse>

    @PUT("transactions/{id}")
    fun updateTransaction(
        @Path("id") id: UUID,
        @Body request: PlutusRequest<TransactionUpdateRequest>
    ): Completable

    @DELETE("transactions/{id}")
    fun deleteTransaction(@Path("id") id: UUID): Completable

    @GET("transactions")
    fun findAllTransactions(
        @Query("page") page: Int,
        @Query("size") size: Int,
        @Query("partnerId") partnerId: UUID?,
        @Query("type") type: TransactionType?,
        @Query("startDate") startDate: LocalDate?,
        @Query("endDate") endDate: LocalDate?
    ): Single<PlutusResponse<List<Transaction>>>

    @POST("transactions/cashing")
    fun collectTransactions(@Query("ids") ids: List<UUID>): Completable

    @POST("transactions/file")
    fun uploadTransactionsFile(@Body request: PlutusRequest<UploadFileRequest>): Completable

}