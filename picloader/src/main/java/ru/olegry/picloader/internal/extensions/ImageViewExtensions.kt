package ru.olegry.picloader.internal.extensions

import android.widget.ImageView
import ru.olegry.picloader.internal.custom.BorderDrawable

/**
 * Convenient extension function for updating progress of loading
 */
fun ImageView.updateProgress(progress: Int) {
    val currentDrawable = drawable
    if (currentDrawable is BorderDrawable) {
        currentDrawable.updateAngle(progress)
    }
}