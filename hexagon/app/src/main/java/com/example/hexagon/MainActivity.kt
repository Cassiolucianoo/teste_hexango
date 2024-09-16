package com.example.hexagon

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.rememberNavController
import com.example.hexagon.ui.navigation.AppNavHost
import com.example.hexagon.ui.main.MainViewModel
import com.example.hexagon.ui.main.MainViewModelFactory
import com.example.hexagon.ui.theme.HexagonTheme
import com.example.hexagon.data.repository.PersonRepository
import com.example.hexagon.ui.components.TopAppBarWithMenu
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModelFactory(PersonRepository(applicationContext))
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
        val context = LocalContext.current

        var selectedImagePath by remember { mutableStateOf<String?>(null) }


        val selectImageLauncher = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) { uri: Uri? ->
            uri?.let {
                val imagePath = saveImageToFile(context, it)
                selectedImagePath = imagePath
            }
        }

        Scaffold(
            topBar = {
                TopAppBarWithMenu(
                    onActiveClick = { navController.navigate("list") },
                    onInactiveClick = { navController.navigate("inactiveList") }
                )
            },
            content = { paddingValues ->
                AppNavHost(
                    navController = navController,
                    viewModel = viewModel,
                    onSelectPhoto = { selectImageLauncher.launch("image/*") },
                    photoPath = selectedImagePath
                )
            }
        )
    }


    fun saveImageToFile(context: Context, imageUri: Uri): String {
        val inputStream: InputStream? = context.contentResolver.openInputStream(imageUri)
        val file = File(context.filesDir, "image_${System.currentTimeMillis()}.png")
        val outputStream = FileOutputStream(file)
        inputStream?.copyTo(outputStream)
        outputStream.close()
        inputStream?.close()
        return file.absolutePath
    }
}
