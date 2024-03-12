package com.alican.livescores.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.alican.livescores.ui.match_detail.MatchDetailScreen
import com.alican.livescores.ui.matches.MatchesScreen
import com.alican.livescores.utils.ScreenRoutes
import com.alican.livescores.utils.theme.LiveScoresTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            LiveScoresTheme {
                val navController = rememberNavController()

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(MaterialTheme.colorScheme.background)
                ) { paddingValues ->
                    NavHost(
                        navController = navController,
                        modifier = Modifier.padding(paddingValues),
                        startDestination = ScreenRoutes.MATCHES_SCREEN
                    ) {
                        composable(
                            route = ScreenRoutes.MATCHES_SCREEN
                        ) {
                            MatchesScreen(
                                openMatchDetail = { route ->
                                    navController.navigate(route)
                                }
                            )
                        }
                        composable(
                            route = ScreenRoutes.MATCH_DETAIL_SCREEN,
                            arguments = listOf(
                                navArgument("id") { type = NavType.StringType }
                            )
                        ) {
                            MatchDetailScreen()
                        }
                    }

                }
            }
        }
    }
}