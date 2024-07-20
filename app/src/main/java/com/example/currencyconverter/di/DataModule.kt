package com.example.currencyconverter.di

import com.example.currencyconverter.data.CurrencyRepositoryImpl
import com.example.currencyconverter.data.CurrencyService
import com.example.currencyconverter.data.mappers.CurrenciesMapper
import com.example.currencyconverter.data.mappers.CurrenciesMapperImpl
import com.example.currencyconverter.domain.CurrencyRepository
import com.google.gson.GsonBuilder
import dagger.Binds
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
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
                .addConverterFactory(ScalarsConverterFactory.create())
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
            service: CurrencyService,
            mapper: CurrenciesMapper
        ): CurrencyRepository {
            return CurrencyRepositoryImpl(Dispatchers.IO, service, mapper)
        }
    }

    @Binds
    fun bindsCurrencyMapper(impl: CurrenciesMapperImpl): CurrenciesMapper
}