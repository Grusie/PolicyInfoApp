package com.grusie.presentation.navigation

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.grusie.presentation.screen.home.HomeScreen
import com.grusie.presentation.screen.mypage.MyPageScreen
import com.grusie.presentation.screen.policydetail.PolicyDetailScreen
import com.grusie.presentation.screen.scrap.ScrapScreen
import com.grusie.presentation.screen.search.SearchScreen
import com.grusie.presentation.util.Constant

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NavGraph(navController: NavHostController, modifier: Modifier = Modifier) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screen.Home.route,
        exitTransition = { ExitTransition.None },
        enterTransition = { EnterTransition.None },
        popEnterTransition = { EnterTransition.None },
        popExitTransition = { ExitTransition.None },
    ) {
        composable(route = Screen.Home.route) {
            HomeScreen(navController = navController, modifier = modifier)
        }
        composable(
            route = Screen.PolicyDetail.route,
            arguments = listOf(navArgument(Constant.POLICY_DETAIL_KEY) {
                type = NavType.StringType
            })
        ) { backStackEntry ->
            backStackEntry.arguments?.getString(Constant.POLICY_DETAIL_KEY)
                ?.let { PolicyDetailScreen(it, navController, modifier = modifier) }
        }

        composable(route = Screen.Search.route) {
            SearchScreen(navController = navController, modifier = modifier)
        }

        composable(route = Screen.Scrap.route) {
            ScrapScreen(navController = navController, modifier = modifier)
        }

        composable(route = Screen.MyPage.route) {
            MyPageScreen(navController = navController, modifier = modifier)
        }
    }
}