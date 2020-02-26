package ru.olegry.picloader.internal.core

import android.widget.ImageView
import ru.olegry.picloader.internal.extensions.updateProgress
import ru.olegry.picloader.internal.models.Request

/**
 * A "View" class which starts loading logic. It holds references
 * for [ImageView] and [ImageDisplayModel] and serves as a connector
 * between them
 *
 * @property imageView an [ImageView] where loaded image will be displayed
 * @author Oleg Ryabtsev
 */
internal class ImageDisplay(private val imageView: ImageView) {

    private val model: ImageDisplayModel =
        ImageDisplayModel()

    init {
        subscribeForChanges()
    }

    /**
     * Starts loading image by passed [request]
     */
    fun loadForDisplay(request: Request) {
        model.load(request)
    }

    private fun subscribeForChanges() = with(model) {
        displayObservable.subscribe(imageView::setImageBitmap)
        placeholderObservable.subscribe(imageView::setImageDrawable)
        placeholderProgressObservable.subscribe(imageView::updateProgress)
    }
}