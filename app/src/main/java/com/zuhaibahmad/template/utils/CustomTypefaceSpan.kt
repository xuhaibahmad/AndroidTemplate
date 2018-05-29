package com.zuhaibahmad.template.utils

import android.graphics.Paint
import android.graphics.Typeface
import android.text.TextPaint
import android.text.style.TypefaceSpan

/**
 * Created by zuhaib.ahmad on 1/18/2018.
 */

const val DEFAULT_SKEW_X = -0.25f

class CustomTypefaceSpan(family: String, private val newType: Typeface) : TypefaceSpan(family) {

	override fun updateDrawState(ds: TextPaint) = applyCustomTypeFace(ds, newType)

	override fun updateMeasureState(paint: TextPaint) = applyCustomTypeFace(paint, newType)

	private fun applyCustomTypeFace(paint: Paint, tf: Typeface) {
		val old = paint.typeface
		val oldStyle: Int = old?.style ?: 0

		val fake = oldStyle and tf.style.inv()
		if (fake and Typeface.BOLD != 0) {
			paint.isFakeBoldText = true
		}

		if (fake and Typeface.ITALIC != 0) {
			paint.textSkewX = DEFAULT_SKEW_X
		}

		paint.typeface = tf
	}
}
