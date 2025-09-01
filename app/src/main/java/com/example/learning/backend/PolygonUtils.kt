package com.example.learning.backend

enum class PolygonType {
    SQUARE,
    TRIANGLE,
    RECTANGLE
}

class Polygon(val type: PolygonType, val params: Array<Double>)

fun calcArea(polygon: Polygon): Double {
    return when (polygon.type) {
        PolygonType.SQUARE -> polygon.params[0] * polygon.params[0]
        PolygonType.TRIANGLE -> (polygon.params[0] * polygon.params[1]) / 2
        PolygonType.RECTANGLE -> polygon.params[0] * polygon.params[1]
    }
}
