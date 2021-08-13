package com.gekaradchenko.coursespbandnbukotlin.privatebank

import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private const val BASE_URL = "https://api.privatbank.ua/p24api/"
private const val DATE = "date"
private const val JSON = "json"
private const val GET = "exchange_rates"


private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()


private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface PBCourseApiService {
    @GET(GET)
    fun getPBCourse(
        @Query(JSON) json: Boolean,
        @Query(DATE) nowDate: String
    ): Deferred<PB>
}


object PBCourseApi {
    val RETROFIT_INSTANCE: PBCourseApiService by lazy {
        retrofit.create(PBCourseApiService::class.java)
    }
}
