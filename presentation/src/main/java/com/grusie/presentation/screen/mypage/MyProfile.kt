package com.grusie.presentation.screen.mypage

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.KeyboardArrowRight
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.grusie.domain.model.UserInfo
import com.grusie.presentation.R

/**
 * 마이프로필
 * - 로그인 인증 및 회원정보
 **/
@Composable
fun MyProfile(
    modifier: Modifier = Modifier,
    userInfo: UserInfo?,
    goToManageAuthScreen: () -> Unit = {},
    goToAuthScreen: () -> Unit = {}
) {
    Spacer(modifier = Modifier.padding(vertical = dimensionResource(id = R.dimen.margin_large)))
    if (userInfo != null) Verified(
        modifier = modifier,
        userInfo = userInfo,
        goToManageAuth = { goToManageAuthScreen() }
    ) else UnVerified(modifier = modifier, goToAuthScreen = { goToAuthScreen() })
}

@Composable
fun UnVerified(modifier: Modifier = Modifier, goToAuthScreen: () -> Unit = {}) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween,
        modifier = modifier
            .fillMaxWidth()
            .clickable {
                goToAuthScreen()
            }
            .padding(
                vertical = dimensionResource(id = R.dimen.margin_large),
                horizontal = dimensionResource(
                    id = R.dimen.margin_default
                )
            )
    ) {
        Column {
            Text(
                text = stringResource(id = R.string.str_auth_title),
                fontWeight = FontWeight.Bold,
                fontSize = 20.sp
            )
            Text(
                text = stringResource(id = R.string.str_auth_sub_title),
                color = Color.Gray,
                fontSize = 12.sp
            )
        }
        Icon(
            imageVector = Icons.AutoMirrored.Default.KeyboardArrowRight,
            contentDescription = "search_icon",
            tint = Color.Gray
        )
    }
}

@Composable
fun Verified(modifier: Modifier = Modifier, userInfo: UserInfo, goToManageAuth: () -> Unit = {}) {
    Column(
        modifier = modifier.padding(
            vertical = dimensionResource(id = R.dimen.margin_large),
            horizontal = dimensionResource(
                id = R.dimen.margin_default
            )
        )
    ) {
        Text(
            text = stringResource(id = R.string.str_welcome),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )
        Text(
            text = if (userInfo.nickname.isNotEmpty()) stringResource(
                id = R.string.str_nickname,
                userInfo.nickname
            ) else stringResource(
                id = R.string.str_nickname_empty
            ),
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            maxLines = 1
        )
        Row(
            modifier = Modifier
                .clickable { goToManageAuth() }
                .fillMaxWidth()
                .padding(vertical = dimensionResource(id = R.dimen.margin_default)),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = userInfo.id)
            Text(
                color = Color.Gray,
                text = stringResource(id = R.string.str_manage_auth)
            )
        }
    }
}

/*
@Composable
@Preview(
    name = "UnVerifiedPreview",
    backgroundColor = 0x00ffffff,
    widthDp = 360,
    showBackground = true
)
fun UnVerifiedPreview() {
    UnVerified()
}

@Composable
@Preview(
    name = "VerifiedPreview",
    backgroundColor = 0x00ffffff,
    showBackground = true,
    widthDp = 360
)
fun VerifiedPreview() {
    Verified(userInfo = UserInfo(nickname = "닉네임입니다."))
}*/
