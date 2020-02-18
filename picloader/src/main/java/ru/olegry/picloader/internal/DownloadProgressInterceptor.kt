package ru.olegry.picloader.internal

import okhttp3.Interceptor
import okhttp3.Response

/**
 *
 *
 * @author Олег Рябцев
 */
internal class DownloadProgressInterceptor(
    private val downloadProgressListener: DownloadProgressListener
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .body(DownloadResponseBody(originalResponse.body()!!, downloadProgressListener))
            .build()
    }
}