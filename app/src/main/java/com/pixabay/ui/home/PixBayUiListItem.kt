package com.pixabay.ui.home

import com.pixabay.apiservice.PixaBayItem
import com.pixabay.ui.base.ListAdapterItem
import java.io.Serializable

data class PixBayUiListItem(
    val pixBayItem: PixaBayItem,
    override val id: Long = pixBayItem.id,
    val isFavourite: Boolean = false
) : ListAdapterItem, Serializable