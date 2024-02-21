package com.grusie.policyinfo.di

import android.app.Application
import com.grusie.data.service.PolicyService
import com.tickaroo.tikxml.TikXml
import com.tickaroo.tikxml.retrofit.TikXmlConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ApiModule {
    private const val CONNECT_TIMEOUT = 15L
    private const val WRITE_TIMEOUT = 15L
    private const val READ_TIMEOUT = 15L
    private const val POLICY_BASE_URL = "https://www.youthcenter.go.kr/opi/"

    @Provides
    @Singleton
    fun provideCache(application: Application): Cache {
        return Cache(application.cacheDir, 10L * 1024 * 1024)
    }

    @Provides
    @Singleton
    fun provideHttpClient(cache: Cache): OkHttpClient {
        return OkHttpClient.Builder().apply {
            cache(cache)
            connectTimeout(CONNECT_TIMEOUT, TimeUnit.SECONDS)
            writeTimeout(WRITE_TIMEOUT, TimeUnit.SECONDS)
            readTimeout(READ_TIMEOUT, TimeUnit.SECONDS)
            retryOnConnectionFailure(true)
            addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
        }.build()
    }

    @Provides
    @Singleton/*
    @Named("policyRetrofit")*/
    fun providePolicyRetrofit(client: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(POLICY_BASE_URL)
            .addConverterFactory(
                TikXmlConverterFactory.create(
                    TikXml.Builder().exceptionOnUnreadXml(false).build()
                )
            )
            .client(client)
            .build()
    }

    @Provides
    @Singleton
    fun providePolicyService(retrofit: Retrofit): PolicyService {
        return retrofit.create(PolicyService::class.java)
    }
}
