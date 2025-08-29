package com.example.learning.backend

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlin.random.Random

enum class Track { STRAIGHT, CURVE }

class F1Game {
    companion object {
        const val MAX_SCORE = 10
        const val TURN_TIME = 2
    }

    var track by mutableStateOf(randomTrack())
        private set

    var score by mutableStateOf(0)
        private set

    var message by mutableStateOf("¡Ayuda a Franco Colapinto a ganar la carrera!")
        private set

    var gameOver by mutableStateOf(false)
        private set

    var gameWon by mutableStateOf(false)
        private set

    var timeLeft by mutableStateOf(TURN_TIME)
        private set

    fun nextTurn() {
        track = randomTrack()
        timeLeft = TURN_TIME
    }

    fun tickTime() {
        if (!gameOver && !gameWon) {
            timeLeft--
            if (timeLeft <= 0) {
                gameOver = true
                message = "¡Colapinto chocó! 😵"
            }
        }
    }

    fun chooseAction(action: Track) {
        if (gameOver || gameWon) return

        if (action == track) {
            score++
            if (score >= MAX_SCORE) {
                gameWon = true
                message = "¡Ganó la carrera! 🏆"
            } else {
                nextTurn()
            }
        } else {
            gameOver = true
            message = "¡Colapinto chocó! 😵"
        }
    }

    fun reset() {
        score = 0
        gameOver = false
        gameWon = false
        message = "¡Ayuda a Franco Colapinto a ganar la carrera!"
        nextTurn()
    }

    private fun randomTrack(): Track {
        return if (Random.nextBoolean()) Track.STRAIGHT else Track.CURVE
    }
}
