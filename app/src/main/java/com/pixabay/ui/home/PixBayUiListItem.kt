package com.pixabay.ui.home

import cut.the.crap.mylibrary.PixaBayItem
import cut.the.crap.mylibrary.ListAdapterItem
import java.io.Serializable

data class PixBayUiListItem(
    val pixBayItem: PixaBayItem,
    override val id: Long = pixBayItem.id,
    val isFavourite: Boolean = false
) : ListAdapterItem, Serializable