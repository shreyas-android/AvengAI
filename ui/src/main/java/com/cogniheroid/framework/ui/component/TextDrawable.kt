package com.cogniheroid.framework.ui.component

import android.graphics.Canvas
import android.graphics.Color
import android.graphics.ColorFilter
import android.graphics.Paint
import android.graphics.PixelFormat
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.Typeface
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape
import java.util.Arrays
import java.util.Locale
import kotlin.random.Random

class TextDrawable private constructor(builder: Builder) : ShapeDrawable(builder.shape) {
    private val textPaint: Paint
    private val borderPaint: Paint
    private val text: String
    private val color: Int
    private val shape: RectShape
    private val height: Int
    private val width: Int
    private val fontSize: Int
    private val radius: Float
    private val borderThickness: Float
    private fun getDarkerShade(color: Int): Int {
        return Color.rgb((SHADE_FACTOR * Color.red(color)).toInt(),
                (SHADE_FACTOR * Color.green(color)).toInt(),
                (SHADE_FACTOR * Color.blue(color)).toInt())
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val r: Rect = bounds


        // draw border
        if (borderThickness > 0) {
            drawBorder(canvas)
        }
        val count: Int = canvas.save()
        canvas.translate(r.left.toFloat(), r.top.toFloat())

        // draw text
        val width = if (width < 0) r.width() else width
        val height = if (height < 0) r.height() else height
        val fontSize = (if (fontSize < 0) Math.min(width, height) / 2 else fontSize).toFloat()
        textPaint.textSize = fontSize
        canvas.drawText(text, (width / 2).toFloat(), height / 2 - (textPaint.descent() + textPaint.ascent()) / 2, textPaint)
        canvas.restoreToCount(count)
    }

    private fun drawBorder(canvas: Canvas) {
        val rect = RectF(bounds)
        rect.inset(borderThickness / 2.toFloat(), borderThickness / 2.toFloat())
        when (shape) {
            is OvalShape -> {
                canvas.drawOval(rect, borderPaint)
            }

            is RoundRectShape -> {
                canvas.drawRoundRect(rect, radius, radius, borderPaint)
            }

            else -> {
                canvas.drawRect(rect, borderPaint)
            }
        }
    }

    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        textPaint.setColorFilter(cf)
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicWidth(): Int {
        return width
    }

    override fun getIntrinsicHeight(): Int {
        return height
    }

    class Builder : IConfigBuilder, IShapeBuilder, IBuilder {
        var text = ""
        var color: Int
        var borderThickness: Float
        var width: Int
        var height: Int
        var font: Typeface?
        var shape: RectShape
        var textColor: Int
        var fontSize: Int
        var isBold: Boolean
        var toUpperCase: Boolean
        var radius = 0f
        override fun width(width: Int): IConfigBuilder {
            this.width = width
            return this
        }

        override fun height(height: Int): IConfigBuilder {
            this.height = height
            return this
        }

        override fun textColor(color: Int): IConfigBuilder {
            textColor = color
            return this
        }

        override fun withBorder(thickness: Float): IConfigBuilder {
            borderThickness = thickness
            return this
        }

        override fun useFont(font: Typeface?): IConfigBuilder {
            this.font = font
            return this
        }

        override fun fontSize(size: Int): IConfigBuilder {
            fontSize = size
            return this
        }

        override fun bold(): IConfigBuilder {
            isBold = true
            return this
        }

        override fun toUpperCase(): IConfigBuilder {
            toUpperCase = true
            return this
        }

        override fun beginConfig(): IConfigBuilder {
            return this
        }

        override fun endConfig(): IShapeBuilder {
            return this
        }

        override fun rect(): IBuilder {
            shape = RectShape()
            return this
        }

        override fun round(): IBuilder {
            shape = OvalShape()
            return this
        }

        override fun roundRect(radius: Int): IBuilder {
            this.radius = radius.toFloat()
            val radii = floatArrayOf(radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat(), radius.toFloat())
            shape = RoundRectShape(radii, null, null)
            return this
        }

        override fun buildRect(text: String, color: Int): TextDrawable {
            rect()
            return build(text, color)
        }

        override fun buildRoundRect(text: String, color: Int, radius: Int): TextDrawable {
            roundRect(radius)
            return build(text, color)
        }

