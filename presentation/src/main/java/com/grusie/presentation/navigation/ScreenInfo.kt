package com.grusie.presentation.navigation

sealed class Screen(val route: String) {
    object Splash : Screen("splash_screen")
    object Home : Screen("home_screen")
    object PolicyDetail : Screen("policy_detail_screen/{policyId}") {
        fun passPolicyId(policyId: String) = "policy_detail_screen/$policyId"
    }

    object MyPage : Screen("my_page")

    object Scrap : Screen("scrap")
    object Search : Screen("search_screen")
    object Setting : Screen("setting_screen")
    object SignIn : Screen("signIn_screen")
    object Signup : Screen("signup_screen")
    object ManageAuth : Screen("manage_auth_screen")
}