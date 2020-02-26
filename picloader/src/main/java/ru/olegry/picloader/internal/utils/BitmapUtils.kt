package ru.olegry.picloader.internal.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import ru.olegry.picloader.internal.models.LoadOptions
import java.io.BufferedInputStream
import java.io.InputStream

/**
 * Converts passed [InputStream] to [Bitmap] using [loadOptions]
 */
internal fun decodeImageInputStream(inputStream: InputStream, loadOptions: LoadOptions): Optional<Bitmap> {
    return BitmapFactory.Options().run {
        val bufferedInputStream = BufferedInputStream(inputStream)
        bufferedInputStream.mark(bufferedInputStream.available())
        inJustDecodeBounds = true
        BitmapFactory.decodeStream(bufferedInputStream, null, this)
        inSampleSize = calculateInSampleSize(this, loadOptions)
        bufferedInputStream.reset()
        inJustDecodeBounds = false
        Optional(
            BitmapFactory.decodeStream(
                bufferedInputStream,
                null,
                this
            )
        )
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