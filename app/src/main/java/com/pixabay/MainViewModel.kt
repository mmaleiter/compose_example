package com.pixabay

import androidx.compose.runtime.State
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pixabay.repository.PixBayItemsRepository
import com.pixabay.ui.base.Resource
import com.pixabay.ui.home.PixBayUiListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pixBayItemsRepository: PixBayItemsRepository
) : ViewModel() {

    val imageList: State<Resource<List<PixBayUiListItem>>> =
        pixBayItemsRepository.currentSearchResultList

    var searchTerm = ""

    lateinit var detailItem : PixBayUiListItem

    init {
        viewModelScope.launch {
            pixBayItemsRepository.searchImages()
        }
    }

    fun showDetailScreen(pixaItem : PixBayUiListItem) {
        detailItem = pixaItem
    }

    fun executeSearch() {
        viewModelScope.launch {
            val queryParam = searchTerm.replace("\\s+".toRegex(),"+")
            pixBayItemsRepository.searchImages(queryParam)
        }
    }

    fun toggleFavourite(pixaItem: PixBayUiListItem) {
        pixBayItemsRepository.toggleFavourite(pixaItem)
    }

}
