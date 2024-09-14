package com.example.hexagon.data.repository

import android.content.ContentValues
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

    fun updatePerson(person: Person) {
        val values = ContentValues().apply {
            put("name", person.name)
            put("birthDate", person.birthDate)
            put("cpf", person.cpf)
            put("city", person.city)
            put("photo", person.photo)
            put("isActive", if (person.isActive) 1 else 0)
        }
        db.writableDatabase.update("persons", values, "id = ?", arrayOf(person.id.toString()))
        db.close()
    }
}
