package com.example.hexagon.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.example.hexagon.data.model.Person
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class PersonRepository(context: Context) {
    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("person_prefs", Context.MODE_PRIVATE)
    private val gson = Gson()


    fun getActivePersons(): List<Person> {
        val json = sharedPreferences.getString("person_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<Person>>() {}.type
            gson.fromJson<List<Person>>(json, type).filter { it.isActive }
        } else {
            emptyList()
        }
    }

    fun addPerson(person: Person) {
        val currentList = getAllPersons().toMutableList()
        currentList.add(person)
        savePersonList(currentList)
    }

    private fun getAllPersons(): List<Person> {
        val json = sharedPreferences.getString("person_list", null)
        return if (json != null) {
            val type = object : TypeToken<List<Person>>() {}.type
            gson.fromJson(json, type)
        } else {
            emptyList()
        }
    }

    private fun savePersonList(personList: List<Person>) {
        val editor = sharedPreferences.edit()
        val json = gson.toJson(personList)
        editor.putString("person_list", json)
        editor.apply()
    }
}
