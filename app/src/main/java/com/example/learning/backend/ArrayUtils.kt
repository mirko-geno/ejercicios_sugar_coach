package com.example.learning.backend

fun comparison(
    big_arr: Array<String>,
    small_arr: Array<String>,
    condition: Boolean,
): MutableList<String> {
    var result = mutableListOf<String>()
    when (condition) {
        true -> {
            for (element in big_arr) {
                if (element in small_arr) result.add(element)
            }
        }

        false -> {
            for (element in big_arr) {
                if (element !in small_arr) result.add(element)
            }
        }
    }
    return result
}


fun intersection(
    arr1: Array<String>,
    arr2: Array<String>,
    condition: Boolean,
): List<String> {
    val result =
        if (arr1.size >= arr2.size) comparison(arr1, arr2, condition)
        else comparison(arr2, arr1, condition)

    return result
}
