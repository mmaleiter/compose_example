package com.pixabay.ui.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
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
    Box(
        modifier = Modifier.padding(top = 16.dp)
    ) {
        val inputValue = rememberSaveable(
            stateSaver = TextFieldValue.Saver) {
            mutableStateOf(TextFieldValue())
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            TextField(
                value = inputValue.value,
                onValueChange = {
                    inputValue.value = it
                    viewModel.searchTerm = it.text
                },
                colors = TextFieldDefaults.textFieldColors(
                    textColor = MaterialTheme.colors.onBackground,
                    backgroundColor = MaterialTheme.colors.background
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
                modifier = Modifier.weight(1f)
            )
            IconButton(
                onClick = viewModel::executeSearch,
                modifier = Modifier
                    .padding(8.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search"
                )
            }
        }
    }
}