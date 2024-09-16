package com.example.hexagon.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hexagon.data.model.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavHostController,
    persons: List<Person>,
    onPersonClick: (Person) -> Unit,
    onDeactivateClick: (Person) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Hexagon App") }
            )
        },
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
