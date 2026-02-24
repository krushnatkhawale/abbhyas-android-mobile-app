package com.kaushalya.abbhyas

import android.content.Context
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TestViewModel : ViewModel() {

    var category by mutableStateOf<Category?>(null)
    var questions = mutableStateListOf<String>()
    var currentIndex by mutableIntStateOf(0)
    var score by mutableIntStateOf(0)
    var timeLeft by mutableIntStateOf(10)
    var isComplete by mutableStateOf(false)

    private var timerJob: Job? = null

    fun startTest(cat: Category) {
        Log.d("TestViewModel", "Starting test for category: ${cat.title}")
        category = cat
        questions.clear()
        questions.addAll(StudyData.getRandomQuestions(cat))
        Log.d("TestViewModel", "Questions loaded: ${questions.size}")
        currentIndex = 0
        score = 0
        isComplete = false

        startTimerForCurrentQuestion()
    }

    private fun startTimerForCurrentQuestion() {
        timerJob?.cancel()
        timeLeft = 10
        Log.d("TestViewModel", "Time left updated: $timeLeft")

        timerJob = viewModelScope.launch {
            for (i in 9 downTo 1) {
                delay(1000)
                timeLeft = i
                Log.d("TestViewModel", "Time left updated: $timeLeft")
            }
            delay(1000)
            timeLeft = 0
            Log.d("TestViewModel", "Time left updated: $timeLeft | Timer expired")
            moveToNextQuestion(isCorrect = false)
        }
    }

    fun markCorrect() {
        moveToNextQuestion(isCorrect = true)
    }

    fun markIncorrect() {
        moveToNextQuestion(isCorrect = false)
    }

    private fun moveToNextQuestion(isCorrect: Boolean) {
        timerJob?.cancel()
        Log.d("TestViewModel", "Moving to next question ${currentIndex + 1} | Correct: $isCorrect")

        if (isCorrect) {
            score++
        }

        currentIndex++

        if (currentIndex >= questions.size) {
            finishTest()
        } else {
            startTimerForCurrentQuestion()
        }
    }

    private fun finishTest() {
        timerJob?.cancel()
        isComplete = true
        Log.d("TestViewModel", "Test complete | Score: $score/${questions.size}")
    }

    fun saveHistory(context: Context) {
        if (category == null) return
        val prefs = context.getSharedPreferences("kids_study", Context.MODE_PRIVATE)

        val oldHistory = prefs.getString("history", "") ?: ""

        val gson = Gson()
        val type = object : TypeToken<Map<String, String>>() {}.type
        val historyMap: Map<String, String> = try {
            gson.fromJson(oldHistory, type) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())

        val reportFullName = "${sdf.format(Date())} • ${category?.title} • $score/10"
        val reportFriendlyName = "${category?.title} • $score/10"
        val testReport = mapOf(reportFullName to reportFriendlyName)

        val newHistoryMap = historyMap + testReport
        val newHistoryJson = gson.toJson(newHistoryMap)

        prefs.edit().putString("history", newHistoryJson).apply()
    }

    fun getHistory(context: Context): List<String> {
        val prefs = context.getSharedPreferences("kids_study", Context.MODE_PRIVATE)
        val raw = prefs.getString("history", "") ?: ""

        val gson = Gson()
        val type = object : TypeToken<Map<String, String>>() {}.type
        val historyMap: Map<String, String> = try {
            gson.fromJson(raw, type) ?: emptyMap()
        } catch (e: Exception) {
            emptyMap()
        }
        return historyMap.values.toList()

    }

    override fun onCleared() {
        timerJob?.cancel()
        super.onCleared()
    }
}