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
    onSelectPhoto: () -> Unit,
    photoPath: String?
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
                    // Aqui desativa e atualiza a lista
                    viewModel.updatePersonStatus(person.id, false)
                }
            )
        }

        composable("inactiveList") {
            InactiveListScreen(
                navController = navController,
                persons = viewModel.inactivePersons.value ?: emptyList(),
                onReactivateClick = { person ->
                    // Aqui reativa e atualiza a lista
                    viewModel.updatePersonStatus(person.id, true)
                },
                onEditClick = { person ->
                    navController.navigate("editPerson/${person.id}")
                }
            )
        }

        // Defina a rota "addPerson"
        composable("addPerson") {
            AddOrEditPersonScreen(
                navController = navController,
                person = null,
                onSave = { person ->
                    // Adiciona pessoa ao banco de dados
                    viewModel.addPerson(person)
                    // Navega de volta à lista
                    navController.navigateUp()
                },
                selectPhoto = onSelectPhoto,
                photoPath = photoPath
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
                    onSave = { updatedPerson ->
                        // Atualiza a pessoa no banco de dados
                        viewModel.updatePerson(updatedPerson)
                        // Navega de volta à lista
                        navController.navigateUp()
                    },
                    selectPhoto = onSelectPhoto,
                    photoPath = photoPath
                )
            }
        }
    }
}
