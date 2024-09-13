package com.example.hexagon.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.hexagon.data.model.Person
import com.example.hexagon.data.repository.PersonRepository

class MainViewModel(private val repository: PersonRepository) : ViewModel() {
    private val _persons = MutableLiveData<List<Person>>()
    val persons: LiveData<List<Person>> = _persons

    init {
        getActivePersons()
    }

    fun getActivePersons() {
        _persons.value = repository.getActivePersons()
    }

    fun addPerson(person: Person) {
        repository.addPerson(person)
        _persons.value = repository.getActivePersons()
    }
}
