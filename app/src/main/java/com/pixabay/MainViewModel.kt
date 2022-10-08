package com.pixabay

import androidx.compose.runtime.State
import androidx.lifecycle.*
import com.pixabay.repository.PixBayItemsRepository
import com.pixabay.ui.base.Resource
import com.pixabay.ui.home.PixBayUiListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pixBayItemsRepository: PixBayItemsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val imageList: State<Resource<List<PixBayUiListItem>>> =
        pixBayItemsRepository.currentSearchResultList

    val filterList: State<List<String>> =
        pixBayItemsRepository.currentFilterList

    var searchTerm = ""

    lateinit var detailItem: PixBayUiListItem

    init {
        viewModelScope.launch {
            pixBayItemsRepository.searchImages()
        }
        savedStateHandle.get<PixBayUiListItem>("detailItem")?.let {
            detailItem = it
        }
    }

    val selectedFilterChips = mutableListOf<String>()

    fun showDetailScreen(pixaItem: PixBayUiListItem) {
        savedStateHandle["detailItem"] = pixaItem
        detailItem = pixaItem
    }

    fun executeSearch() {
        viewModelScope.launch {
            val queryParam = searchTerm.replace("\\s+".toRegex(), "+")
            pixBayItemsRepository.searchImages(queryParam)
        }
    }

    fun toggleFavourite(pixaItem: PixBayUiListItem) {
        pixBayItemsRepository.toggleFavourite(pixaItem)
    }

}


