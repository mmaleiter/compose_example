package com.pixabay.di

import com.pixabay.repository.PixBayItemsRepository
import com.pixabay.repository.PixBayItemsRepositoryImpl
import cut.the.crap.mylibrary.NetworkModule
import cut.the.crap.mylibrary.PixaBayService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module(
    includes = [
        NetworkModule::class
    ]
)
@InstallIn(SingletonComponent::class)
class ApiModule {

    @Singleton
    @Provides
    fun providesRepository(pixaBayService: PixaBayService): PixBayItemsRepository {
        return PixBayItemsRepositoryImpl(pixaBayService)
    }

}
