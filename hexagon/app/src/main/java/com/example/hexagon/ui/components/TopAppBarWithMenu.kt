package com.example.hexagon.ui.components

import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import com.example.hexagon.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopAppBarWithMenu(
    title: String,
    onActiveClick: () -> Unit,
    onInactiveClick: () -> Unit
) {
    var expanded by remember { mutableStateOf(false) }

    TopAppBar(
        title = { Text(text = title, color = Color.White) },
        colors = TopAppBarDefaults.smallTopAppBarColors(
            containerColor = Color(0xFF6200EA),
            titleContentColor = Color.White,
            actionIconContentColor = Color.White
        ),
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
