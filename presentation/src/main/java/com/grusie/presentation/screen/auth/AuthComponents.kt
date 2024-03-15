package com.grusie.presentation.screen.auth

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.grusie.presentation.R
import com.grusie.presentation.ui.theme.Blue500

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun IdTextField(
    idText: String,
    changeIdText: (String) -> Unit = {},
    verifyFlag: Boolean = false,
    enabled: Boolean = true,
    verifyOnClick: () -> Unit = {}
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = idText,
        onValueChange = { changeIdText(it) },
        label = {
            Text(
                text = stringResource(id = R.string.str_id_hint)
            )
        },
        singleLine = true,
        trailingIcon = {
            if (verifyFlag) {
                if(enabled) {
                    Text(
                        modifier = Modifier
                            .clickable {
                                verifyOnClick()
                            }
                            .padding(2.dp),
                        text = stringResource(id = R.string.str_verified), color = Blue500
                    )
                }
            }
        },
        enabled = enabled
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextField(pwText: String, changePwText: (String) -> Unit = {}) {
    var passwordVisibility by remember { mutableStateOf(false) }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = pwText,
        onValueChange = { changePwText(it) },
        label = {
            Text(
                text = stringResource(id = R.string.str_pw_hint)
            )
        }, singleLine = true,
        trailingIcon = {
            IconButton(onClick = { passwordVisibility = !passwordVisibility }) {
                Image(
                    painterResource(if (passwordVisibility) R.drawable.password_invisible else R.drawable.password_visible),
                    contentDescription = "password_visibility_icon",
                    colorFilter = ColorFilter.tint(Color.Gray)
                )
            }
        },
        visualTransformation = if (passwordVisibility) VisualTransformation.None else PasswordVisualTransformation()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTopBar(title: String, goToBack: () -> Unit = {}) {
    CenterAlignedTopAppBar(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = dimensionResource(id = R.dimen.margin_large)),
        title = {

            Text(text = title, fontWeight = FontWeight.Bold)
        },
        navigationIcon = {
            IconButton(
                onClick = { goToBack() },
            ) {
                Icon(
                    imageVector = Icons.AutoMirrored.Default.ArrowBack,
                    contentDescription = "login_back_icon"
                )
            }
        }
    )
}