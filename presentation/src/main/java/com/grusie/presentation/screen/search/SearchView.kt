package com.grusie.presentation.screen.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.sp
import com.grusie.presentation.R


@Composable
fun SearchView(modifier: Modifier = Modifier) {
    Column(modifier = modifier
        .fillMaxSize()
        , verticalArrangement = Arrangement.Center
        , horizontalAlignment = Alignment.CenterHorizontally
    ){
        Image(painter = painterResource(id = R.drawable.search_icon), contentDescription = "search_icon")
        Text(text = stringResource(id = R.string.str_write_search), fontSize = 20.sp)
    }
}