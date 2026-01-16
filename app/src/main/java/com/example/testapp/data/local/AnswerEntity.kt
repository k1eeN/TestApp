package com.example.testapp.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "answers")
data class AnswerEntity(
    @PrimaryKey val questionId: Int,
    val selectedAnswerId: Int
)