// F1GameScreen.kt
package com.example.learning.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.delay
import com.example.learning.R
import com.example.learning.backend.F1Game
import com.example.learning.backend.Track

@Composable
fun F1GameScreen(modifier: Modifier = Modifier) {
    val game = remember { F1Game() }
    var showHelp by remember { mutableStateOf(false) }

    // Temporizador
    LaunchedEffect(game.track, game.gameOver, game.gameWon) {
        game.nextTurn()
        while (game.timeLeft > 0 && !game.gameOver && !game.gameWon) {
            delay(1000)
            game.tickTime()
        }
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top
            ) {
                // Imagen arriba
                Image(
                    painter = painterResource(id = R.drawable.colapinto_8bits),
                    contentDescription = "Colapinto",
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(400.dp)
                )

                Spacer(Modifier.height(16.dp))

                Text(
                    text = game.message,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(Modifier.height(32.dp))

                if (!game.gameOver && !game.gameWon) {
                    Text(
                        text = when (game.track) {
                            Track.STRAIGHT -> "⬆️"
                            Track.CURVE -> "↪️"
                        },
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Spacer(Modifier.height(16.dp))
                    Text(
                        "Tiempo restante: ${game.timeLeft} s",
                        color = MaterialTheme.colorScheme.secondary
                    )
                    Spacer(Modifier.height(32.dp))

                    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
                        Button(
                            onClick = { game.chooseAction(Track.STRAIGHT) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) { Text("Acelerar") }

                        Button(
                            onClick = { game.chooseAction(Track.CURVE) },
                            colors = ButtonDefaults.buttonColors(
                                containerColor = MaterialTheme.colorScheme.primary,
                                contentColor = MaterialTheme.colorScheme.onPrimary
                            )
                        ) { Text("Doblar") }
                    }
                    Spacer(Modifier.height(16.dp))
                    Text("Puntos: ${game.score}", color = MaterialTheme.colorScheme.onBackground)
                } else {
                    Button(
                        onClick = { game.reset() },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary,
                            contentColor = MaterialTheme.colorScheme.onPrimary
                        )
                    ) { Text("Reiniciar") }
                }
            }

            // Botón de ayuda abajo a la derecha
            Button(
                onClick = { showHelp = true },
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp)
            ) {
                Text("Ayuda")
            }

            // Diálogo de instrucciones
            if (showHelp) {
                AlertDialog(
                    onDismissRequest = { showHelp = false },
                    confirmButton = {
                        TextButton(onClick = { showHelp = false }) {
                            Text("Entendido")
                        }
                    },
                    title = { Text("Cómo jugar") },
                    text = {
                        Text(
                            "Colapinto necesita tu ayuda para ganar la carrera.\n\n" +
                                    "⬆️ Acelerar: cuando la pista sigue recta.\n" +
                                    "↪️ Doblar: cuando aparece una curva.\n\n" +
                                    "¡Responde antes de que el tiempo se acabe!"
                        )
                    }
                )
            }
        }
    }
}
