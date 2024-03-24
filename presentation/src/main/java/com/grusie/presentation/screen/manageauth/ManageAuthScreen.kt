package com.grusie.presentation.screen.manageauth

import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grusie.presentation.R
import com.grusie.presentation.components.BackBtnTopBar
import com.grusie.presentation.components.Progress
import com.grusie.presentation.uiState.ManageAuthUiState
import com.grusie.presentation.viewmodel.ManageAuthViewModel

@Composable
fun ManageAuthScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: ManageAuthViewModel = hiltViewModel()
) {
    val uiState = viewModel.manageAuthUiState.collectAsState().value
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            BackBtnTopBar(
                title = stringResource(id = R.string.str_manage_auth),
                goToBack = { navController.popBackStack() })
        }) {
        Column {
            Button(modifier = Modifier.padding(it), onClick = { viewModel.signOut() }) {
                Text(text = stringResource(id = R.string.str_go_signOut))
            }

            when(uiState){
                is ManageAuthUiState.Loading -> {
                    Progress()
                }

                is ManageAuthUiState.SuccessSignOut -> {
                    Toast.makeText(context, stringResource(id = R.string.str_success_signOut), Toast.LENGTH_SHORT).show()
                    Handler(Looper.getMainLooper()).postDelayed({
                        navController.popBackStack()
                    }, Toast.LENGTH_SHORT.toLong())
                }
                else -> {

                }
            }
        }
    }
}