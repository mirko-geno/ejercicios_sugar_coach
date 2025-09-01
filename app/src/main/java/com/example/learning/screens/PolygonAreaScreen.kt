package com.example.learning.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.unit.dp
import com.example.learning.backend.*

@Composable
fun PolygonAreaScreen(modifier: Modifier = Modifier) {
    var selectedType by remember { mutableStateOf(PolygonType.SQUARE) }
    var base by remember { mutableStateOf("") }
    var height by remember { mutableStateOf("") }
    var side by remember { mutableStateOf("") }
    var result by remember { mutableStateOf<Double?>(null) }
    var expanded by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = modifier
                .fillMaxSize()
                .padding(24.dp),
            verticalArrangement = Arrangement.Top
        ) {
            Text("Calculadora de Áreas", style = MaterialTheme.typography.headlineSmall)

            Spacer(Modifier.height(16.dp))

            //  Selector de polígono
            Box {
                Button(onClick = { expanded = true }) {
                    Text("Polígono: $selectedType")
                }
                DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
                    PolygonType.values().forEach { type ->
                        DropdownMenuItem(
                            text = { Text(type.name) },
                            onClick = {
                                selectedType = type
                                expanded = false
                                result = null
                            }
                        )
                    }
                }
            }

            Spacer(Modifier.height(16.dp))

            // Vista previa del polígono
            PolygonPreview(type = selectedType)

            Spacer(Modifier.height(16.dp))

            // Inputs según el polígono
            when (selectedType) {
                PolygonType.SQUARE -> {
                    OutlinedTextField(
                        value = side,
                        onValueChange = { side = it },
                        label = { Text("Lado") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }

                PolygonType.TRIANGLE, PolygonType.RECTANGLE -> {
                    OutlinedTextField(
                        value = base,
                        onValueChange = { base = it },
                        label = { Text("Base") },
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(Modifier.height(8.dp))
                    OutlinedTextField(
                        value = height,
                        onValueChange = { height = it },
                        label = { Text("Altura") },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            Spacer(Modifier.height(16.dp))

            // Botón calcular
            Button(
                onClick = {
                    result = when (selectedType) {
                        PolygonType.SQUARE -> {
                            val l = side.toDoubleOrNull() ?: 0.0
                            calcArea(Polygon(PolygonType.SQUARE, arrayOf(l)))
                        }

                        PolygonType.TRIANGLE -> {
                            val b = base.toDoubleOrNull() ?: 0.0
                            val h = height.toDoubleOrNull() ?: 0.0
                            calcArea(Polygon(PolygonType.TRIANGLE, arrayOf(b, h)))
                        }

                        PolygonType.RECTANGLE -> {
                            val b = base.toDoubleOrNull() ?: 0.0
                            val h = height.toDoubleOrNull() ?: 0.0
                            calcArea(Polygon(PolygonType.RECTANGLE, arrayOf(b, h)))
                        }
                    }
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Calcular Área")
            }

            Spacer(Modifier.height(16.dp))

            // Resultado
            result?.let {
                Text("Área = $it", style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
fun PolygonPreview(type: PolygonType) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp) // un poco más alto para el texto
            .padding(16.dp)
    ) {
        Canvas(modifier = Modifier.matchParentSize()) {
            val canvasWidth = size.width
            val canvasHeight = size.height
            val offset = 30f
            val line_width = 5f

            when (type) {
                PolygonType.SQUARE -> {
                    val sizeSq = canvasHeight / 2
                    val topLeft = Offset((canvasWidth / 2 - sizeSq / 2), canvasHeight / 4)

                    // Dibujar cuadrado
                    drawRect(
                        color = Color.Red,
                        topLeft = topLeft,
                        size = Size(sizeSq, sizeSq)
                    )

                    // Línea altura
                    drawLine(
                        color = Color.White,
                        start = Offset(topLeft.x + sizeSq + offset, topLeft.y),
                        end = Offset(topLeft.x + sizeSq + offset, topLeft.y + sizeSq),
                        strokeWidth = line_width
                    )
                }

                PolygonType.RECTANGLE -> {
                    val rectWidth = canvasWidth / 2
                    val rectHeight = canvasHeight / 3
                    val topLeft = Offset(canvasWidth / 4, canvasHeight / 3)

                    // Dibujar rectángulo
                    drawRect(
                        color = Color.Blue,
                        topLeft = topLeft,
                        size = Size(rectWidth, rectHeight)
                    )

                    // Línea base
                    drawLine(
                        color = Color.White,
                        start = Offset(topLeft.x, topLeft.y + rectHeight + offset),
                        end = Offset(topLeft.x + rectWidth, topLeft.y + rectHeight + offset),
                        strokeWidth = line_width
                    )

                    // Línea altura
                    drawLine(
                        color = Color.White,
                        start = Offset(topLeft.x + rectWidth + offset, topLeft.y),
                        end = Offset(topLeft.x + rectWidth + offset, topLeft.y + rectHeight),
                        strokeWidth = line_width
                    )
                }

                PolygonType.TRIANGLE -> {
                    val path = Path().apply {
                        moveTo(canvasWidth / 2, canvasHeight / 4) // punto superior
                        lineTo(canvasWidth / 4, 3 * canvasHeight / 4) // inferior izquierda
                        lineTo(3 * canvasWidth / 4, 3 * canvasHeight / 4) // inferior derecha
                        close()
                    }
                    drawPath(path = path, color = Color.Yellow)

                    // Línea base
                    drawLine(
                        color = Color.White,
                        start = Offset(canvasWidth / 4, 3 * canvasHeight / 4 + offset),
                        end = Offset(3 * canvasWidth / 4, 3 * canvasHeight / 4 + offset),
                        strokeWidth = line_width
                    )

                    // Línea altura
                    drawLine(
                        color = Color.White,
                        start = Offset(3 * canvasWidth / 4 + offset, 3 * canvasHeight / 4),
                        end = Offset(3 * canvasWidth / 4 + offset, canvasHeight / 4),
                        strokeWidth = line_width
                    )
                }
            }
        }

        // Etiquetas superpuestas
        when (type) {
            PolygonType.SQUARE -> {
                Text(
                    "Altura",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 50.dp)
                )
            }
            PolygonType.RECTANGLE -> {
                Text(
                    "base",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                )
                Text(
                    "altura",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                )
            }
            PolygonType.TRIANGLE -> {
                Text(
                    "base",
                    modifier = Modifier
                        .align(Alignment.BottomCenter)
                        .padding(bottom = 8.dp)
                )
                Text(
                    "altura",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp)
                )
            }
        }
    }
}
