package com.example.hexagon.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import com.example.hexagon.data.model.Person

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
                title = { Text(text = "Inactive Persons") }
            )
        },
        content = { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
            ) {
                items(currentPersons) { person ->
                    PersonItem(
                        person = person,
                        onPrimaryActionClick = onEditClick,
                        onSecondaryActionClick = {
                            onReactivateClick(it)
                            currentPersons = currentPersons.filter { p -> p.id != it.id }
                        },
                        secondaryActionLabel = "Reativar"
                    )
                }
            }
        }
    )
}
