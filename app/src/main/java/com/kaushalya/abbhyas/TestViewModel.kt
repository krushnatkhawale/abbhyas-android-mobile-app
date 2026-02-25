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
        Log.d("TestViewModel", "Starting test for category: ${cat.name} (display: ${cat.displayName})")
        category = cat
        questions.clear()
        questions.addAll(StudyData.getRandomQuestions(cat))
        Log.d("TestViewModel", "Questions loaded: ${questions.size}")
        currentIndex = 0
        score = 0
        isComplete = false

        startTimerForCurrentQuestion()
    }

    // ... (all timer, markCorrect, markIncorrect, moveToNextQuestion, finishTest remain EXACTLY same as v3.2)

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
        val sdf = SimpleDateFormat("dd MMM yyyy HH:mm", Locale.getDefault())
        val entry = "${sdf.format(Date())} • ${category?.displayName} • $score/10"   // ← only this line changed

        val old = prefs.getString("history", "") ?: ""
        val newHistory = if (old.isEmpty()) entry else "$old||$entry"
        prefs.edit().putString("history", newHistory).apply()
    }

    fun getHistory(context: Context): List<String> {
        val prefs = context.getSharedPreferences("kids_study", Context.MODE_PRIVATE)
        val raw = prefs.getString("history", "") ?: ""
        return if (raw.isEmpty()) emptyList() else raw.split("||")
    }

    override fun onCleared() {
        timerJob?.cancel()
        super.onCleared()
    }
}