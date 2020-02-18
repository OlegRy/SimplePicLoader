package ru.olegry.picloader.api

import android.content.Context
import android.graphics.drawable.Drawable
import android.widget.ImageView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import ru.olegry.picloader.internal.ImageDisplay
import ru.olegry.picloader.internal.LoadOptions
import ru.olegry.picloader.internal.Request
import ru.olegry.picloader.internal.Size

/**
 *
 *
 * @author Олег Рябцев
 */
class RequestBuilder(private val url: String) {

    private var placeholderResId: Int = 0
    private var placeholder: Drawable? = null
    private var width: Int = 0
    private var height: Int = 0
    private var hasBorder: Boolean = false

    fun placeholder(@DrawableRes placeholder: Int) = apply {
        this.placeholderResId = placeholder
        if (this.placeholder != null) {
            this.placeholder = null
        }
    }

    fun placeholder(placeholder: Drawable?) = apply {
        this.placeholder = placeholder
        if (this.placeholderResId != 0) {
            this.placeholderResId = 0
        }
    }

    fun crop(width: Int = 0, height: Int = 0) = apply {
        this.width = width
        this.height = height
    }

    fun border() = apply {
        hasBorder = true
    }

    fun noBorder() = apply {
        hasBorder = false
    }

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