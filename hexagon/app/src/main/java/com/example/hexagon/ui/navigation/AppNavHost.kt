package com.example.hexagon.ui.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.hexagon.ui.screens.AddPersonScreen
import com.example.hexagon.ui.screens.EditPersonScreen
import com.example.hexagon.ui.screens.ListScreen
import com.example.hexagon.ui.screens.InactiveListScreen
import com.example.hexagon.ui.main.MainViewModel



@Composable
fun AppNavHost(
    navController: NavHostController,
    viewModel: MainViewModel,
    onSelectPhoto: () -> Unit,
    photoPath: String?,
    resetPhotoPath: () -> Unit
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

        composable("addPerson") {
            resetPhotoPath()

            AddPersonScreen(
                navController = navController,
                onSave = { person ->
                    viewModel.addPerson(person)
                    viewModel.getActivePersons()
                    viewModel.getInactivePersons()
                    navController.navigate("list")
                },
                selectPhoto = { onSelectPhoto() },
                photoPath = photoPath
            )
        }

        composable("editPerson/{personId}") { backStackEntry ->
            val personId = backStackEntry.arguments?.getString("personId")?.toIntOrNull()
            val person = viewModel.activePersons.value?.find { it.id == personId }
                ?: viewModel.inactivePersons.value?.find { it.id == personId }

            person?.let {
                EditPersonScreen(
                    navController = navController,
                    person = it,
                    onSave = { updatedPerson ->
                        viewModel.updatePerson(updatedPerson)
                        navController.navigate("list")
                    },
                    selectPhoto = { onSelectPhoto() },
                    photoPath = photoPath
                )
            }
        }
    }
}
