package com.example.hexagon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import com.example.hexagon.ui.navigation.AppNavHost
import com.example.hexagon.ui.main.MainViewModel
import com.example.hexagon.ui.main.MainViewModelFactory
import com.example.hexagon.ui.theme.HexagonTheme
import com.example.hexagon.data.repository.PersonRepository

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(PersonRepository(applicationContext)) // Adiciona o ViewModelFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HexagonTheme {
                Surface(color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }

    @OptIn(ExperimentalMaterial3Api::class)
    @Composable
    fun MainScreen() {
        val navController = rememberNavController()

        Scaffold(
            topBar = {
                TopAppBar(title = { Text(text = "Hexagon App") })
            },
            content = { paddingValues ->
                AppNavHost(
                    navController = navController,
                    viewModel = viewModel,
                    onSelectPhoto = { /* Lógica para selecionar foto */ }
                )
            }
        )
    }

    @Preview(showBackground = true)
    @Composable
    fun DefaultPreview() {
        HexagonTheme {
            MainScreen()
        }
    }
}
