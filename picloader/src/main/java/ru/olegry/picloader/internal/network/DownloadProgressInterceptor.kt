package ru.olegry.picloader.internal.network

import okhttp3.Interceptor
import okhttp3.Response
import ru.olegry.picloader.internal.common.DownloadProgressListener

/**
 * An interceptor for OkHttp requests which allows us to track loading progress
 *
 * @property downloadProgressListener an instance of listener for passing progress values
 * @author Oleg Ryabtsev
 */
internal class DownloadProgressInterceptor(
    private val downloadProgressListener: DownloadProgressListener
) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalResponse = chain.proceed(chain.request())
        return originalResponse.newBuilder()
            .body(
                DownloadResponseBody(
                    originalResponse.body()!!,
                    downloadProgressListener
                )
            )
            .build()
    }
}