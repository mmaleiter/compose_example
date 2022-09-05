package com.pixabay.ui.home

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.pixabay.MainViewModel

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun SearchWidget(
    viewModel: MainViewModel = hiltViewModel()
) {

//    val (focusRequester) = FocusRequester.createRefs()
    val keyboardController = LocalSoftwareKeyboardController.current
    val context = LocalContext.current
    Box(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        val inputValue = rememberSaveable(
            stateSaver = TextFieldValue.Saver
        ) {
            mutableStateOf(TextFieldValue())
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = inputValue.value,
                singleLine = true,
                shape = CircleShape,
                onValueChange = {
                    inputValue.value = it
                    viewModel.searchTerm = it.text
                },
                trailingIcon = {
                    Icon(
                        tint = MaterialTheme.colors.primary,
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Search",
                        modifier = Modifier
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = rememberRipple(bounded = false),
                                onClick = { inputValue.value = TextFieldValue() }
                            )
                            .align(Alignment.CenterVertically)
                            .padding(8.dp)
                    )
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background,
                    disabledTextColor = Color.Transparent,
//                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = MaterialTheme.colors.onSurface,
//                    disabledIndicatorColor = Color.Transparent
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Search
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
//                        keyboardController?.hide()
                        viewModel.executeSearch()
                    }
                ),
                placeholder = {
                    Text(
                        text = "Suche eingeben",
                        color = MaterialTheme.colors.onBackground
                    )
                },
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            )
//            IconButton(
//                onClick = viewModel::executeSearch,
//                modifier = Modifier
//                    .align(Alignment.CenterVertically)
//            ) {
//                Icon(
//                    imageVector = Icons.Default.Search,
//                    contentDescription = "Search"
//                )
//            }
        }
    }
}