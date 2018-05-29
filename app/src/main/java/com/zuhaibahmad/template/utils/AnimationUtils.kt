@file:Suppress("unused")

package com.zuhaibahmad.template.utils

import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.animation.TranslateAnimation
import org.jetbrains.anko.runOnUiThread

private const val ANIMATION_DURATION = 500L

/**
 * Created by Zuhaib on 4/6/2016.
 */

// To animate view slide out from left to right
fun View.slideToRight(isVisible: Boolean = true) = context.runOnUiThread {
	startAnimation(TranslateAnimation(0f, width.toFloat(), 0f, 0f).apply {
		duration = ANIMATION_DURATION
		fillAfter = true
	})
	visibility = if (isVisible) VISIBLE else GONE
}

// To animate view slide out from right to left
fun View.slideToLeft(isVisible: Boolean = true) = context.runOnUiThread {
	startAnimation(TranslateAnimation(0f, (-width).toFloat(), 0f, 0f).apply {
		duration = ANIMATION_DURATION
		fillAfter = true
	})
	visibility = if (isVisible) VISIBLE else GONE
}

// To animate view slide out from top to bottom
fun View.slideToBottom(isVisible: Boolean = true) = context.runOnUiThread {
	startAnimation(TranslateAnimation(0f, 0f, 0f, height.toFloat()).apply {
		duration = ANIMATION_DURATION
		fillAfter = true
	})
	visibility = if (isVisible) VISIBLE else GONE
}

// To animate view slide out from bottom to top
fun View.slideToTop(isVisible: Boolean = true) = context.runOnUiThread {
	startAnimation(TranslateAnimation(0f, 0f, 0f, (-height).toFloat()).apply {
		duration = ANIMATION_DURATION
		fillAfter = true
	})
	visibility = if (isVisible) VISIBLE else GONE
}

// To animate view slide out from bottom to top
fun View.slideFromTop(isVisible: Boolean = true) = context.runOnUiThread {
	startAnimation(TranslateAnimation(0f, 0f, (-height).toFloat(), 0f).apply {
		duration = ANIMATION_DURATION
		fillAfter = true
	})
	visibility = if (isVisible) VISIBLE else GONE
}

// To animate view slide out from left to right
fun View.slideFromRight(isVisible: Boolean = true) = context.runOnUiThread {
	startAnimation(TranslateAnimation(width.toFloat(), 0f, 0f, 0f).apply {
		duration = ANIMATION_DURATION
		fillAfter = true
	})
	visibility = if (isVisible) VISIBLE else GONE
}

// To animate view slide out from right to left
fun View.slideFromLeft(isVisible: Boolean = true) = context.runOnUiThread {
	startAnimation(TranslateAnimation((-width).toFloat(), 0f, 0f, 0f).apply {
		duration = ANIMATION_DURATION
		fillAfter = true
	})
	visibility = if (isVisible) VISIBLE else GONE
}

// To animate view slide out from top to bottom
fun View.slideFromBottom(isVisible: Boolean = true) = context.runOnUiThread {
	startAnimation(TranslateAnimation(0f, 0f, height.toFloat(), 0f).apply {
		duration = ANIMATION_DURATION
		fillAfter = true
	})
	visibility = if (isVisible) VISIBLE else GONE
}

