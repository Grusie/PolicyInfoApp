package com.grusie.presentation.screen.search

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.grusie.presentation.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchBar(
    finish: () -> Unit,
    searchQuery: String,
    changeSearchQuery: (String) -> Unit,
    onSearch: () -> Unit
) {
    Row(
        modifier = Modifier.padding(dimensionResource(id = R.dimen.margin_default)),
        verticalAlignment = Alignment.CenterVertically
    ) {
        IconButton(onClick = { finish() }) {
            Icon(
                imageVector = Icons.AutoMirrored.Default.ArrowBack,
                contentDescription = "search_back_icon"
            )
        }
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth(),
            value = searchQuery,
            singleLine = true,
            trailingIcon = {
                Icon(imageVector = Icons.Filled.Search, contentDescription = "search_icon")
            },
            onValueChange = { changeSearchQuery(it) },
            keyboardActions = KeyboardActions(onDone = {
                onSearch()
            })
        )
    }
}