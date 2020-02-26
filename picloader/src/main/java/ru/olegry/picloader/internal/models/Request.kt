package ru.olegry.picloader.internal.models

import android.graphics.drawable.Drawable
import android.widget.ImageView

/**
 * A model which contains all options to perform image loading
 *
 * @property url image url which should be loaded
 * @property options options for loading image
 */
internal data class Request(
    val url: String,
    val options: LoadOptions
)

/**
 * Options for loading image
 *
 * @property placeholder optional placeholder which will be displaying while loading in progress
 * @property hasBorder if set to true, then loading progress will be shown
 * @property size a model which provides result sizes for image
 */
internal data class LoadOptions(
    val placeholder: Drawable?,
    val hasBorder: Boolean,
    val size: Size
)

/**
 * A wrapper model for providing result sizes of loading image
 *
 * @property with result width
 * @property height result height
 */
internal data class Size(
    val width: Int,
    val height: Int
) {

    /**
     * Checks if this object has no [width] or [height]
     */
    fun isEmpty(): Boolean = width <= 0 || height <= 0

    companion object {

        /**
         * Creates an instance of [Size] with width and height of passed [imageView]
         */
        @JvmStatic
        fun fitToView(imageView: ImageView) = with(imageView) {
            Size(width, height)
        }
    }
}