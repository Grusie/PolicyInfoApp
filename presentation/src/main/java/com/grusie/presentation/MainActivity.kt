package com.grusie.presentation

import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.grusie.presentation.navigation.BottomNavItem
import com.grusie.presentation.navigation.BottomNavigationBar
import com.grusie.presentation.navigation.NavGraph
import com.grusie.presentation.navigation.Screen
import com.grusie.presentation.ui.theme.PolicyInfoTheme
import dagger.hilt.android.AndroidEntryPoint

@OptIn(ExperimentalMaterial3Api::class)
@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController

    @OptIn(ExperimentalAnimationApi::class)
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolicyInfoTheme {
                navController = rememberAnimatedNavController()
                val currentBackStackEntry = navController.currentBackStackEntryAsState().value
                val currentRoute = currentBackStackEntry?.destination?.route
                val isBottomNavVisible = currentRoute in listOf(
                    Screen.Home.route,
                    Screen.Scrap.route,
                    Screen.MyPage.route
                )

                Scaffold(
                    bottomBar = {
                        if (isBottomNavVisible) {
                            BottomNavigationBar(
                                items = listOf(
                                    BottomNavItem(
                                        name = "Home",
                                        route = Screen.Home.route,
                                        icon = Icons.Default.Home
                                    ),
                                    BottomNavItem(
                                        name = "Scrap",
                                        route = Screen.Scrap.route,
                                        icon = Icons.Default.Favorite
                                    ),
                                    BottomNavItem(
                                        name = "MyPage",
                                        route = Screen.MyPage.route,
                                        icon = Icons.Default.Person
                                    )
                                ),
                                navController = navController,
                                onItemClick = {
                                    navController.navigate(it.route)
                                },
                                modifier = Modifier.background(Color.Gray)
                            )
                        }
                    }
                ) {
                    NavGraph(navController = navController, modifier = Modifier.padding(it))
                }
            }
        }
    }
}