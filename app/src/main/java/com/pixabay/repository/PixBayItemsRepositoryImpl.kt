package com.pixabay.repository

import androidx.compose.runtime.mutableStateOf
import cut.the.crap.mylibrary.PixaBayService
import com.pixabay.ui.base.Resource
import com.pixabay.ui.home.PixBayUiListItem

class PixBayItemsRepositoryImpl(
    private val pixaBayService: PixaBayService,
) : PixBayItemsRepository {

    override val currentSearchResultList =
        mutableStateOf(Resource.success<List<PixBayUiListItem>>(data = emptyList()))

    override val isFavouriteList: MutableList<Long> = mutableListOf()

    override suspend fun searchImages(searchTerm: String) {
        val backupState = currentSearchResultList.value
        currentSearchResultList.value = Resource.loading()
        try {
            val resultList = pixaBayService.searchPhotos(searchTerm = searchTerm).hits.map {
                PixBayUiListItem(it, isFavourite = isFavouriteList.contains(it.id))
            }
            currentSearchResultList.value = Resource.Success(resultList)
        } catch (e: Throwable) {
            currentSearchResultList.value = Resource.Error(data = backupState.data, cause = e)
        }
    }

    override fun toggleFavourite(imageItem: PixBayUiListItem) {
        val itemIndex = currentSearchResultList.value.data?.indexOf(imageItem) ?: -1
        val newList = currentSearchResultList.value.data?.toMutableList()
            ?: emptyList<PixBayUiListItem>().toMutableList()
        val isFavourite = if (isFavouriteList.contains(imageItem.id)) {
            isFavouriteList.remove(imageItem.id)
            false
        } else {
            isFavouriteList.add(imageItem.id)
            true
        }
        if (itemIndex > -1) {
            newList[itemIndex] = imageItem.copy(isFavourite = isFavourite)
            currentSearchResultList.value = Resource.Success(newList.toList())
        }
    }

}