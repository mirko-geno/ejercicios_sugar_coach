package com.example.learning.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.learning.screens.FizzbuzzScreen
import com.example.learning.screens.F1GameScreen
import com.example.learning.screens.WelcomeScreen
import com.example.learning.screens.IntersectionScreen
import com.example.learning.screens.PolygonAreaScreen

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "welcome") {
        composable("welcome") { WelcomeScreen(navController) }
        composable("fizzbuzz") { FizzbuzzScreen() }
        composable("f1game") { F1GameScreen() }
        composable("intersection") { IntersectionScreen() }
        composable("polygon") { PolygonAreaScreen() }
    }
}
