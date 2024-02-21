package com.grusie.presentation.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home_screen")
    object PolicyDetail : Screen("policy_detail_screen/{policyId}") {
        fun passPolicyId(policyId: String) = "policy_detail_screen/$policyId"
    }

    object MyPage : Screen("my_page")

    object Search : Screen("search_screen")
}