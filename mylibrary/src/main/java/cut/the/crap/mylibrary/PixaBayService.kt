package cut.the.crap.mylibrary

import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Inject

interface PixaBayService {
    @GET("api")
    suspend fun searchPhotos(
        @Query("image_type") imageType: String = "photo",
        @Query("q") searchTerm: String = "",
    ): SearchResponse
}

class WrappedService @Inject constructor(val service: PixaBayService) {

}