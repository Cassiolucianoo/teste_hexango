package com.example.hexagon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImagePainter
import coil.compose.rememberAsyncImagePainter
import com.example.hexagon.R
import com.example.hexagon.data.model.Person







@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AddOrEditPersonScreen(
    navController: NavHostController,
    person: Person?,
    onSave: (Person) -> Unit,
    selectPhoto: (String) -> Unit, // Modifiquei para aceitar o caminho da nova foto
    photoPath: String?
) {
    var name by remember { mutableStateOf(person?.name ?: "") }
    var birthDate by remember { mutableStateOf(person?.birthDate ?: "") }
    var cpf by remember { mutableStateOf(person?.cpf ?: "") }
    var city by remember { mutableStateOf(person?.city ?: "") }
    var isActive by remember { mutableStateOf(person?.isActive ?: true) }

    // Se a pessoa for nula, use a imagem padrão
    var currentPhotoPath by remember { mutableStateOf(photoPath ?: person?.photo ?: "") }

    // Use uma imagem padrão ao adicionar uma nova pessoa
    val defaultPhoto = painterResource(id = R.drawable.baseline_person_24)

    // Atualiza a foto assim que o caminho for alterado
    val updatedPhotoPath = rememberUpdatedState(currentPhotoPath)

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Hexagon App") }
            )
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
                // Exibe a foto existente ou selecionada
                Image(
                    painter = rememberAsyncImagePainter(model = updatedPhotoPath.value),
                    contentDescription = "User Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(150.dp)
                )
            } else {
                // Mostra a imagem padrão ao adicionar uma nova pessoa
                Image(
                    painter = defaultPhoto,
                    contentDescription = "Default User Icon",
                    modifier = Modifier.size(150.dp)
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    // Aqui, a função selectPhoto é chamada e depois atualizamos a foto
                    selectPhoto("new_photo_path.jpg") // Exemplo: passe o caminho da nova foto
                    currentPhotoPath = "new_photo_path.jpg" // Atualiza o estado local imediatamente
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
                    val newPerson = Person(
                        id = person?.id ?: 0,
                        name = name,
                        birthDate = birthDate,
                        cpf = cpf,
                        city = city,
                        isActive = isActive,
                        photo = currentPhotoPath.ifEmpty { "" } // Caminho da nova foto ou vazio se não selecionado
                    )
                    onSave(newPerson)
                    navController.popBackStack() // Limpa a pilha de navegação
                    navController.navigate("list") // Navega para a tela de pessoas ativas
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }
}
