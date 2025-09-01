package com.example.learning.backend

const val P1 = 0
const val P2 = 1
const val P1WIN = 1
const val P2WIN = 2
const val TIE = 0

enum class Options {
    Piedra,
    Papel,
    Tijera,
}

// Determina el resultado de una sola partida
fun combination(play: Array<String>): Int {
    return when (play[P1]) {
        "Piedra" -> when (play[P2]) {
            "Tijera" -> P1WIN
            "Papel" -> P2WIN
            else -> TIE
        }
        "Papel" -> when (play[P2]) {
            "Piedra" -> P1WIN
            "Tijera" -> P2WIN
            else -> TIE
        }
        "Tijera" -> when (play[P2]) {
            "Papel" -> P1WIN
            "Piedra" -> P2WIN
            else -> TIE
        }
        else -> TIE // entrada inválida
    }
}

// Devuelve el ganador como texto
fun winner(p1Choice: String, p2Choice: String): String {
    return when (combination(arrayOf(p1Choice, p2Choice))) {
        P1WIN -> "Player 1"
        P2WIN -> "Player 2"
        else -> "Tie"
    }
}
