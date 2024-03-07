package com.grusie.presentation.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import com.grusie.domain.model.PolicySimple
import com.grusie.presentation.R
import com.grusie.presentation.navigation.Screen
import com.grusie.presentation.ui.theme.Gray900
import com.grusie.presentation.util.TextUtils

/**
 * 메인 페이지 간단한 정책 정보 컴포넌트들
 **/
@Composable
fun PolicySimpleList(
    policyList: LazyPagingItems<PolicySimple>,
    navController: NavHostController,
    loading: (Boolean) -> Unit
) {
    /*val scrollState = rememberLazyListState()*/
    LazyColumn(
        //state = scrollState,
        contentPadding = PaddingValues(
            horizontal = dimensionResource(id = R.dimen.margin_default),
            vertical = 4.dp
        )
    ) {
        items(items = policyList, key = { policy ->
            policy.id
        }) { policy ->
            if (policy != null) {
                PolicySimpleItem(policy = policy, navController = navController)
            }
        }
        when {
            policyList.loadState.refresh is LoadState.Loading || policyList.loadState.append is LoadState.Loading -> {
                loading(true)
            }

            policyList.loadState.append is LoadState.Error -> {
                loading(false)
                item {
                    LoadStateFooter(
                        loadState = policyList.loadState.append,
                        onRetryClick = { policyList.retry() },
                    )
                }
            }

            else -> loading(false)
        }
    }
}

@Composable
fun LoadStateFooter(
    modifier: Modifier = Modifier,
    loadState: LoadState,
    onRetryClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
    ) {
        if(loadState is LoadState.Error){
            LoadErrorScreen(onRetryClick = onRetryClick)
        }
    }
}

@Composable
fun LoadErrorScreen(
    modifier: Modifier = Modifier,
    onRetryClick: () -> Unit,
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Row(
            modifier = modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(
                text = stringResource(id = R.string.error_msg),
                color = Gray900,
                fontSize = 16.sp,
                modifier = Modifier.align(Alignment.CenterVertically),
            )
            Button(
                onClick = onRetryClick,
            ) {
                Text(text = stringResource(id = R.string.retry))
            }
        }
    }
}

@Composable
fun PolicySimpleItem(policy: PolicySimple, navController: NavController) {
    Card(
        modifier = Modifier
            .padding(bottom = dimensionResource(id = R.dimen.margin_default))
            .fillMaxWidth()
            .clickable {
                navController.navigate(route = Screen.PolicyDetail.passPolicyId(policy.id))
            },
        elevation = CardDefaults.cardElevation(4.dp)
    ) {
        Column(
            modifier = Modifier.padding(
                horizontal = dimensionResource(id = R.dimen.margin_default),
                vertical = dimensionResource(id = R.dimen.margin_default)
            )
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {

                Row(
                    modifier = Modifier
                        .background(
                            color = Color.LightGray,
                            shape = RoundedCornerShape(dimensionResource(id = R.dimen.margin_default))
                        )
                        .padding(4.dp)
                        .weight(1f, fill = false),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Default.ThumbUp,
                        contentDescription = "support target",
                        Modifier
                            .size(25.dp)
                            .padding(horizontal = 4.dp),
                        tint = Color.Black
                    )
                    Text(
                        modifier = Modifier.padding(end = 4.dp),
                        text = TextUtils.replaceEmptyText(
                            policy.kindInfo,
                            stringResource(id = R.string.str_none_limited)
                        ),
                        style = TextStyle(
                            fontSize = 12.sp,
                            color = Color.Black
                        ),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }

                IconButton(
                    modifier = Modifier, onClick = { /*TODO*/ }) {
                    Icon(
                        modifier = Modifier.padding(0.dp),
                        imageVector = Icons.Default.FavoriteBorder,
                        contentDescription = "Policy Scrap"
                    )
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
            ) {
                policy.let {
                    Text(
                        text = TextUtils.replaceEmptyText(
                            it.name,
                            stringResource(id = R.string.str_none)
                        ),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Bold,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = TextUtils.replaceEmptyText(
                            it.subInfo,
                            stringResource(id = R.string.str_none)
                        ),
                        fontSize = 12.sp,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        color = Color.Gray
                    )
                    Spacer(modifier = Modifier.height(dimensionResource(id = R.dimen.margin_default)))
                    Text(
                        text = TextUtils.replaceEmptyText(
                            it.organization,
                            stringResource(id = R.string.str_none)
                        ),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = TextUtils.replaceEmptyText(
                            policy.period,
                            stringResource(id = R.string.str_none_period_limited)
                        ),
                        fontSize = 12.sp,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            }
        }
    }
}

@Preview
@Composable
fun PolicySimpleItemPreview() {
    PolicySimpleItem(
        policy = PolicySimple(
            subInfo = "청년마음건강지원사업 이용자 모집(안양시)",
            name = "[우울감, 취업 애로 등으로 심리적 어려움을 겪고 있는 전국 청년을 대상으로 전문심리상담 서비스 제공]",
            period = "-",
            organization = "안양시청",
            kindInfo = "2023년 1월 1일 이후 도내 출생아 출산 가정"
        ), navController = rememberNavController()
    )
}