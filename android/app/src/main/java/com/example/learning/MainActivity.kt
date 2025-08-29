package com.example.learning

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.material3.*
import androidx.navigation.compose.rememberNavController
import com.example.learning.navigation.AppNavigation
import com.example.learning.ui.theme.LearningTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            LearningTheme {
                val navController = rememberNavController()
                AppNavigation(navController)
            }
        }
    }
}
