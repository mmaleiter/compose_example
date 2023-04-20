package cut.the.crap.mylibrary

import cut.the.crap.mylibrary.models.SearchResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface PixaBayService {

    @GET("api")
    suspend fun searchPhotos(
                             @Query("q") searchTerm: String = "",
                             @Query("lang")language: String = "en",
                             @Query("image_type") imageType: String = "all",
                             @Query("orientation") orientation: String = "all",
                             @Query("category") category: String = "",
                             @Query("min_width") min_width: String = "0",
                             @Query("min_height") min_height: String = "0",
                             @Query("colors") colors: String = "",
                             @Query("editors_choice") editors_choice: String = "false",
                             @Query("order") order: String = "popular",
                             @Query("page") page: Int = 1,
                             @Query("per_page") per_page: Int = 20,
    ): SearchResponse
}
