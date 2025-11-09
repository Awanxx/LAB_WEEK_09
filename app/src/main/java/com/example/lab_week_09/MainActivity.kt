package com.example.lab_week_09

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.lab_week_09.ui.theme.LAB_WEEK_09Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LAB_WEEK_09Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberNavController()
                    App(navController)
                }
            }
        }
    }
}

@Composable
fun App(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(navController)
        }
        composable("result/{listData}") { backStackEntry ->
            val listData = backStackEntry.arguments?.getString("listData").orEmpty()
            ResultContent(listData)
        }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    var inputText by remember { mutableStateOf("") }
    var names by remember { mutableStateOf(listOf("Tanu", "Tina", "Tono")) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Enter a name", style = MaterialTheme.typography.titleLarge)

        TextField(
            value = inputText,
            onValueChange = { inputText = it },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Button(onClick = {
                if (inputText.isNotBlank()) {
                    names = names + inputText
                    inputText = ""
                }
            }) {
                Text("Submit")
            }

            Button(onClick = {
                if (inputText.isNotBlank()) {
                    names = names + inputText
                }
                val namesString = names.joinToString(", ") // Convert list to a single string
                navController.navigate("result/$namesString")
            }) {
                Text("Finish")
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        LazyColumn {
            items(names) { name ->
                Text(text = name, modifier = Modifier.padding(vertical = 4.dp))
            }
        }
    }
}

@Composable
fun ResultContent(listData: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(text = "Submitted Names", style = MaterialTheme.typography.titleLarge)
        Text(text = listData, style = MaterialTheme.typography.bodyLarge)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    LAB_WEEK_09Theme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}