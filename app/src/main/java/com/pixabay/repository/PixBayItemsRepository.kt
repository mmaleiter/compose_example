package com.pixabay.repository

import androidx.compose.runtime.State
import com.pixabay.ui.base.Resource
import com.pixabay.ui.home.PixBayUiListItem

interface PixBayItemsRepository {

    val currentSearchResultList: State<Resource<List<PixBayUiListItem>>>

    val isFavouriteList: MutableList<Long>

    suspend fun searchImages(searchTerm: String = "fruits")

    fun toggleFavourite(imageItem: PixBayUiListItem)
}

