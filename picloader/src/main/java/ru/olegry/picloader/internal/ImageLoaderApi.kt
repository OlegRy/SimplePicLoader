package ru.olegry.picloader.internal

import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Url

/**
 *
 *
 * @author Олег Рябцев
 */
internal interface ImageLoaderApi {

    @GET
    fun getImage(@Url url: String): Single<Response<ResponseBody>>
}