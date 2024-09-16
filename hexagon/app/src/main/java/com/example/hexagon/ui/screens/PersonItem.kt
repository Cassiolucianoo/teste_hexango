package com.example.hexagon.ui.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.hexagon.R
import com.example.hexagon.data.model.Person

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PersonItem(
    person: Person,
    onPrimaryActionClick: (Person) -> Unit,
    onSecondaryActionClick: (Person) -> Unit,
    secondaryActionLabel: String
) {
    Card(
        shape = RoundedCornerShape(8.dp),
        elevation = CardDefaults.cardElevation(4.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        ) {
            if (person.photo.isNotEmpty()) {
                Image(
                    painter = rememberAsyncImagePainter(model = person.photo),
                    contentDescription = "User Photo",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(100.dp)
                        .padding(end = 5.dp)
                )
            } else {
                Image(
                    painter = painterResource(id = R.drawable.baseline_person_24),
                    contentDescription = "Default User Icon",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp)
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(end = 16.dp)
            ) {
                Text(text = person.name, style = MaterialTheme.typography.titleMedium)
                Text(text = "Age: ${person.birthDate}", style = MaterialTheme.typography.bodyMedium)
            }

            Column {
                Button(onClick = { onPrimaryActionClick(person) }) {
                    Text("Editar")
                }
                Spacer(modifier = Modifier.height(8.dp))
                Button(onClick = { onSecondaryActionClick(person) }) {
                    Text(secondaryActionLabel)
                }
            }
        }
    }
}
