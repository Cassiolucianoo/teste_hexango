package com.example.hexagon.ui.screens

import androidx.compose.foundation.layout.*

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hexagon.data.model.Person
import com.example.hexagon.utils.DateUtils.calculateAge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InactiveListScreen(
    navController: NavHostController,
    persons: List<Person>,
    onReactivateClick: (Person) -> Unit
) {
    Scaffold(
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(persons) { person ->
                    InactivePersonItem(person, onReactivateClick)
                }
            }
        }
    )
}

@Composable
fun InactivePersonItem(person: Person, onReactivateClick: (Person) -> Unit) {
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
        Button(onClick = { onReactivateClick(person) }) {
            Text("Reactivate")
        }
    }
}
