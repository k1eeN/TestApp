package com.example.testapp.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [AnswerEntity::class], version = 1, exportSchema = false)
abstract class TestDatabase : RoomDatabase() {

    abstract fun answerDao(): AnswerDao
}