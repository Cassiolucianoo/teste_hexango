package com.example.hexagon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hexagon.ui.screens.AddOrEditPersonScreen
import com.example.hexagon.ui.screens.ListScreen
import com.example.hexagon.ui.screens.InactiveListScreen
import com.example.hexagon.ui.main.MainViewModel

@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    onSelectPhoto: () -> Unit
) {
    NavHost(navController = navController, startDestination = "list") {
        composable("list") {
            ListScreen(
                navController = navController,
                persons = viewModel.activePersons.value ?: emptyList(),
                onPersonClick = { person ->
                    navController.navigate("editPerson/${person.id}")
                },
                onDeactivateClick = { person ->
                    viewModel.updatePersonStatus(person.id, false)
                }
            )
        }

        composable("addPerson") {
            AddOrEditPersonScreen(
                navController = navController,
                person = null,
                onSave = { person -> viewModel.addPerson(person) },
                selectPhoto = onSelectPhoto
            )
        }

        composable("inactiveList") {
            InactiveListScreen(
                navController = navController,
                persons = viewModel.inactivePersons.value ?: emptyList(),
                onReactivateClick = { person ->
                    viewModel.updatePersonStatus(person.id, true)
                },
                onEditClick = { person ->
                    navController.navigate("editPerson/${person.id}")
                }
            )
        }

        composable("editPerson/{personId}") { backStackEntry ->
            val personId = backStackEntry.arguments?.getString("personId")?.toIntOrNull()
            val person = viewModel.activePersons.value?.find { it.id == personId }
                ?: viewModel.inactivePersons.value?.find { it.id == personId }

            person?.let {
                AddOrEditPersonScreen(
                    navController = navController,
                    person = it,
                    onSave = { updatedPerson -> viewModel.updatePerson(updatedPerson) },
                    selectPhoto = onSelectPhoto
                )
            }
        }
    }
}
