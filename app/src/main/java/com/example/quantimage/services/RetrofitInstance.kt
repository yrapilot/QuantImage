package com.example.quantimage.services

import okhttp3.Interceptor
import okhttp3.OkHttp
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.math.log
import com.example.quantimage.BuildConfig
import com.example.quantimage.Const

object RetrofitInstance {

    private val retrofit by lazy {
        val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC)
        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .addInterceptor(Interceptor {chain: Interceptor.Chain ->
                val request = chain.request().newBuilder()
                    .addHeader("Authorization","Client-ID " + Const.accesskey)
                    .build()
                chain.proceed(request)
            })
            .build()
        Retrofit.Builder()
            .baseUrl(Const.baseApiUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()
    }
    val api : ApiInterface by lazy {
        retrofit.create(ApiInterface::class.java)
    }
}