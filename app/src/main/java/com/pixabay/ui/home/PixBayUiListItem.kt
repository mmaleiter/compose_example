package com.pixabay.ui.home

import cut.the.crap.mylibrary.models.PixaBayItem
import com.pixabay.ui.base.ListAdapterItem

data class PixBayUiListItem(
    val pixBayItem: PixaBayItem,
    override val id: Long = pixBayItem.id,
    val isFavourite: Boolean = false
) : ListAdapterItem

fun PixaBayItem.getTagList(): List<String> {
    return this.tags.split(",").map {
        it.trim()
    }.toSet().toList()
}