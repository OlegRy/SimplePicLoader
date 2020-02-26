package ru.olegry.picloader.internal.network

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 * A retrofit interface for image loading loading
 *
 * @author Oleg Ryabtsev
 */
internal interface ImageLoaderApi {

    /**
     * Performs a request by passed [url]
     */
    @GET
    fun getImage(@Url url: String): Single<Response<ResponseBody>>
}