        override fun buildRound(text: String, color: Int): TextDrawable {
            round()
            return build(text, color)
        }

        override fun build(text: String, color: Int): TextDrawable {
            this.color = color
            this.text = text
            return TextDrawable(this)
        }

        init {
            color = Color.GRAY
            textColor = Color.WHITE
            borderThickness = 0f
            width = -1
            height = -1
            shape = RectShape()
            font = Typeface.create("sans-serif-light", Typeface.NORMAL)
            fontSize = -1
            isBold = false
            toUpperCase = false
        }
    }

    interface IConfigBuilder {
        fun width(width: Int): IConfigBuilder?
        fun height(height: Int): IConfigBuilder?
        fun textColor(color: Int): IConfigBuilder?
        fun withBorder(thickness: Float): IConfigBuilder?
        fun useFont(font: Typeface?): IConfigBuilder?
        fun fontSize(size: Int): IConfigBuilder?
        fun bold(): IConfigBuilder?
        fun toUpperCase(): IConfigBuilder?
        fun endConfig(): IShapeBuilder?
    }

    interface IBuilder {
        fun build(text: String, color: Int): TextDrawable
    }

    interface IShapeBuilder {
        fun beginConfig(): IConfigBuilder?
        fun rect(): IBuilder?
        fun round(): IBuilder?
        fun roundRect(radius: Int): IBuilder?
        fun buildRect(text: String, color: Int): TextDrawable
        fun buildRoundRect(text: String, color: Int, radius: Int): TextDrawable
        fun buildRound(text: String, color: Int): TextDrawable
    }

    companion object {
        private const val SHADE_FACTOR = 0.9f
        fun builder(): IShapeBuilder {
            return Builder()
        }
    }

    init {

        // shape properties
        shape = builder.shape
        height = builder.height
        width = builder.width
        radius = builder.radius

        // text and color
        text = if (builder.toUpperCase) builder.text.uppercase(Locale.getDefault()) else builder.text
        color = builder.color

        // text paint settings
        fontSize = builder.fontSize
        textPaint = Paint()
        textPaint.color = builder.textColor
        textPaint.isAntiAlias = true
        textPaint.isFakeBoldText = builder.isBold
        textPaint.style = Paint.Style.FILL
        textPaint.setTypeface(builder.font)
        textPaint.textAlign = Paint.Align.CENTER
        textPaint.strokeWidth = builder.borderThickness

        // border paint settings
        borderThickness = builder.borderThickness
        borderPaint = Paint()
        borderPaint.color = getDarkerShade(color)
        borderPaint.style = Paint.Style.STROKE
        borderPaint.strokeWidth = borderThickness

        // drawable paint color
        val paint: Paint = paint
        paint.color = color
    }
}


class ColorGenerator private constructor(private val mColors: List<Int>) {
    companion object {
        var DEFAULT: ColorGenerator
        var MATERIAL: ColorGenerator
        fun create(colorList: List<Int>): ColorGenerator {
            return ColorGenerator(colorList)
        }

        init {
            DEFAULT = create(Arrays.asList(
                    -0xe9c9c,
                    -0xa7aa7,
                    -0x65bc2,
                    -0x1b39d2,
                    -0x98408c,
                    -0xa65d42,
                    -0xdf6c33,
                    -0x529d59,
                    -0x7fa87f
            ))
            MATERIAL = create(
                Arrays.asList(
                    -0x1a8c8d,
                    -0xf9d6e,
                    -0x459738,
                    -0x6a8a33,
                    -0x867935,
                    -0x9b4a0a,
                    -0xb03c09,
                    -0xb22f1f,
                    -0xb24954,
                    -0x7e387c,
                    -0x512a7f,
                    -0x759b,
                    -0x2b1ea9,
                    -0x2ab1,
                    -0x48b3,
                    -0x5e7781,
                    -0x6f5b52
            ))
        }
    }

    private val mRandom: Random = Random(System.currentTimeMillis())
    val randomColor: Int
        get() = mColors[mRandom.nextInt(mColors.size)]

    fun getColor(key: Any): Int {
        return mColors[Math.abs(key.hashCode()) % mColors.size]
    }

}