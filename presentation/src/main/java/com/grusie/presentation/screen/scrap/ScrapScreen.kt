package com.grusie.presentation.screen.scrap

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.grusie.presentation.R
import com.grusie.presentation.components.EmptyView
import com.grusie.presentation.components.Progress
import com.grusie.presentation.components.SingleAlertDialog
import com.grusie.presentation.screen.home.HomeTopBar
import com.grusie.presentation.screen.home.PolicySimpleList
import com.grusie.presentation.uiState.PolicyListUiState
import com.grusie.presentation.util.TextUtils
import com.grusie.presentation.viewmodel.PolicyFavoriteViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ScrapScreen(
    navController: NavHostController,
    viewModel: PolicyFavoriteViewModel = hiltViewModel(),
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    val policyList = viewModel.policyList.collectAsLazyPagingItems()
    val policyListUiState = viewModel.policyListUiState.collectAsState().value
    val isLoading by viewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    var initList by remember { mutableStateOf(false) }

    Scaffold(
        modifier = modifier,
        topBar = { HomeTopBar(navController = navController) }) {
        SwipeRefresh(state = swipeRefreshState, onRefresh = {
            viewModel.loading()
        }, modifier = Modifier.padding(it)) {
            Box {
                LaunchedEffect(policyList.loadState) {
                    val loadState = policyList.loadState
                    if (loadState.refresh !is LoadState.Loading &&
                        loadState.append !is LoadState.Loading &&
                        loadState.prepend !is LoadState.Loading &&
                        loadState.source.refresh !is LoadState.Loading &&
                        loadState.source.append !is LoadState.Loading &&
                        loadState.source.prepend !is LoadState.Loading
                    ) {
                        Log.d(
                            "confirm loadState in HomeScreen : ",
                            "$loadState, ${policyList.itemCount}"
                        )
                        initList = if (policyList.itemCount > 0) {
                            true
                        } else {
                            viewModel.setPolicyUiState(PolicyListUiState.Empty)
                            false
                        }
                    }
                }

                if (initList) {
                    PolicySimpleList(
                        policyList = policyList,
                        navController = navController,
                        loading = { loading ->
                            viewModel.setPolicyUiState(if (loading) PolicyListUiState.Loading else PolicyListUiState.Success)
                        }
                    )
                }

                when (policyListUiState) {
                    is PolicyListUiState.Loading -> {
                        Progress()
                    }

                    is PolicyListUiState.Success -> {
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

                    is PolicyListUiState.Empty -> {
                        EmptyView()
                    }

                    else -> {

                    }
                }
            }

        }
    }
}