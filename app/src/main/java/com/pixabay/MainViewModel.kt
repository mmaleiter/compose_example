package com.pixabay

import androidx.compose.ui.graphics.Color
import androidx.lifecycle.*
import com.pixabay.repository.PixBayItemsRepository
import com.pixabay.ui.base.Resource
import com.pixabay.ui.home.PixBayUiListItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val pixBayItemsRepository: PixBayItemsRepository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    val imageList: StateFlow<Resource<List<PixBayUiListItem>>> =
        pixBayItemsRepository.currentSearchResultList

    val filterList: StateFlow<List<String>> =
        pixBayItemsRepository.currentFilterList

    var searchTerm = ""

    lateinit var detailItem: PixBayUiListItem


    var commonColor: Color = Color(0xFFBB86FC)
        private set

    init {
        viewModelScope.launch {
            pixBayItemsRepository.searchImages()
        }
        savedStateHandle.get<PixBayUiListItem>("detailItem")?.let {
            detailItem = it
        }
    }

    val selectedFilterChips = mutableListOf<String>()

    fun setSearchTermState(searchTerm: String) {
        this.searchTerm = searchTerm
    }

    fun showDetailScreen(pixaItem: PixBayUiListItem) {
        savedStateHandle["detailItem"] = pixaItem
        detailItem = pixaItem
    }

   fun newCommonColor (newColor: Color) {
        commonColor = newColor
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


