package ru.olegry.picloader.internal

import android.widget.ImageView

/**
 *
 *
 * @author Олег Рябцев
 */
internal class ImageDisplay(private val imageView: ImageView) {

    private val model: ImageDisplayModel = ImageDisplayModel()

    init {
        subscribeForChanges()
    }

    fun loadForDisplay(request: Request) {
        model.load(request)
    }

    private fun subscribeForChanges() = with(model) {
        displayObservable.subscribe(imageView::setImageBitmap)
        placeholderObservable.subscribe(imageView::setImageDrawable)
        placeholderProgressObservable.subscribe(imageView::updateProgress)
    }
}