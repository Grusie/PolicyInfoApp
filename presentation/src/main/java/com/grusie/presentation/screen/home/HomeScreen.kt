package com.grusie.presentation.screen.home


import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grusie.presentation.R
import com.grusie.presentation.components.Progress
import com.grusie.presentation.components.SingleAlertDialog
import com.grusie.presentation.navigation.Screen
import com.grusie.presentation.uiState.PolicyListUiState
import com.grusie.presentation.util.TextUtils
import com.grusie.presentation.viewmodel.PolicyListViewModel

/**
 * 메인 페이지
 **/
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController, viewModel: PolicyListViewModel = hiltViewModel()) {
    val context = LocalContext.current

    Scaffold(topBar = { HomeTopBar(navController = navController) }) {
        Box(modifier = Modifier.padding(it)) {
            when (val policyListUiState = viewModel.policyListUiState.collectAsState().value) {
                is PolicyListUiState.Loading -> {
                    Progress()
                }

                is PolicyListUiState.Success -> {
                    val policyList = policyListUiState.policySimpleList
                    PolicySimpleList(policyList = policyList, navController = navController)
                }

                is PolicyListUiState.Error -> {
                    SingleAlertDialog(
                        confirm = false,
                        title = stringResource(id = R.string.str_title_error),
                        content = TextUtils.getErrorMsg(
                            context,
                            policyListUiState.error
                        )
                    )
                }

                else -> {

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopBar(navController: NavHostController) {
    TopAppBar(
        title = {
            Text(
                text = stringResource(R.string.app_name),
                fontWeight = FontWeight.Bold,
                modifier = Modifier.fillMaxWidth(),
                fontSize = 16.sp
            )
        },
        actions = {
            IconButton(onClick = { navController.navigate(route = Screen.Search.route) }) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search Icon",
                )
            }
        }
    )
}