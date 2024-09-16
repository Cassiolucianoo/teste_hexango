package com.example.hexagon.ui.screens

import androidx.compose.foundation.layout.*


import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hexagon.data.model.Person

@Composable
fun PersonEditScreen(navController: NavHostController, person: Person, onSaveClick: (Person) -> Unit) {
    var name by remember { mutableStateOf(person.name) }
    var birthDate by remember { mutableStateOf(person.birthDate) }
    var cpf by remember { mutableStateOf(person.cpf) }
    var city by remember { mutableStateOf(person.city) }
    var isActive by remember { mutableStateOf(person.isActive) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text("Edit Person", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        BasicTextField(value = name, onValueChange = { name = it }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(value = birthDate, onValueChange = { birthDate = it }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(value = cpf, onValueChange = { cpf = it }, modifier = Modifier.fillMaxWidth())
        Spacer(modifier = Modifier.height(8.dp))
        BasicTextField(value = city, onValueChange = { city = it }, modifier = Modifier.fillMaxWidth())

        Spacer(modifier = Modifier.height(16.dp))

        Row {
            Text("Active")
            Switch(checked = isActive, onCheckedChange = { isActive = it })
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = {
                val updatedPerson = person.copy(name = name, birthDate = birthDate, cpf = cpf, city = city, isActive = isActive)
                onSaveClick(updatedPerson)
                navController.navigate("list")
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save Changes")
        }
    }
}
