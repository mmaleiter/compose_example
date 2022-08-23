package com.pixabay.apiservice

data class SearchResponse (
    val total: Long,
    val totalHits: Long,
    val hits: List<PixaBayItem>
)