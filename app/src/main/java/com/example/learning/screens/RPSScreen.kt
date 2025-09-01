package com.example.learning.screens
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.learning.backend.winner


const val P1TURN = 0
const val P2TURN = 1
const val RESULT = 2

@Composable
fun RockPaperScissorsApp() {
    var screen by remember { mutableStateOf(P1TURN) }
    var p1Choice by remember { mutableStateOf("") }
    var p2Choice by remember { mutableStateOf("") }


    when (screen) {
        P1TURN -> PlayerScreen(
            player = "Jugador 1",
            onSelect = {
                p1Choice = it
                screen = P2TURN
            }
        )
        P2TURN -> PlayerScreen(
            player = "Jugador 2",
            onSelect = {
                p2Choice = it
                screen = RESULT
            }
        )
        RESULT -> ResultScreen(p1Choice, p2Choice) {
            // Reiniciar el juego
            p1Choice = ""
            p2Choice = ""
            screen = P1TURN
        }
    }
}

@Composable
fun PlayerScreen(player: String, onSelect: (String) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("$player, elige tu jugada:", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(20.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                Button(onClick = { onSelect("Piedra") }) { Text("🪨 Piedra") }
                Button(onClick = { onSelect("Papel") }) { Text("📄 Papel") }
                Button(onClick = { onSelect("Tijera") }) { Text("✂️ Tijera") }
            }
        }
    }
}

@Composable
fun ResultScreen(p1Choice: String, p2Choice: String, onRestart: () -> Unit) {
    val result = winner(p1Choice, p2Choice)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize().padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Resultados:", style = MaterialTheme.typography.headlineSmall)
            Spacer(Modifier.height(16.dp))
            Text("Player 1 eligió: $p1Choice")
            Text("Player 2 eligió: $p2Choice")
            Spacer(Modifier.height(16.dp))
            Text("Ganador: $result", style = MaterialTheme.typography.headlineMedium)

            Spacer(Modifier.height(24.dp))
            Button(onClick = onRestart) { Text("Jugar de nuevo") }
        }
    }
}
