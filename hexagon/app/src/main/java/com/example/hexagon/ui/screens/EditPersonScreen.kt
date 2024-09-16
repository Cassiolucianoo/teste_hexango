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
    // Mantém os valores iniciais do usuário
    var name by remember { mutableStateOf(person.name) }
    var birthDate by remember { mutableStateOf(person.birthDate) }
    var cpf by remember { mutableStateOf(person.cpf) }
    var city by remember { mutableStateOf(person.city) }
    var isActive by remember { mutableStateOf(person.isActive) }

    // Mantém o caminho da foto atual (ou usa a foto passada via parâmetro)
    var currentPhotoPath by remember { mutableStateOf(person.photo) }
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
            // Campo de nome
            CustomTextField(
                value = name,
                onValueChange = { name = it },
                label = "Nome"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de data de nascimento
            CustomTextFieldWithMask(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = "Data de Nascimento"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de CPF
            CustomTextField(
                value = cpf,
                onValueChange = { cpf = it },
                label = "CPF"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Campo de cidade
            CustomTextField(
                value = city,
                onValueChange = { city = it },
                label = "Cidade"
            )

            Spacer(modifier = Modifier.height(16.dp))

            // Exibe a foto atual ou a imagem padrão
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

            // Botão para selecionar uma nova foto
            Button(
                onClick = {
                    // Chama a função para selecionar uma nova foto
                    selectPhoto()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Selecionar Foto")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Switch para marcar a pessoa como ativa/inativa
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ativo")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = isActive, onCheckedChange = { isActive = it })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão para salvar as alterações
            Button(
                onClick = {
                    val updatedPerson = Person(
                        id = person.id,
                        name = name,
                        birthDate = birthDate,
                        cpf = cpf,
                        city = city,
                        isActive = isActive,
                        photo = currentPhotoPath.ifEmpty { person.photo } // Se o caminho da foto estiver vazio, mantém a foto antiga
                    )
                    onSave(updatedPerson)
                    navController.navigate("list") // Volta para a lista após salvar
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }

    // Atualiza o caminho da foto em tempo real
    LaunchedEffect(photoPath) {
        photoPath?.let {
            currentPhotoPath = it // Atualiza o caminho da foto quando uma nova foto é selecionada
        }
    }

    // Atualiza os campos de edição quando a pessoa é alterada
    LaunchedEffect(person) {
        name = person.name
        birthDate = person.birthDate
        cpf = person.cpf
        city = person.city
        isActive = person.isActive
        currentPhotoPath = person.photo // Reseta a foto quando a pessoa muda
    }
}
