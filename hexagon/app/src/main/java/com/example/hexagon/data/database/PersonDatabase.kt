package com.example.hexagon.data.database

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.hexagon.data.model.Person

class PersonDatabase(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_PERSON_TABLE = ("CREATE TABLE $TABLE_PERSONS ("
                + "$KEY_ID INTEGER PRIMARY KEY,"
                + "$KEY_NAME TEXT,"
                + "$KEY_BIRTH_DATE TEXT,"
                + "$KEY_CPF TEXT,"
                + "$KEY_CITY TEXT,"
                + "$KEY_PHOTO TEXT,"
                + "$KEY_IS_ACTIVE INTEGER)")
        db.execSQL(CREATE_PERSON_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_PERSONS")
        onCreate(db)
    }

    fun addPerson(person: Person) {
        val db = this.writableDatabase
        val values = ContentValues().apply {
            put(KEY_NAME, person.name)
            put(KEY_BIRTH_DATE, person.birthDate)
            put(KEY_CPF, person.cpf)
            put(KEY_CITY, person.city)
            put(KEY_PHOTO, person.photo)
            put(KEY_IS_ACTIVE, if (person.isActive) 1 else 0)
        }
        db.insert(TABLE_PERSONS, null, values)
        db.close()
    }

    fun getActivePersons(): List<Person> {
        val personList = mutableListOf<Person>()
        val selectQuery = "SELECT * FROM $TABLE_PERSONS WHERE $KEY_IS_ACTIVE = 1"
        val db = this.readableDatabase
        val cursor = db.rawQuery(selectQuery, null)
        if (cursor.moveToFirst()) {
            do {
                val person = Person(
                    name = cursor.getString(cursor.getColumnIndexOrThrow(KEY_NAME)),
                    birthDate = cursor.getString(cursor.getColumnIndexOrThrow(KEY_BIRTH_DATE)),
                    cpf = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CPF)),
                    city = cursor.getString(cursor.getColumnIndexOrThrow(KEY_CITY)),
                    photo = cursor.getString(cursor.getColumnIndexOrThrow(KEY_PHOTO)),
                    isActive = cursor.getInt(cursor.getColumnIndexOrThrow(KEY_IS_ACTIVE)) == 1
                )
                personList.add(person)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return personList
    }

    companion object {
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "personsManager"
        private const val TABLE_PERSONS = "persons"
        private const val KEY_ID = "id"
        private const val KEY_NAME = "name"
        private const val KEY_BIRTH_DATE = "birthDate"
        private const val KEY_CPF = "cpf"
        private const val KEY_CITY = "city"
        private const val KEY_PHOTO = "photo"
        private const val KEY_IS_ACTIVE = "isActive"
    }
}
