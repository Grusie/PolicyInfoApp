package com.grusie.presentation.screen.policydetail

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import com.grusie.presentation.R
import com.grusie.presentation.components.EmptyView
import com.grusie.presentation.components.SingleAlertDialog
import com.grusie.presentation.uiState.PolicyDetailUiState
import com.grusie.presentation.uiState.PolicyListUiState
import com.grusie.presentation.util.TextUtils
import com.grusie.presentation.viewmodel.PolicyDetailViewModel

/**
 * 정책정보 상세 페이지
 **/
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolicyDetailScreen(
    policyId: String,
    navController: NavHostController,
    viewModel: PolicyDetailViewModel = hiltViewModel()
) {
    val context = LocalContext.current

    LaunchedEffect(policyId) {
        viewModel.getPolicyDetail(policyId)
    }
    val policyDetailUiState by viewModel.policyDetailUiState.collectAsState()

    when (policyDetailUiState) {
        is PolicyDetailUiState.Success -> {
            val policyDetail = (policyDetailUiState as PolicyDetailUiState.Success).policyDetail
            Scaffold(
                topBar = {
                    PolicyDetailTopBar(
                        navController = navController,
                        policyDetail = policyDetail
                    ) { policyId ->
                        viewModel.setScrap(policyId)
                    }
                }
            ) {
                Box(modifier = Modifier.padding(it)) {
                    PolicyDetailContent(policyDetail = policyDetail)
                }
            }
        }

        is PolicyDetailUiState.Loading -> {
            CircularProgressIndicator()
        }

        is PolicyDetailUiState.Error -> {
            SingleAlertDialog(
                confirm = false,
                title = stringResource(id = R.string.str_title_error),
                content = TextUtils.getErrorMsg(
                    context,
                    (policyDetailUiState as PolicyDetailUiState.Error).error
                ),
                confirmCallBack = {
                    navController.popBackStack()
                }
            )
        }

        is PolicyDetailUiState.Empty -> {
            EmptyView()
        }
    }
}
