package com.example.hexagon.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hexagon.data.model.Person

@Composable
fun PersonItem(
    person: Person,
    onItemClick: (Person) -> Unit,
    onDeactivateClick: (Person) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Column {
            Text(text = person.name, style = MaterialTheme.typography.titleMedium)
            Text(text = "Age: ${person.birthDate}", style = MaterialTheme.typography.bodyMedium)
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onItemClick(person) }) {
            Text("Edit")
        }
        Spacer(modifier = Modifier.width(8.dp))
        Button(onClick = { onDeactivateClick(person) }) {
            Text("Deactivate")
        }
    }
}
