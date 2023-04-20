package com.pixabay.repository

import androidx.compose.runtime.State
import com.pixabay.ui.base.Resource
import com.pixabay.ui.home.PixBayUiListItem
import kotlinx.coroutines.flow.StateFlow

interface PixBayItemsRepository {

    val currentSearchResultList: StateFlow<Resource<List<PixBayUiListItem>>>

    val currentFilterList: StateFlow<List<String>>

    val isFavouriteList: MutableList<Long>

    suspend fun searchImages(searchTerm: String = "fruits")

    fun toggleFavourite(imageItem: PixBayUiListItem)
}

