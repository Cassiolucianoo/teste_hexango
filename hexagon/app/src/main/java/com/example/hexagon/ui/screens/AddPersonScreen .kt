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
fun AddPersonScreen(
    navController: NavHostController,
    onSave: (Person) -> Unit,
    selectPhoto: (String) -> Unit,  // Função para selecionar uma foto e passar o caminho
    photoPath: String? // Parâmetro opcional para o caminho da foto selecionada
) {
    // Variáveis de estado para os campos
    var name by remember { mutableStateOf("") }
    var birthDate by remember { mutableStateOf("") }
    var cpf by remember { mutableStateOf("") }
    var city by remember { mutableStateOf("") }
    var isActive by remember { mutableStateOf(true) }

    // Variável de estado para a imagem, sempre iniciando com a imagem padrão
    var currentPhotoPath by remember { mutableStateOf("") }
    val defaultPhoto = painterResource(id = R.drawable.baseline_person_24)

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Add Person") })
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(paddingValues)
                .padding(16.dp)
                .verticalScroll(rememberScrollState())
        ) {
            // Nome
            CustomTextField(
                value = name,
                onValueChange = { name = it },
                label = "Nome"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Data de Nascimento
            CustomTextFieldWithMask(
                value = birthDate,
                onValueChange = { birthDate = it },
                label = "Data de Nascimento"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // CPF
            CustomTextField(
                value = cpf,
                onValueChange = { cpf = it },
                label = "CPF"
            )

            Spacer(modifier = Modifier.height(8.dp))

            // Cidade
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

            // Botão de Seleção de Foto
            Button(
                onClick = {
                    // Função para selecionar uma nova foto e atualizar o estado
                    selectPhoto("new_photo_path.jpg")  // Exemplo de caminho fictício, atualize isso ao selecionar a foto
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Selecionar Foto")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Switch Ativo/Inativo
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Ativo")
                Spacer(modifier = Modifier.width(8.dp))
                Switch(checked = isActive, onCheckedChange = { isActive = it })
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Botão de Salvar
            Button(
                onClick = {
                    val newPerson = Person(
                        id = 0,
                        name = name,
                        birthDate = birthDate,
                        cpf = cpf,
                        city = city,
                        isActive = isActive,
                        photo = currentPhotoPath.ifEmpty { "" } // Salva o caminho da foto, ou vazio se não houver
                    )
                    onSave(newPerson)
                    navController.navigate("list") // Navegar para a lista após salvar
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Salvar")
            }
        }
    }


    LaunchedEffect(Unit) {
        currentPhotoPath = ""
        name = ""
        birthDate = ""
        cpf = ""
        city = ""
        isActive = true
    }


    LaunchedEffect(photoPath) {
        photoPath?.let {
            currentPhotoPath = it
        }
    }
}
