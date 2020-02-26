package ru.olegry.picloader.internal

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import ru.olegry.picloader.internal.common.DefaultDownloadProgressListener
import ru.olegry.picloader.internal.common.DefaultRxSchedulers
import ru.olegry.picloader.internal.common.DownloadProgressListener
import ru.olegry.picloader.internal.common.RxSchedulers
import ru.olegry.picloader.internal.network.DownloadProgressInterceptor
import ru.olegry.picloader.internal.network.ImageLoaderApi

/**
 * An object which provides shared dependencies for library
 *
 * @author Oleg Ryabtsev
 */
internal object Dependencies {

    // we don't need any host since we have only one method for loading image by full url, but retrofit
    // restricts passing empty host, so we're using google.com
    val loaderApi: ImageLoaderApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://google.com")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
            .create(ImageLoaderApi::class.java)
    }

    val schedulers: RxSchedulers by lazy { DefaultRxSchedulers() }

    val downloadProgressListener: DownloadProgressListener by lazy {
        DefaultDownloadProgressListener()
    }

    private val client: OkHttpClient by lazy {
        OkHttpClient.Builder()
            .addInterceptor(
                DownloadProgressInterceptor(
                    downloadProgressListener
                )
            )
            .build()
    }
}