package com.example.hexagon.ui.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import com.example.hexagon.R
import com.example.hexagon.data.model.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ListScreen(
    navController: NavHostController,
    persons: List<Person>,
    onPersonClick: (Person) -> Unit,
    onDeactivateClick: (Person) -> Unit
) {

    var currentPersons by remember { mutableStateOf(persons) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Active Persons") }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { navController.navigate("addPerson") }) {
                Icon(painterResource(id = R.drawable.baseline_add_24), contentDescription = "Add Person")
            }
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
                        onPrimaryActionClick = onPersonClick,
                        onSecondaryActionClick = {
                            onDeactivateClick(it)
                            currentPersons = currentPersons.filter { p -> p.id != it.id }
                        },
                        secondaryActionLabel = "Desativar"
                    )
                }
            }
        }
    )
}
