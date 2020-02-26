package ru.olegry.picloader.api

import android.widget.ImageView

/**
 * Extension function for API for more convenient usage with Kotlin
 *
 * @author Oleg Ryabtsev
 */
fun ImageView.load(url: String, builder: RequestBuilder.() -> Unit) {
    PicLoaderProvider.get()
        .load(url)
        .also(builder)
        .into(this)
}