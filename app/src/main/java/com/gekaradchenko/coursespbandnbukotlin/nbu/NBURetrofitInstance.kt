package com.gekaradchenko.coursespbandnbukotlin.nbu


import com.jakewharton.retrofit2.adapter.kotlin.coroutines.CoroutineCallAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import kotlinx.coroutines.Deferred
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private const val BASE_URL = "https://bank.gov.ua/NBUStatService/v1/statdirectory/"
private const val GET = "exchange"
private const val DATE = "date"
private const val JSON = "json"

private val moshi = Moshi.Builder()
    .add(KotlinJsonAdapterFactory())
    .build()

private val retrofit = Retrofit.Builder()
    .addConverterFactory(MoshiConverterFactory.create(moshi))
    .addCallAdapterFactory(CoroutineCallAdapterFactory())
    .baseUrl(BASE_URL)
    .build()


interface NBUCourseApiService {
    @GET(GET)
    fun getNBUCourse(
        @Query(DATE) nowDate: String,
        @Query(JSON) json: Boolean,
    ): Deferred<List<NBUCourse>>
}

object NBUCourseApi {
    val RETROFIT_INSTANCE: NBUCourseApiService by lazy {
        retrofit.create(NBUCourseApiService::class.java)
    }
}