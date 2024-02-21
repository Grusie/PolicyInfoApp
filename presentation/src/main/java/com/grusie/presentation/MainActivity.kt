package com.grusie.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.grusie.presentation.navigation.NavGraph
import com.grusie.presentation.ui.theme.PolicyInfoTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var navController: NavHostController
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PolicyInfoTheme {
                navController = rememberNavController()
                NavGraph(navController = navController)
            }
        }
    }
}