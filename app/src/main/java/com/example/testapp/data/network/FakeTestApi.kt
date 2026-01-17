package com.example.testapp.data.network

import android.content.res.AssetManager
import com.example.testapp.data.model.QuestionDto
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.delay
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class FakeTestApi(
    private val assetManager: AssetManager,
    private val gson: Gson,
    private val assetFileName: String = "questions_movie_valid.json"
) : TestApi {

    override suspend fun getQuestions(): List<QuestionDto> = withContext(Dispatchers.IO) {
        delay(600)
        val json = assetManager.open(assetFileName).bufferedReader().use {
            reader -> reader.readText()
        }
        val type = object : TypeToken<List<QuestionDto>>() {}.type
        gson.fromJson(json, type)
    }
}