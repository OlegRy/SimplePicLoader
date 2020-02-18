package ru.olegry.picloader.internal

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory

/**
 *
 *
 * @author Олег Рябцев
 */
internal object Dependencies {

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
            .addInterceptor(DownloadProgressInterceptor(downloadProgressListener))
            .build()
    }
}