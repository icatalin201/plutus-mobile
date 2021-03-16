package com.finance.plutus.mobile.data.network

import com.finance.plutus.mobile.data.model.*
import com.finance.plutus.mobile.data.network.payload.*
import io.reactivex.Completable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.http.*
import java.time.LocalDate
import java.util.*

/**
Plutus Finance
Created by Catalin on 1/23/2021
 **/
interface PlutusService {

    @GET("banks")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findAllBanks(): Single<List<Bank>>

    @GET("countries")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findAllCountries(): Single<List<Country>>

    @GET("users/business")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun getBusiness(): Single<Business>

    @POST("partners")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun createPartner(@Body request: PartnerUpdateRequest): Single<EntityCreatedResponse>

    @PUT("partners/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun updatePartner(
            @Path("id") id: UUID,
            @Body request: PartnerUpdateRequest
    ): Completable

    @DELETE("partners/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun deletePartner(@Path("id") id: UUID): Completable

    @GET("partners/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findPartnerById(@Path("id") id: UUID): Single<Partner>

    @GET("partners")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findAllPartners(
            @Query("page") page: Int,
            @Query("size") size: Int
    ): Single<List<Partner>>

    @POST("items")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun createItem(@Body request: ItemUpdateRequest): Single<EntityCreatedResponse>

    @PUT("items/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun updateItem(
            @Path("id") id: UUID,
            @Body request: ItemUpdateRequest
    ): Completable

    @DELETE("items/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun deleteItem(@Path("id") id: UUID): Completable

    @GET("items/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findItemById(@Path("id") id: UUID): Single<Item>

    @GET("items")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findAllItems(
            @Query("page") page: Int,
            @Query("size") size: Int
    ): Single<List<Item>>

    @PUT("serials/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun updateSerial(
            @Path("id") id: UUID,
            @Body request: SerialUpdateRequest
    ): Completable

    @GET("serials/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findSerialById(@Path("id") id: UUID): Single<Serial>

    @POST("invoices")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun createInvoice(@Body request: InvoiceUpdateRequest): Single<EntityCreatedResponse>

    @PUT("invoices/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun updateInvoice(
            @Path("id") id: UUID,
            @Body request: InvoiceUpdateRequest
    ): Completable

    @DELETE("invoices/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun deleteInvoice(@Path("id") id: UUID): Completable

    @GET("invoices/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findInvoiceById(@Path("id") id: UUID): Single<Invoice>

    @GET("invoices")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findAllInvoices(
            @Query("page") page: Int,
            @Query("size") size: Int
    ): Single<List<Invoice>>

    @POST("invoices/cashing")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun collectInvoices(@Query("ids") ids: List<UUID>): Completable

    @GET("invoices/{id}/pdf")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun downloadInvoice(@Path("id") id: UUID): Single<ResponseBody>

    @POST("transactions")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun createTransaction(@Body request: TransactionUpdateRequest): Single<EntityCreatedResponse>

    @PUT("transactions/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun updateTransaction(
            @Path("id") id: UUID,
            @Body request: TransactionUpdateRequest
    ): Completable

    @DELETE("transactions/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun deleteTransaction(@Path("id") id: UUID): Completable

    @GET("transactions")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findAllTransactions(
            @Query("page") page: Int,
            @Query("size") size: Int,
            @Query("partnerId") partnerId: UUID?,
            @Query("type") type: TransactionType?,
            @Query("deductible") deductible: Boolean?,
            @Query("startDate") startDate: LocalDate?,
            @Query("endDate") endDate: LocalDate?
    ): Single<List<Transaction>>

    @GET("transactions/stats")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findStats(): Single<List<TransactionStat>>

    @POST("transactions/cashing")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun collectTransactions(@Query("ids") ids: List<UUID>): Completable

    @POST("transactions/file")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun uploadTransactionsFile(@Body request: UploadFileRequest): Completable

    @GET("rates/today")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun fetchTodayRates(): Single<List<CurrencyRate>>

    @GET("transactions/document/{year}")
    @Streaming
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun downloadTransactionsReport(@Path("year") year: String): Single<ResponseBody>

    @GET("invoices/archive")
    @Streaming
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun downloadInvoicesArchive(): Single<ResponseBody>

}