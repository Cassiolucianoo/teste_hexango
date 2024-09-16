package com.example.hexagon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.hexagon.R
import com.example.hexagon.data.model.Person
import com.example.hexagon.utils.DateUtils.calculateAge

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InactiveListScreen(
    navController: NavHostController,
    persons: List<Person>,
    onReactivateClick: (Person) -> Unit,
    onEditClick: (Person) -> Unit
) {

    var currentPersons by remember { mutableStateOf(persons) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Inactive Persons") }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(currentPersons) { person ->
                    InactivePersonItem(
                        person = person,
                        onReactivateClick = {
                            onReactivateClick(it)

                            currentPersons = currentPersons.filter { p -> p.id != it.id }
                        },
                        onEditClick = onEditClick
                    )
                }
            }
        }
    )
}

@Composable
fun InactivePersonItem(
    person: Person,
    onReactivateClick: (Person) -> Unit,
    onEditClick: (Person) -> Unit
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {

            if (person.photo.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = person.photo),
                    contentDescription = "User Photo",
                    modifier = Modifier
                        .size(60.dp)
                        .padding(end = 16.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "Default User Icon",
                    modifier = Modifier.size(60.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(text = person.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Age: ${calculateAge(person.birthDate)}", style = MaterialTheme.typography.bodyMedium)
            }

            Column {
                Button(onClick = { onEditClick(person) }) {
                    Text("Edit")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { onReactivateClick(person) }) {
                    Text("Reactivate")
                }
            }
        }
    }
}
