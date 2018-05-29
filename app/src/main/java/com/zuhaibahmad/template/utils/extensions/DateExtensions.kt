package com.zuhaibahmad.template.utils.extensions

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by zuhaib.ahmad on 2/22/2018.
 */

const val DEFAULT_SOURCE_FORMAT: String = "yyyyy-mmm-dd hh:mm:ss"

fun String.formatDate(srcFormat: String = DEFAULT_SOURCE_FORMAT, dstFormat: String): String {
	val parsed = SimpleDateFormat(srcFormat, Locale.getDefault()).parse(this)
	return SimpleDateFormat(dstFormat, Locale.getDefault()).format(parsed)
}
