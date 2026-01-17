package com.example.testapp.di

import com.example.testapp.data.local.AnswerDao
import com.example.testapp.data.network.TestApi
import com.example.testapp.data.repository.TestRepositoryImpl
import com.example.testapp.domain.repository.TestRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideTestRepository(api: TestApi, answerDao: AnswerDao): TestRepository {
        return TestRepositoryImpl(api, answerDao)
    }
}
