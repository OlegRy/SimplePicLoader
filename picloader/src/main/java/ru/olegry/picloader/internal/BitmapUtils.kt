package ru.olegry.picloader.internal

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.BufferedInputStream
import java.io.InputStream

/**
 *
 *
 * @author Олег Рябцев
 */
internal fun decodeImageByteArray(imageArray: InputStream, loadOptions: LoadOptions): Optional<Bitmap> {
    return BitmapFactory.Options().run {
        val inputStream = BufferedInputStream(imageArray)
        inputStream.mark(inputStream.available())
        inJustDecodeBounds = true
        BitmapFactory.decodeStream(inputStream, null, this)
        // BitmapFactory.decodeByteArray(imageArray, 0, imageArray.size, this)
        inSampleSize = calculateInSampleSize(this, loadOptions)
        inputStream.reset()
        inJustDecodeBounds = false
        Optional(BitmapFactory.decodeStream(inputStream, null, this))
        // BitmapFactory.decodeByteArray(inputStream, 0, imageArray.size, this) ?: throw IllegalStateException()
    }
}

private fun calculateInSampleSize(options: BitmapFactory.Options, loadOptions: LoadOptions): Int {
    var inSampleSize = 1
    val (width, height) = options.run { outWidth to outHeight }
    val (reqWidth, reqHeight) = if (!loadOptions.size.isEmpty()) {
        loadOptions.size.width to loadOptions.size.height
    } else {
        width to height
    }
    if (width > reqWidth || height > reqHeight) {
        val halfWidth = width / 2
        val halfHeight = height / 2
        while (halfWidth / inSampleSize >= reqWidth && halfHeight / inSampleSize >= reqHeight) {
            inSampleSize *= 2
        }
    }
    return inSampleSize
}

internal data class Optional<T>(
    val value: T?
)