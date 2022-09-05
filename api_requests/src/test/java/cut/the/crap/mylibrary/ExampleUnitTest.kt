package cut.the.crap.mylibrary

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test


@OptIn(ExperimentalCoroutinesApi::class)
class ExampleUnitTest {

    private val networkModule = NetworkModule()

    private val api: PixaBayService = networkModule.provideApiService(
        retrofit = networkModule.provideRetrofit(
            okHttpClient = networkModule.providesOkHttpClient(
                httpLoggingInterceptor = networkModule.providesHttpLoggingInterceptor(),
                apiKeyInterceptor = networkModule.providesApikeyInterceptor(BuildConfig.apiKey)
            )
        )
    )

    @Test
    fun verifyApiCallListSizeEquals20() {
        runTest {
            val list = api.searchPhotos(searchTerm = "fruits")
            assertEquals(list.hits.size, 20)
        }
    }
}