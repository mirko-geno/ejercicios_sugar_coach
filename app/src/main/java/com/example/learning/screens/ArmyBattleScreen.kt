package com.example.learning.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.learning.backend.*
import com.example.learning.R

@Composable
fun ArmyBattleScreen(modifier: Modifier = Modifier) {
    var goodArmy by remember { mutableStateOf(Army<KindRace>()) }
    var evilArmy by remember { mutableStateOf(Army<EvilRace>()) }
    var result by remember { mutableStateOf<String?>(null) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text("Batalla de Ejércitos", style = MaterialTheme.typography.headlineMedium)

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ArmyEditor(
                    title = "Ejército del Bien",
                    races = KindRace.entries.toList(),
                    onUpdate = { race, count -> goodArmy.add(race, count) }
                )
            }

            Row(
                modifier = modifier
                    .fillMaxWidth()
                    .weight(1f),
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ArmyEditor(
                    title = "Ejército del Mal",
                    races = EvilRace.entries.toList(),
                    onUpdate = { race, count -> evilArmy.add(race, count) }
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Botón al final
            Button(onClick = { result = battle(goodArmy, evilArmy) }) {
                Text("Armar ejércitos y pelear")
            }

            Spacer(modifier = Modifier.height(16.dp))

            result?.let {
                Text(it, style = MaterialTheme.typography.headlineSmall)
                Text("Fuerza del Bien: ${goodArmy.totalStrength()}")
                Text("Fuerza del Mal: ${evilArmy.totalStrength()}")
            }
        }
    }
}

@Composable
fun <T> ArmyEditor(title: String, races: List<T>, onUpdate: (T, Int) -> Unit) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(title, style = MaterialTheme.typography.titleMedium)
        Spacer(modifier = Modifier.height(8.dp))

        LazyColumn {
            items(races.size) { index ->
                val race = races[index]
                var count by remember { mutableStateOf(0) }
                val strength = when(race) {
                    is KindRace -> race.strength
                    is EvilRace -> race.strength
                    else -> 0
                }

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(4.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(
                            id = when (race) {
                                is KindRace -> when (race) {
                                    KindRace.Pelosos -> R.drawable.colapinto_8bits
                                    KindRace.Sureños -> R.drawable.colapinto_8bits
                                    KindRace.Enanos -> R.drawable.colapinto_8bits
                                    KindRace.Numenoreanos -> R.drawable.colapinto_8bits
                                    KindRace.Elfos -> R.drawable.colapinto_8bits
                                }
                                is EvilRace -> when (race) {
                                    EvilRace.Sureños -> R.drawable.colapinto_8bits
                                    EvilRace.Orcos -> R.drawable.colapinto_8bits
                                    EvilRace.Goblins -> R.drawable.colapinto_8bits
                                    EvilRace.Huargos -> R.drawable.colapinto_8bits
                                    EvilRace.Trolls -> R.drawable.colapinto_8bits
                                }
                                else -> R.drawable.colapinto_8bits
                            }
                        ),
                        contentDescription = race.toString(),
                        modifier = Modifier.size(48.dp)
                    )

                    Text(
                        race.toString() + " (${strength})",
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 8.dp)
                    )

                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Button(onClick = {
                            if (count > 0) {
                                count--
                                onUpdate(race, -1)
                            }
                        }) { Text("-") }

                        Text(count.toString(), modifier = Modifier.padding(8.dp))

                        Button(onClick = {
                            count++
                            onUpdate(race, 1)
                        }) { Text("+") }
                    }
                }
            }
        }
    }
}




