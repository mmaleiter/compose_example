package com.pixabay.di

import com.pixabay.apiservice.ApiKeyInterceptor
import com.pixabay.apiservice.PixaBayService
import com.pixabay.repository.PixBayItemsRepository
import com.pixabay.repository.PixBayItemsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    private const val BASE_URL = "https://pixabay.com/"

    @Provides
    fun providesHttpLoggingInterceptor() = HttpLoggingInterceptor()
        .apply {
            level = HttpLoggingInterceptor.Level.BODY
        }

    @Provides
    fun providesApikeyInterceptor() = ApiKeyInterceptor()

    @Singleton
    @Provides
    fun providesOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        apiKeyInterceptor: ApiKeyInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(apiKeyInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): PixaBayService {
        return retrofit.create(PixaBayService::class.java)
    }

    @Singleton
    @Provides
    fun providesRepository(pixaBayService: PixaBayService): PixBayItemsRepository {
        return PixBayItemsRepositoryImpl(pixaBayService)
    }
}
