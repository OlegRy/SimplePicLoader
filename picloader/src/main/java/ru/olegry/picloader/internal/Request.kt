package ru.olegry.picloader.internal

import android.graphics.drawable.Drawable
import android.widget.ImageView

/**
 *
 *
 * @author Олег Рябцев
 */
internal data class Request(
    val url: String,
    val options: LoadOptions
)

internal data class LoadOptions(
    val placeholder: Drawable?,
    val hasBorder: Boolean,
    val size: Size
)

internal data class Size(
    val width: Int,
    val height: Int
) {

    fun isEmpty(): Boolean = width <= 0 || height <= 0

    companion object {
        @JvmStatic
        fun fitToView(imageView: ImageView) = with(imageView) {
            Size(width, height)
        }
    }
}