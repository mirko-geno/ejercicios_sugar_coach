package com.example.learning.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.learning.ui.theme.LearningTheme

@Composable
fun FizzbuzzScreen(modifier: Modifier = Modifier) {
    var sliderValue by remember { mutableStateOf(1f) } //remember asegura que este objeto de estado se mantenga a lo largo de las recomposiciones

    val n = sliderValue.toInt()
    val result = when {
        n % 3 == 0 && n % 5 == 0 -> "fizzbuzz"
        n % 3 == 0 -> "fizz"
        n % 5 == 0 -> "buzz"
        else -> n.toString()
    }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Número: $n",
                style = MaterialTheme.typography.headlineSmall,
                color = MaterialTheme.colorScheme.onBackground
            )
            Spacer(modifier = Modifier.height(16.dp))
            Text(
                text = result,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.primary
            )
            Spacer(modifier = Modifier.height(32.dp))

            Slider(
                value = sliderValue,
                onValueChange = { sliderValue = it }, // When value changes, fizzbuzz is calculated
                valueRange = 1f..100f,
                steps = 100,
                colors = SliderDefaults.colors(
                    thumbColor = MaterialTheme.colorScheme.primary,
                    activeTrackColor = MaterialTheme.colorScheme.secondary,
                    inactiveTrackColor = MaterialTheme.colorScheme.surface
                )
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun FizzbuzzPreview() {
    LearningTheme {
        FizzbuzzScreen()
    }
}
