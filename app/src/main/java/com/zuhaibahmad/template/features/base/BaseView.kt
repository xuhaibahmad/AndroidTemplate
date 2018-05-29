package com.zuhaibahmad.template.features.base

/**
 * Created by zuhaib.ahmad on 12/27/2017.
 */

interface BaseView {
	fun displayMessage(resId: Int = 0, message: String = ""): Any
}
