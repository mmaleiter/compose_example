package com.pixabay.ui.base

import okhttp3.Interceptor
import okhttp3.Response

class ResponseHeaderInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val response = chain.proceed(chain.request())
        return response.newBuilder()
            .header("Cache-Control", "max-age=60")
            .build()
    }
}