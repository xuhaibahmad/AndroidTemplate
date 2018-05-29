package com.zuhaibahmad.template.utils.extensions

import android.graphics.PorterDuff.Mode.SRC_IN
import android.support.design.widget.FloatingActionButton
import android.support.v4.content.ContextCompat.getColor
import com.zuhaibahmad.template.R

/**
 * Created by Zuhaib on 12/17/2017.
 */


fun FloatingActionButton.changeIcon(resId: Int) = with(this) {
	show()
	setImageResource(resId)
	drawable.setColorFilter(getColor(context, R.color.color_window_background), SRC_IN)
}
