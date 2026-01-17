package com.example.testapp.di

import android.content.Context
import androidx.room.Room
import com.example.testapp.data.local.AnswerDao
import com.example.testapp.data.local.TestDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): TestDatabase {
        return Room.databaseBuilder(context, TestDatabase::class.java, "test-db").build()
    }

    @Provides
    fun provideAnswerDao(database: TestDatabase): AnswerDao = database.answerDao()
}
