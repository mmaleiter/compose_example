package cut.the.crap.mylibrary

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response


class ApiKeyInterceptor(private val apiKey: String) : Interceptor {

    private var retrievedKey: String = ""

    override fun intercept(chain: Interceptor.Chain): Response {
        val original: Request = chain.request()
        val originalHttpUrl: HttpUrl = original.url

//        val JSON = "application/json; charset=utf-8".toMediaTypeOrNull()
//        val i = chain.request().newBuilder().url("").post(RequestBody.create(JSON, "")).build()
//        val apiKeyResponse = chain.proceed(i)
//        apiKeyResponse.


        val url = originalHttpUrl.newBuilder()
            .addQueryParameter("key", apiKey)
            .build()

        val requestBuilder: Request.Builder = original.newBuilder()
            .url(url)

        val request: Request = requestBuilder.build()
        return chain.proceed(request)
    }
}