package com.example.guesstheword

import android.os.CountDownTimer
import android.text.format.DateUtils
import android.util.Log
import android.view.animation.Transformation
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {
    private val _eventGameFinish = MutableLiveData<Boolean>()
    val eventGameFinish: MutableLiveData<Boolean>
    get() = _eventGameFinish

    private var _word = MutableLiveData<String>()
    val word: LiveData<String>
    get() = _word

    val wordHint = Transformations.map(word) {word ->
        val randomPosition = (1..word.length).random()
        "Current word length is" + " " + word.length + " " +  "letters" + "\n the letter at position" + " " + randomPosition + " " + "is" + " " +  word.get(randomPosition - 1).toUpperCase()


    }

    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
    get() = _score

    private lateinit var wordsList: MutableList<String>

    companion object {
        private const val DONE = 0L
        private const val ONE_SECOND = 1000L
        private const val COUNTDOWN_TIME = 60000L
    }

    private val _currentTime = MutableLiveData<Long>()
    val currentTime: LiveData<Long>
    get() = _currentTime
    val currentTimeString = Transformations.map(currentTime) {time ->
        DateUtils.formatElapsedTime(time)

    }

    private val timer: CountDownTimer

    init {
        timer = object : CountDownTimer(COUNTDOWN_TIME, ONE_SECOND) {
            override fun onTick(millisUntilFinished: Long) {
                _currentTime.value = millisUntilFinished/ ONE_SECOND
            }
            override fun onFinish() {
                _currentTime.value = DONE
                onGameFinish()

            }
        }
        timer.start()
        Log.i("GameViewModel", "GameViewModel class is created")
        _word.value = ""
        _score.value = 0
        resetList()
        nextWord()

    }
    override fun onCleared() {
        super.onCleared()
        timer.cancel()

    }
    fun resetList() {
        wordsList = mutableListOf(
            "queen",
            "hospital",
            "basketball",
            "cat",
            "change",
            "snail",
            "soup",
            "calendar",
            "sad",
            "desk",
            "guitar",
            "home",
            "railway",
            "zebra",
            "jelly",
            "car",
            "crow",
            "trade",
            "bag",
            "roll",
            "bubble"
        )
        wordsList.shuffle()
    }
     fun nextWord() {
        if(wordsList.isEmpty()) {
           resetList()
        }
         else {
            _word.value = wordsList.removeAt(0)
        }

    }
     fun onCorrect() {
        _score.value = _score.value?.plus(1)
        nextWord()
    }
    fun onSkip() {
        _score.value = _score.value?.minus(1)
        nextWord()
    }
    fun onGameFinish() {
        _eventGameFinish.value = true
    }
    fun onGameCompleted() {
        _eventGameFinish.value = false
    }
}
