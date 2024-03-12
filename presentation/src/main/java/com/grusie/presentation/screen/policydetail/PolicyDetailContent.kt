package com.grusie.presentation.screen.policydetail

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.KeyboardArrowUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.grusie.domain.model.PolicyDetail
import com.grusie.presentation.R
import com.grusie.presentation.util.Constant
import com.grusie.presentation.util.TextUtils

/**
 * 정책정보 상세 내용 컴포넌트들
 **/

@Composable
fun PolicyDetailContent(policyDetail: PolicyDetail) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .verticalScroll(state = scrollState)
            .fillMaxWidth()
            .padding(
                horizontal = dimensionResource(id = R.dimen.margin_large),
                vertical = dimensionResource(id = R.dimen.margin_default)
            )
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth(),
            colors = CardDefaults.cardColors(Color.LightGray),
            elevation = CardDefaults.cardElevation(4.dp)
        ) {
            Row(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default))) {
                Icon(
                    imageVector = Icons.Default.Info,
                    contentDescription = "summary_info_icon",
                    tint = Color.Black
                )
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_small)))
                Text(
                    modifier = Modifier.align(Alignment.CenterVertically),
                    text = TextUtils.replaceEmptyText(
                        policyDetail.subInfo,
                        stringResource(id = R.string.str_none)
                    ),
                    style = TextStyle(Color.Black)
                )
            }
        }
        PolicySummary(policyDetail)
        PolicyQualification(policyDetail)
        PolicyEtc(policyDetail)
    }
}

@Composable
fun PolicySummary(policyDetail: PolicyDetail) {
    val context = LocalContext.current

    val policySummaryMap = mutableMapOf<Int, String>()
    policyDetail.let {
        policySummaryMap[R.string.str_policy_id] = it.id
        policySummaryMap[R.string.str_policy_kind] =
            TextUtils.replacePolicyKindCode(context = context, it.policyKindCode)
        policySummaryMap[R.string.str_supply_info] = it.info
        policySummaryMap[R.string.str_period] = it.period
        policySummaryMap[R.string.str_period_application] = it.appPeriod
        policySummaryMap[R.string.str_supply_scale] = it.scale
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = dimensionResource(id = R.dimen.margin_large))
    ) {
        Text(
            text = stringResource(id = R.string.str_summary),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default)))

        policySummaryMap.forEach {
            PolicyItem(titleResource = it.key, content = it.value)
        }
    }
}

@Composable
fun PolicyQualification(policyDetail: PolicyDetail) {
    val policyQualificationMap = mutableMapOf<Int, String>()

    policyDetail.let {
        policyQualificationMap[R.string.str_age] = it.ageInfo
        policyQualificationMap[R.string.str_income] = it.locIncomeInfo
        policyQualificationMap[R.string.str_education] = it.eduInfo
        policyQualificationMap[R.string.str_major] = it.majorInfo
        policyQualificationMap[R.string.str_employment] = it.empInfo
        policyQualificationMap[R.string.str_specialization_kind] = it.kindInfo
        policyQualificationMap[R.string.str_qualification_etc] = it.ageInfo
        policyQualificationMap[R.string.str_limited_people] = it.limitPeople
    }
    Column(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_large))) {
        Text(
            text = stringResource(id = R.string.str_qualification),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default)))

        policyQualificationMap.forEach {
            PolicyItem(titleResource = it.key, content = it.value)
        }
    }
}

@Composable
fun PolicyEtc(policyDetail: PolicyDetail) {
    val policyEtcMap = mutableMapOf<Int, String>()
    policyDetail.let {
        policyEtcMap[R.string.str_etc_info] = it.etc
        policyEtcMap[R.string.str_host_department] = it.hostDepartment
        policyEtcMap[R.string.str_organization] = it.organization
        policyEtcMap[R.string.str_reference_url1] = it.referUrl1
        policyEtcMap[R.string.str_reference_url2] = it.referUrl2
    }
    Column(modifier = Modifier.padding(top = dimensionResource(id = R.dimen.margin_large))) {
        Text(
            text = stringResource(id = R.string.str_etc),
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )
        )
        Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default)))

        policyEtcMap.forEach {
            when (it.key) {
                R.string.str_reference_url1, R.string.str_reference_url2 -> PolicyItem(
                    titleResource = it.key,
                    content = it.value,
                    hyperLinkFlag = !TextUtils.isEmpty(it.value)
                )

                R.string.str_host_department -> PolicyItem(
                    titleResource = it.key,
                    content = it.value,
                    child = mapOf(
                        R.string.str_manager_name to policyDetail.managerName,
                        R.string.str_manager_number to policyDetail.managerNumber
                    )
                )

                R.string.str_organization -> PolicyItem(
                    titleResource = it.key,
                    content = it.value,
                    child = mapOf(
                        R.string.str_manager_name to policyDetail.orgManagerName,
                        R.string.str_manager_number to policyDetail.orgManageNumber
                    )
                )

                else -> PolicyItem(titleResource = it.key, content = it.value)
            }
        }
    }
}

