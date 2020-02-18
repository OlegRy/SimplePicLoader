package ru.olegry.picloader.api

import android.widget.ImageView

/**
 *
 *
 * @author Олег Рябцев
 */

fun ImageView.load(url: String, builder: RequestBuilder.() -> Unit) {
    PicLoaderProvider.get()
        .load(url)
        .also(builder)
        .into(this)
}