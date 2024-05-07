package com.grusie.presentation.screen.search

import android.os.Build
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.grusie.presentation.R
import com.grusie.presentation.components.EmptyView
import com.grusie.presentation.components.Progress
import com.grusie.presentation.components.SingleAlertDialog
import com.grusie.presentation.screen.home.PolicySimpleList
import com.grusie.presentation.uiState.PolicySearchUiState
import com.grusie.presentation.util.TextUtils
import com.grusie.presentation.viewmodel.PolicySearchViewModel

/**
 * 검색 페이지
 **/

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    viewModel: PolicySearchViewModel = hiltViewModel(),
) {
    val searchQuery = viewModel.searchQuery.collectAsState()
    val searchPolicyList = viewModel.searchPolicyList.collectAsLazyPagingItems()
    val isLoading by viewModel.isLoading.collectAsState()
    val swipeRefreshState = rememberSwipeRefreshState(isRefreshing = isLoading)
    val policyListUiState = viewModel.policySearchUiState.collectAsState().value
    val context = LocalContext.current
    var initList by remember { mutableStateOf(false) }
    val focusManager = LocalFocusManager.current

    Scaffold(modifier = modifier
        .fillMaxSize()
        .pointerInput(Unit) {
            detectTapGestures(onPress = { focusManager.clearFocus() })
        }
    ) {
        Column(modifier = modifier.padding(it)) {
            SearchBar(
                finish = { navController.popBackStack() },
                searchQuery = searchQuery.value,
                changeSearchQuery = { query -> viewModel.changeSearchQuery(query) },
                onSearch = { if (policyListUiState !is PolicySearchUiState.Loading) viewModel.doSearch() })
            SwipeRefresh(state = swipeRefreshState, onRefresh = {
                viewModel.loading()
            }) {
                LaunchedEffect(searchPolicyList.loadState) {
                    val loadState = searchPolicyList.loadState
                    if (loadState.refresh is LoadState.NotLoading && loadState.append is LoadState.NotLoading && loadState.prepend is LoadState.NotLoading) {
                        if (searchPolicyList.itemCount > 0) {
                            focusManager.clearFocus()
                            initList = true
                        } else {
                            initList = false
                            viewModel.setPolicyUiState(
                                PolicySearchUiState.Empty
                            )
                        }
                    }
                }

                if (initList) {
                    PolicySimpleList(
                        policyList = searchPolicyList,
                        navController = navController,
                        loading = { loading -> viewModel.setPolicyUiState(if (loading) PolicySearchUiState.Loading else PolicySearchUiState.Success) }
                    )
                }


                when (policyListUiState) {
                    is PolicySearchUiState.Loading -> {
                        Progress(
                            modifier = Modifier.padding(bottom = 200.dp),
                            onClick = { focusManager.clearFocus() })
                    }

                    is PolicySearchUiState.Success -> {
                    }

                    is PolicySearchUiState.Error -> {
                        SingleAlertDialog(
                            confirm = false,
                            title = stringResource(id = R.string.str_title_error),
                            content = TextUtils.getErrorMsg(
                                context,
                                policyListUiState.error
                            )
                        )
                    }

                    is PolicySearchUiState.Empty -> {
                        EmptyView(
                            modifier = Modifier.padding(bottom = 200.dp),
                            query = viewModel.currentQuery
                        )
                    }

                    is PolicySearchUiState.Search -> {
                        SearchView(modifier = Modifier.padding(bottom = 200.dp))
                    }

                    else -> {}
                }
            }
        }
    }
}


@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Preview
@Composable
fun SearchScreenPreView() {
    SearchScreen(navController = rememberNavController())
}