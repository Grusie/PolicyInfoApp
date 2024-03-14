package com.grusie.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object PolicyDetail : Screen("policy_detail_screen/{policyId}") {
        fun passPolicyId(policyId: String) = "policy_detail_screen/$policyId"
    }

    object MyPage : Screen("my_page")

    object Scrap : Screen("scrap")
    object Search : Screen("search_screen")
    object Setting : Screen("setting_screen")
    object Login : Screen("login_screen")
    object Signup : Screen("signup_screen")
}