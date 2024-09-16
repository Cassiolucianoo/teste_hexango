package com.example.hexagon.utils

fun applyCpfMask(input: String): String {
    val str = input.replace(Regex("[^\\d]"), "") // Remove qualquer coisa que não seja dígito
    return when {
        str.length > 11 -> str.substring(0, 11)
        str.length > 9 -> "${str.substring(0, 3)}.${str.substring(3, 6)}.${str.substring(6, 9)}-${str.substring(9)}"
        str.length > 6 -> "${str.substring(0, 3)}.${str.substring(3, 6)}.${str.substring(6)}"
        str.length > 3 -> "${str.substring(0, 3)}.${str.substring(3)}"
        else -> str
    }
}
