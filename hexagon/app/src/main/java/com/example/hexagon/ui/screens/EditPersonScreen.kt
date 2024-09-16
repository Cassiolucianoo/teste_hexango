package com.example.hexagon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.rememberAsyncImagePainter
import com.example.hexagon.R
import com.example.hexagon.data.model.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditPersonScreen(
    navController: NavHostController,
    person: Person,
    onSave: (Person) -> Unit,
    selectPhoto: () -> Unit,
    photoPath: String?
) {
    var name by remember { mutableStateOf(person.name) }
    var birthDate by remember { mutableStateOf(person.birthDate) }
    var cpf by remember { mutableStateOf(person.cpf) }
    var city by remember { mutableStateOf(person.city) }
    var isActive by remember { mutableStateOf(person.isActive) }


    var currentPhotoPath by remember { mutableStateOf(photoPath ?: person.photo) }
    val defaultPhoto = painterResource(id = R.drawable.baseline_person_24)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Edit Person") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            CustomTextField(
                value = name,
                onValueChange = { name = it },
                label = "Nome"
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextFieldWithMask(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = "Data de Nascimento"
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                value = cpf,
                onValueChange = { cpf = it },
                label = "CPF"
            )

            Spacer(modifier = Modifier.height(8.dp))

            CustomTextField(
                value = city,
                onValueChange = { city = it },
                label = "Cidade"
            )

            Spacer(modifier = Modifier.height(16.dp))

            if (currentPhotoPath.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = currentPhotoPath),
                    contentDescription = "User Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(150.dp)
                )
            } else {
                Image(
                    painter = defaultPhoto,
                    contentDescription = "Default User Icon",
                    modifier = Modifier.size(150.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    selectPhoto()
                    currentPhotoPath = "new_photo_path.jpg"
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Selecionar Foto")
            }

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ativo")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = isActive, onCheckedChange = { isActive = it })
            }

            Spacer(modifier = Modifier.height(16.dp))

            Button(
                onClick = {
                    val updatedPerson = Person(
                        id = person.id,
                        name = name,
                        birthDate = birthDate,
                        cpf = cpf,
                        city = city,
                        isActive = isActive,
                        photo = currentPhotoPath.ifEmpty { person.photo }
                    )
                    onSave(updatedPerson)
                    navController.navigate("list")
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}
