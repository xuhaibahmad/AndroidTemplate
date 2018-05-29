package com.zuhaibahmad.template.utils.extensions

import android.os.Bundle
import android.os.Parcelable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction

/**
 * Created by zuhaib.ahmad on 1/31/2018.
 */

fun Fragment.withParcelableArgument(key: String, value: Parcelable) = this.apply {
	arguments = Bundle().apply { putParcelable(key, value) }
}

fun FragmentTransaction.openOrReplaceFragment(id: Int, isChild: Boolean, f: Fragment, tag: String) =
	(if (isChild) add(id, f, tag) else replace(id, f, tag))!!
