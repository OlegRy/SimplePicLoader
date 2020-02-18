package ru.olegry.picloader.internal

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.RectF
import android.graphics.drawable.Drawable

/**
 *
 *
 * @author Олег Рябцев
 */
class BorderDrawable(
    private val mainDrawable: Drawable
) : Drawable() {

    private val borderPaint = Paint().apply {
        isAntiAlias = true
        color = Color.BLUE
        strokeWidth = 20f
        style = Paint.Style.STROKE
    }

    private var arcRectF: RectF? = null
    private var currentAngle = 0f

    override fun draw(canvas: Canvas) {
        mainDrawable.setBounds(
            (borderPaint.strokeWidth * 4).toInt(),
            (borderPaint.strokeWidth * 4).toInt(),
            bounds.width() - (borderPaint.strokeWidth * 4).toInt(),
            bounds.height() - (borderPaint.strokeWidth * 4).toInt()
        )
        mainDrawable.draw(canvas)
        drawBorder(canvas)
    }

    override fun setAlpha(alpha: Int) {
        mainDrawable.alpha = alpha
    }

    override fun getOpacity(): Int = mainDrawable.opacity

    override fun setColorFilter(colorFilter: ColorFilter?) {
        mainDrawable.colorFilter = colorFilter
    }

    fun updateAngle(progress: Int) {
        currentAngle = 360 / 100f * progress
        invalidateSelf()
    }

    private fun drawBorder(canvas: Canvas) {
        val width = bounds.width()
        val height = bounds.height()
        if (arcRectF == null) {
            arcRectF = RectF(borderPaint.strokeWidth, borderPaint.strokeWidth, width.toFloat() - borderPaint.strokeWidth, height.toFloat() - borderPaint.strokeWidth)
        }
        canvas.drawArc(arcRectF!!, 270f, currentAngle, false, borderPaint)
    }
}