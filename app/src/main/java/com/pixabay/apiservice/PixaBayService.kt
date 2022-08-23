package com.pixabay.apiservice

import retrofit2.http.GET
import retrofit2.http.Query

interface PixaBayService {
    @GET("api")
    suspend fun searchPhotos(
        @Query("image_type") imageType: String = "photo",
        @Query("q") searchTerm: String = "",
    ): SearchResponse
}