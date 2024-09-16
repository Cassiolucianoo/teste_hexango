package com.example.hexagon.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hexagon.data.model.Person
import com.example.hexagon.data.repository.PersonRepository
import kotlinx.coroutines.launch

class MainViewModel(private val repository: PersonRepository) : ViewModel() {

    private val _activePersons = MutableLiveData<List<Person>>()
    val activePersons: LiveData<List<Person>> = _activePersons

    private val _inactivePersons = MutableLiveData<List<Person>>()
    val inactivePersons: LiveData<List<Person>> = _inactivePersons

    init {
        getActivePersons()
        getInactivePersons()
    }

    fun getActivePersons() {
        viewModelScope.launch {
            _activePersons.value = repository.getActivePersons()
        }
    }

    fun getInactivePersons() {
        viewModelScope.launch {
            _inactivePersons.value = repository.getInactivePersons()
        }
    }

    fun updatePersonStatus(personId: Int, isActive: Boolean) {
        viewModelScope.launch {
            repository.updatePersonStatus(personId, isActive)
            getActivePersons()  // Atualiza lista de pessoas ativas
            getInactivePersons()  // Atualiza lista de pessoas inativas
        }
    }
    fun addPerson(person: Person) {
        viewModelScope.launch {
            repository.addPerson(person)
            getActivePersons()  // Atualiza a lista de pessoas ativas
        }
    }

    fun updatePerson(person: Person) {
        viewModelScope.launch {
            repository.updatePerson(person)
            getActivePersons()  // Atualiza a lista de pessoas ativas
        }
    }

}
