package com.example.currencyconverter.di

import com.example.currencyconverter.shared.currency.data.CurrencyRepositoryImpl
import com.example.currencyconverter.shared.currency.data.CurrencyService
import com.example.currencyconverter.shared.currency.domain.CurrencyRepository
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
interface DataModule {
    companion object {

        private const val API_KEY = "fca_live_QH9IKAPvCwRczNsJbNuSnQm6Lz404uQCiPwrwyWt"
        private const val BASE_URL = "https://api.freecurrencyapi.com/v1/"

        @Singleton
        @Provides
        fun provideRetrofit(): Retrofit {
            val authInterceptor = Interceptor { chain ->
                val requestBuilder = chain.request().newBuilder()
                requestBuilder.addHeader(
                    "apikey",
                    API_KEY
                )
                val response = chain.proceed(requestBuilder.build())
                return@Interceptor response
            }


            val client = OkHttpClient.Builder()
                .writeTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .connectTimeout(30, TimeUnit.SECONDS)
                .addInterceptor(authInterceptor)
                .build()

            return Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(
                    GsonConverterFactory.create(
                        GsonBuilder().setLenient().create()
                    )
                )
                .build()
        }

        @Singleton
        @Provides
        fun provideCurrencyService(retrofit: Retrofit): CurrencyService {
            return retrofit.create(CurrencyService::class.java)
        }

        @Provides
        fun provideCurrencyRepository(
            service: CurrencyService
        ): CurrencyRepository {
            return CurrencyRepositoryImpl(
                Dispatchers.IO,
                service
            )
        }
    }
}