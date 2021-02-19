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
    fun findAllBanks(): Single<PlutusResponse<List<Bank>>>

    @GET("countries")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findAllCountries(): Single<PlutusResponse<List<Country>>>

    @GET("users/business")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun getBusiness(): Single<PlutusResponse<Business>>

    @POST("partners")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun createPartner(@Body request: PlutusRequest<PartnerUpdateRequest>): Single<EntityCreatedResponse>

    @PUT("partners/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun updatePartner(
            @Path("id") id: UUID,
            @Body request: PlutusRequest<PartnerUpdateRequest>
    ): Completable

    @DELETE("partners/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun deletePartner(@Path("id") id: UUID): Completable

    @GET("partners/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findPartnerById(@Path("id") id: UUID): Single<PlutusResponse<Partner>>

    @GET("partners")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findAllPartners(
            @Query("page") page: Int,
            @Query("size") size: Int
    ): Single<PlutusResponse<List<Partner>>>

    @POST("items")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun createItem(@Body request: PlutusRequest<ItemUpdateRequest>): Single<EntityCreatedResponse>

    @PUT("items/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun updateItem(
            @Path("id") id: UUID,
            @Body request: PlutusRequest<ItemUpdateRequest>
    ): Completable

    @DELETE("items/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun deleteItem(@Path("id") id: UUID): Completable

    @GET("items/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findItemById(@Path("id") id: UUID): Single<PlutusResponse<Item>>

    @GET("items")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findAllItems(
            @Query("page") page: Int,
            @Query("size") size: Int
    ): Single<PlutusResponse<List<Item>>>

    @PUT("serials/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun updateSerial(
            @Path("id") id: UUID,
            @Body request: PlutusRequest<SerialUpdateRequest>
    ): Completable

    @GET("serials/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findSerialById(@Path("id") id: UUID): Single<PlutusResponse<Serial>>

    @POST("invoices")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun createInvoice(@Body request: PlutusRequest<InvoiceUpdateRequest>): Single<EntityCreatedResponse>

    @PUT("invoices/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun updateInvoice(
            @Path("id") id: UUID,
            @Body request: PlutusRequest<InvoiceUpdateRequest>
    ): Completable

    @DELETE("invoices/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun deleteInvoice(@Path("id") id: UUID): Completable

    @GET("invoices/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findInvoiceById(@Path("id") id: UUID): Single<PlutusResponse<Invoice>>

    @GET("invoices")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findAllInvoices(
            @Query("page") page: Int,
            @Query("size") size: Int
    ): Single<PlutusResponse<List<Invoice>>>

    @POST("invoices/cashing")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun collectInvoices(@Query("ids") ids: List<UUID>): Completable

    @GET("invoices/{id}/pdf")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun downloadInvoice(@Path("id") id: UUID): Single<ResponseBody>

    @POST("transactions")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun createTransaction(@Body request: PlutusRequest<TransactionUpdateRequest>): Single<EntityCreatedResponse>

    @PUT("transactions/{id}")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun updateTransaction(
            @Path("id") id: UUID,
            @Body request: PlutusRequest<TransactionUpdateRequest>
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
    ): Single<PlutusResponse<List<Transaction>>>

    @GET("transactions/stats")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun findStats(): Single<PlutusResponse<List<TransactionStat>>>

    @POST("transactions/cashing")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun collectTransactions(@Query("ids") ids: List<UUID>): Completable

    @POST("transactions/file")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun uploadTransactionsFile(@Body request: PlutusRequest<UploadFileRequest>): Completable

    @GET("rates/today")
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun fetchTodayRates(): Single<PlutusResponse<List<CurrencyRate>>>

    @GET("transactions/document/{year}")
    @Streaming
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun downloadTransactionsReport(@Path("year") year: String): Single<ResponseBody>

    @GET("invoices/archive")
    @Streaming
    @Headers("Content-Type: application/vnd.plutus.finance+json")
    fun downloadInvoicesArchive(): Single<ResponseBody>

}