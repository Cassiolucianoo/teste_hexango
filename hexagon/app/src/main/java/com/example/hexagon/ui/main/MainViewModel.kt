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
    }

    // Buscar pessoas ativas do repositório
    fun getActivePersons() {
        viewModelScope.launch {
            _activePersons.value = repository.getActivePersons() // Assíncrono
        }

    }

    // Buscar pessoas inativas do repositório
    fun getInactivePersons() {
        viewModelScope.launch {
            _inactivePersons.value = repository.getInactivePersons() // Assíncrono
        }

    }

    // Adicionar uma nova pessoa e atualizar a lista
    fun addPerson(person: Person) {
        viewModelScope.launch {
            repository.addPerson(person) // Assíncrono
            getActivePersons() // Atualiza a lista após a inserção
        }
    }

    // Atualizar uma pessoa e atualizar a lista
    fun updatePerson(person: Person) {
        viewModelScope.launch {
            repository.updatePerson(person) // Assíncrono
            getActivePersons() // Atualiza a lista após a atualização
        }
    }
}
