package com.example.hexagon.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.hexagon.data.model.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditPersonScreen(
    navController: NavHostController,
    person: Person?,
    onSave: (Person) -> Unit,
    selectPhoto: () -> Unit
) {
    var name by remember { mutableStateOf(person?.name ?: "") }
    var birthDate by remember { mutableStateOf(person?.birthDate ?: "") }
    var cpf by remember { mutableStateOf(person?.cpf ?: "") }
    var city by remember { mutableStateOf(person?.city ?: "") }
    var isActive by remember { mutableStateOf(person?.isActive ?: true) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {

        TextField(
            value = name,
            onValueChange = { name = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Nome") }
        )

        Spacer(modifier = Modifier.height(8.dp))


        TextField(
            value = birthDate,
            onValueChange = { birthDate = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Data de Nascimento") }
        )

        Spacer(modifier = Modifier.height(8.dp))


        TextField(
            value = cpf,
            onValueChange = { cpf = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("CPF") }
        )

        Spacer(modifier = Modifier.height(8.dp))


        TextField(
            value = city,
            onValueChange = { city = it },
            modifier = Modifier.fillMaxWidth(),
            label = { Text("Cidade") }
        )

        Spacer(modifier = Modifier.height(16.dp))


        Row {
            Text("Ativo")
            Switch(checked = isActive, onCheckedChange = { isActive = it })
        }

        Spacer(modifier = Modifier.height(16.dp))


        Button(
            onClick = {
                val newPerson = Person(
                    id = person?.id ?: 0,
                    name = name,
                    birthDate = birthDate,
                    cpf = cpf,
                    city = city,
                    isActive = isActive,
                    photo = person?.photo ?: ""
                )
                onSave(newPerson)
                navController.navigateUp()
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Salvar")
        }

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = selectPhoto,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Selecionar Foto")
        }
    }
}
