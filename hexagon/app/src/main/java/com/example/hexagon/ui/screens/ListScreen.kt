package com.example.hexagon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberImagePainter
import com.example.hexagon.R
import com.example.hexagon.data.model.Person
import com.example.hexagon.utils.DateUtils.calculateAge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavHostController,
    persons: List<Person>,
    onPersonClick: (Person) -> Unit,
    onDeactivateClick: (Person) -> Unit
) {
    Scaffold(
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addPerson") }) {
                Icon(Icons.Default.Add, contentDescription = "Add Person")
            }
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(persons) { person ->
                    PersonItem(person, onPersonClick, onDeactivateClick)
                }
            }
        }
    )
}

@Composable
fun PersonItem(
    person: Person,
    onItemClick: (Person) -> Unit,
    onDeactivateClick: (Person) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        if (person.photo.isNotEmpty()) {
            val painter = rememberImagePainter(data = person.photo)
            Image(
                painter = painter,
                contentDescription = "Person Photo",
                modifier = Modifier.size(40.dp)
            )
        } else {
            Icon(
                painter = painterResource(R.drawable.baseline_person_24),
                contentDescription = "Default Person Icon",
                modifier = Modifier.size(40.dp)
            )
        }
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = person.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Age: ${calculateAge(person.birthDate)}", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onItemClick(person) }) {
            Text("Edit")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onDeactivateClick(person) }) {
            Text("Deactivate")
        }
    }
}

