package com.example.hexagon.ui.components


import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.example.hexagon.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithMenu(onActiveClick: () -> Unit, onInactiveClick: () -> Unit) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = "Hexagon App") },
        actions = {
            IconButton(onClick = { expanded = true }) {
                Icon(
                    painter = painterResource(id = R.drawable.baseline_menu_24),
                    contentDescription = "Menu"
                )
            }
            DropdownMenu(
                expanded = expanded,
                onDismissRequest = { expanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Lista Ativos") },
                    onClick = {
                        expanded = false
                        onActiveClick()
                    }
                )
                DropdownMenuItem(
                    text = { Text("Lista Inativos") },
                    onClick = {
                        expanded = false
                        onInactiveClick()
                    }
                )
            }
        }
    )
}

@Preview(showBackground = true)
@Composable
fun PreviewTopAppBarWithMenu() {
    TopAppBarWithMenu(
        onActiveClick = { /* Handle Active List Click */ },
        onInactiveClick = { /* Handle Inactive List Click */ }
    )
}
