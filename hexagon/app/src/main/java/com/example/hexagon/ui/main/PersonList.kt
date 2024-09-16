package com.example.hexagon.ui.main

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hexagon.data.model.Person
import com.example.hexagon.utils.DateUtils.calculateAge

@Composable
fun PersonList(
    persons: List<Person>,
    onPersonClick: (Person) -> Unit,
    onDeactivateClick: (Person) -> Unit
) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(persons) { person ->
            PersonItem(person = person, onPersonClick = onPersonClick, onDeactivateClick = onDeactivateClick)
        }
    }
}

@Composable
fun PersonItem(
    person: Person,
    onPersonClick: (Person) -> Unit,
    onDeactivateClick: (Person) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            Text(text = person.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Age: ${calculateAge(person.birthDate)}", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onPersonClick(person) }) {
            Text("Edit")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onDeactivateClick(person) }) {
            Text(if (person.isActive) "Deactivate" else "Reactivate")
        }
    }
}
