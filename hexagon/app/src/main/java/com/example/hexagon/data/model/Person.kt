package com.example.hexagon.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Person(
    val id: Int,
    val name: String,
    val birthDate: String,
    val cpf: String,
    val city: String,
    val isActive: Boolean,
    val photo: String = ""
) : Parcelable
