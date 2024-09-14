package com.example.hexagon.data.repository

import android.content.Context
import com.example.hexagon.data.database.PersonDatabase
import com.example.hexagon.data.model.Person

class PersonRepository(context: Context) {
    private val db = PersonDatabase(context)

    fun getActivePersons(): List<Person> {
        return db.getActivePersons()
    }

    fun getInactivePersons(): List<Person> {
        return db.getInactivePersons()
    }

    fun addPerson(person: Person) {
        db.addPerson(person)
    }
}
