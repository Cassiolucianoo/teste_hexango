package com.example.hexagon.utils

fun applyDateMask(input: String): String {
    val str = input.replace(Regex("[^\\d]"), "")
    return when {
        str.length > 8 -> str.substring(0, 8)
        str.length > 4 -> "${str.substring(0, 2)}/${str.substring(2, 4)}/${str.substring(4)}"
        str.length > 2 -> "${str.substring(0, 2)}/${str.substring(2)}"
        else -> str
    }
}
