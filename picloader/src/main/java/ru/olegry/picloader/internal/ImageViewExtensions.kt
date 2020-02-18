package ru.olegry.picloader.internal

import android.widget.ImageView

/**
 *
 *
 * @author Олег Рябцев
 */
fun ImageView.updateProgress(progress: Int) {
    val currentDrawable = drawable
    if (currentDrawable is BorderDrawable) {
        currentDrawable.updateAngle(progress)
    }
}