package com.example.testapp.data.network

import com.example.testapp.data.model.QuestionDto


interface TestApi {

    suspend fun getQuestions(): List<QuestionDto>
}