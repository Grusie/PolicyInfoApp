package com.grusie.presentation

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.OnBackPressedCallback
import androidx.activity.compose.LocalOnBackPressedDispatcherOwner
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
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.grusie.presentation.components.SingleAlertDialog
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
                var exitAppDialogVisibility by remember { mutableStateOf(false) }

                val backDispatcher =
                    LocalOnBackPressedDispatcherOwner.current?.onBackPressedDispatcher

                if (isBottomNavVisible) {
                    DisposableEffect(backDispatcher) {
                        val callback = object : OnBackPressedCallback(true) {
                            override fun handleOnBackPressed() {
                                exitAppDialogVisibility = true
                            }
                        }

                        backDispatcher?.addCallback(callback)
                        onDispose {
                            callback.remove()
                        }
                    }
                }

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

                            if(exitAppDialogVisibility) {
                                SingleAlertDialog(
                                    confirm = true,
                                    title = stringResource(id = R.string.str_exit_app_title),
                                    content = stringResource(id = R.string.str_exit_app_content),
                                    confirmCallBack = { finish() },
                                    onDismissRequest = { exitAppDialogVisibility = false}
                                )
                            }
                        }
                    }
                ) {
                    NavGraph(navController = navController, modifier = Modifier.padding(it))
                }
            }
        }
    }
}