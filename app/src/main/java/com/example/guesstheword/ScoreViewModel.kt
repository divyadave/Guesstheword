package com.example.guesstheword

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel(finalScore: Int): ViewModel() {
    private var _eventPlayAgain = MutableLiveData<Boolean>()
    val eventPlayAgain: LiveData<Boolean>
    get() = _eventPlayAgain
    private var _score = MutableLiveData<Int>()
    val score: LiveData<Int>
    get() = _score

    init {
        _score.value = finalScore
        Log.i("ScoreViewModel", "Final Score is $score")

    }
    fun onPlayAgain() {
        _eventPlayAgain.value = true
    }
    fun onPlayComplete() {
        _eventPlayAgain.value = false
    }
}