@Composable
fun PolicyItem(
    modifier: Modifier = Modifier.padding(all = dimensionResource(id = R.dimen.margin_default)),
    titleResource: Int,
    content: String,
    hyperLinkFlag: Boolean = false,
    child: Map<Int, String>? = null,
    dividerFlag: Boolean = true
) {
    if (content.length >= Constant.STRING_LIMITED) {
        PolicyVerticalItem(
            modifier = modifier,
            titleResource = titleResource,
            content = content,
            hyperLinkFlag = hyperLinkFlag,
            child = child,
            dividerFlag = dividerFlag
        )
    } else {
        PolicyHorizontalItem(
            modifier = modifier,
            titleResource = titleResource,
            content = content,
            hyperLinkFlag = hyperLinkFlag,
            child = child,
            dividerFlag = dividerFlag
        )
    }
}

@Composable
fun PolicyHorizontalItem(
    modifier: Modifier = Modifier,
    titleResource: Int,
    content: String,
    hyperLinkFlag: Boolean,
    child: Map<Int, String>? = null,
    dividerFlag: Boolean = true
) {
    val context = LocalContext.current
    if (dividerFlag) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp), color = Color.LightGray
        )
    }
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row {
            Text(
                modifier = Modifier
                    .width(170.dp)
                    .align(Alignment.CenterVertically),
                text = stringResource(id = titleResource)
            )
            Spacer(modifier = Modifier.width(8.dp))

            Text(
                modifier = if (hyperLinkFlag) Modifier
                    .width(170.dp)
                    .clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(content))
                        context.startActivity(intent)
                    }
                    .align(Alignment.CenterVertically)
                    .weight(0.6f)
                else
                    Modifier
                        .align(Alignment.CenterVertically)
                        .weight(0.6f),
                text = TextUtils.replaceEmptyText(content, stringResource(id = R.string.str_none)),
                style = TextStyle(
                    fontSize = 12.sp,
                    color = if (hyperLinkFlag) Color.Blue else Color.Gray
                )
            )

            if (child != null) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (!expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = "child info expand Icon"
                    )
                }
            }
        }
        if (expanded) {
            child?.forEach {
                PolicyItem(
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin_default)),
                    titleResource = it.key,
                    content = it.value,
                    dividerFlag = false
                )
            }
        }
    }
}

@Composable
fun PolicyVerticalItem(
    modifier: Modifier = Modifier,
    titleResource: Int,
    content: String,
    hyperLinkFlag: Boolean,
    child: Map<Int, String>? = null,
    dividerFlag: Boolean = true
) {
    val context = LocalContext.current

    if (dividerFlag) {
        Divider(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp), color = Color.LightGray
        )
    }
    var expanded by remember { mutableStateOf(false) }
    Column(modifier = modifier) {
        Row() {
            Column(modifier = Modifier.weight(1f)) {
                Text(text = stringResource(id = titleResource))
                Spacer(modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_small)))

                Text(
                    modifier = if (hyperLinkFlag) Modifier.clickable {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(content))
                        context.startActivity(intent)
                    } else Modifier,
                    text = TextUtils.replaceEmptyText(
                        content,
                        stringResource(id = R.string.str_none)
                    ),
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = if (hyperLinkFlag) Color.Blue else Color.Gray
                    )
                )
            }

            if (child != null) {
                IconButton(onClick = { expanded = !expanded }) {
                    Icon(
                        imageVector = if (!expanded) Icons.Default.KeyboardArrowDown else Icons.Default.KeyboardArrowUp,
                        contentDescription = "child info expand Icon"
                    )
                }
            }
        }
        if (expanded) {
            child?.forEach {
                PolicyItem(
                    modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin_default)),
                    titleResource = it.key,
                    content = it.value,
                    dividerFlag = false
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PolicyDetailTopBar(
    policyDetail: PolicyDetail,
    navController: NavHostController,
    onScrapCallback: (String) -> Unit = {}
) {
    TopAppBar(
        title = {
            Text(
                TextUtils.replaceEmptyText(
                    policyDetail.name,
                    stringResource(id = R.string.str_none)
                ),
                style = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.Bold),
                modifier = Modifier.fillMaxWidth()
            )
        },
        navigationIcon = {
            IconButton(onClick = { navController.popBackStack() }) {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                    contentDescription = "Back Button"
                )
            }
        },
        actions = {
            IconButton(onClick = { onScrapCallback(policyDetail.id) }) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = "Policy Scrap"
                )
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Preview(heightDp = 1500)
@Composable
fun PolicyDetailTopBarPreView() {
    val policyDetail = PolicyDetail(
        subInfo = "[우울감, 취업 애로 등으로 심리적 어려움을 겪고 있는 전국 청년을 대상으로 전문심리상담 서비스 제공]",
        name = "청년마음건강지원사업 이용자 모집(안양시)",
        period = "-",
        organization = "안양시청",
        kindInfo = "2023년 1월 1일 이후 도내 출생아 출산 가정"
    )

    Scaffold(
        topBar = {
            PolicyDetailTopBar(
                navController = rememberNavController(),
                policyDetail = policyDetail
            )
        }
    ) {
        Box(modifier = Modifier.padding(it)) {
            PolicyDetailContent(policyDetail = policyDetail)
        }
    }
}