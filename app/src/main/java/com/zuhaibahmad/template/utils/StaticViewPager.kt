/*
 * Copyright (C) 2017 Zuhaib Ahmad
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
