package com.zuhaibahmad.template.utils

import android.content.Context
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

/**
 * Created by Zuhaib Ahmad on 1/10/2017.
 *
 *
 * View pager used for a finite, low number of pages, where there is no need for optimization.
 */

class StaticViewPager : ViewPager {
	/**
	 * Initialize the view.
	 *
	 * @param context The application context.
	 */
	constructor(context: Context) : super(context)

	/**
	 * Initialize the view.
	 *
	 * @param context The application context.
	 * @param attrs   The requested attributes.
	 */
	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

	override fun onAttachedToWindow() {
		super.onAttachedToWindow()

		// Make sure all are loaded at once
		val childrenCount = childCount
		offscreenPageLimit = childrenCount - 1

		// Attach the adapter
		adapter = object : PagerAdapter() {

			override fun instantiateItem(container: ViewGroup, pos: Int) =
				container.getChildAt(pos)

			override fun isViewFromObject(arg0: View, arg1: Any) = arg0 === arg1

			override fun getCount() = childrenCount

			override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {}
		}
	}
}
