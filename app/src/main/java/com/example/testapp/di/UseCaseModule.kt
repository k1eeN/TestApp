package com.example.testapp.di

import com.example.testapp.domain.repository.TestRepository
import com.example.testapp.domain.usecase.CalculateResultUseCase
import com.example.testapp.domain.usecase.ClearAnswersUseCase
import com.example.testapp.domain.usecase.GetQuestionsUseCase
import com.example.testapp.domain.usecase.GetSavedAnswersUseCase
import com.example.testapp.domain.usecase.SaveAnswerUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    fun provideGetQuestionsUseCase(repository: TestRepository): GetQuestionsUseCase {
        return GetQuestionsUseCase(repository)
    }

    @Provides
    fun provideSaveAnswerUseCase(repository: TestRepository): SaveAnswerUseCase {
        return SaveAnswerUseCase(repository)
    }

    @Provides
    fun provideGetSavedAnswersUseCase(repository: TestRepository): GetSavedAnswersUseCase {
        return GetSavedAnswersUseCase(repository)
    }

    @Provides
    fun provideClearAnswersUseCase(repository: TestRepository): ClearAnswersUseCase {
        return ClearAnswersUseCase(repository)
    }

    @Provides
    fun provideCalculateResultUseCase(): CalculateResultUseCase = CalculateResultUseCase()
}
