package ru.olegry.picloader.api

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import ru.olegry.picloader.internal.core.ImageDisplay
import ru.olegry.picloader.internal.models.LoadOptions
import ru.olegry.picloader.internal.models.Request
import ru.olegry.picloader.internal.models.Size

/**
 * Builder of requests for loading images. Allows you to apply
 * some options for request
 *
 * @author Oleg Ryabtsev
 */
class RequestBuilder(private val url: String) {

    private var placeholderResId: Int = 0
    private var placeholder: Drawable? = null
    private var width: Int = 0
    private var height: Int = 0
    private var hasBorder: Boolean = false

    /**
     * Applies option for displaying placeholder while image loading
     * in progress. It's more convenient way to set placeholder if you
     * need a picture from resources
     */
    fun placeholder(@DrawableRes placeholder: Int) = apply {
        this.placeholderResId = placeholder
        if (this.placeholder != null) {
            this.placeholder = null
        }
    }

    /**
     * Applies option for displaying placeholder while image loading
     * in progress. It may be convenient to use this method if you need
     * a custom drawable to be set as a placeholder
     */
    fun placeholder(placeholder: Drawable?) = apply {
        this.placeholder = placeholder
        if (this.placeholderResId != 0) {
            this.placeholderResId = 0
        }
    }

    /**
     * Applies option for resizing image. If no values was passed, width and height
     * of an [ImageView] will be used
     */
    @JvmOverloads
    fun crop(width: Int = 0, height: Int = 0) = apply {
        this.width = width
        this.height = height
    }

    /**
     * Applies an option for displaying loading progress around placeholder
     */
    fun border() = apply {
        hasBorder = true
    }

    /**
     * Turns off [border] option
     */
    fun noBorder() = apply {
        hasBorder = false
    }

    /**
     * Builds request for loading and starts it immediately using.
     * An image will be displayed in [imageView]
     */
    fun into(imageView: ImageView) {
        ImageDisplay(imageView)
            .loadForDisplay(buildRequest(imageView))
    }

    private fun buildRequest(imageView: ImageView) = Request(
        url,
        LoadOptions(
            getAppropriatePlaceholder(imageView.context),
            hasBorder,
            getAppropriateSize(imageView)
        )
    )

    private fun getAppropriatePlaceholder(context: Context): Drawable? = when {
        placeholder != null -> placeholder
        placeholderResId != 0 -> ContextCompat.getDrawable(context, placeholderResId)
        else -> null
    }

    private fun getAppropriateSize(imageView: ImageView) = if (width == 0 || height == 0) {
        Size.fitToView(imageView)
    } else {
        Size(width, height)
    }
}