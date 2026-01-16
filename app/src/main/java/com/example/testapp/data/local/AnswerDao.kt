package com.example.testapp.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AnswerDao {
    @Query("SELECT * FROM answers")
    suspend fun getAll(): List<AnswerEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(answer: AnswerEntity)

    @Query("DELETE FROM answers")
    suspend fun clearAll()
}