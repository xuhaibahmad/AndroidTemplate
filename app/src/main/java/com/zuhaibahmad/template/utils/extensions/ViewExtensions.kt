package com.zuhaibahmad.template.utils.extensions

import android.graphics.Typeface
import android.support.annotation.LayoutRes
import android.text.Spannable
import android.text.SpannableString
import android.view.LayoutInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import com.zuhaibahmad.template.utils.CustomTypefaceSpan

/**
 * Created by zuhaib.ahmad on 1/8/2018.
 */

fun View.isDisplayed(): Boolean = this.visibility == View.VISIBLE

fun MenuItem.setFont(typeface: Typeface) {
	val span = CustomTypefaceSpan("", typeface)
	title = SpannableString(title).apply {
		setSpan(span, 0, this.length, Spannable.SPAN_INCLUSIVE_INCLUSIVE)
	}

}

fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
	return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun <T : View> View.findViewsByIds(vararg resIds: Int) = resIds.map { this.findViewById<T>(it) }
