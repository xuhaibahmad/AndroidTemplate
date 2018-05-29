package com.zuhaibahmad.template.utils.extensions

import android.content.Context
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.inputmethod.InputMethodManager
import com.zuhaibahmad.template.R


/**
 * Created by Zuhaib on 9/16/2017.
 *
 * Base context with extension functions
 */

fun AppCompatActivity.showHomeAsUp() = supportActionBar?.setDisplayHomeAsUpEnabled(true)

fun AppCompatActivity.hideHomeAsUp() = supportActionBar?.setDisplayHomeAsUpEnabled(false)

fun AppCompatActivity.hideToolbar() = supportActionBar?.hide()

fun AppCompatActivity.showToolbar() = supportActionBar?.show()

fun AppCompatActivity.hideKeyboard() {
	if (currentFocus != null) {
		val im = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
		im.hideSoftInputFromWindow(currentFocus.windowToken, InputMethodManager.HIDE_NOT_ALWAYS)
	}
}

fun AppCompatActivity.hideKeyboardAndDo(operation: () -> Unit) {
	hideKeyboard()
	operation()
}

/*
fun Fragment.replaceWithTag(id: Int, tag: String, show: Boolean = false) = activity
		?.supportFragmentManager
		?.beginTransaction()
		?.addAnimations(R.anim.translate_right_in, R.anim.translate_right_out)
		?.replace(id, this, tag)
		?.noBackStack(show)
		?.commit()
*/

fun FragmentTransaction.noBackStack(b: Boolean): FragmentTransaction =
	if (b) addToBackStack(null) else this

fun FragmentTransaction.addAnimations(enter: Int, exit: Int): FragmentTransaction =
	setCustomAnimations(enter, exit, enter, exit)

fun FragmentTransaction.animateRightToRight() =
	addAnimations(R.anim.translate_right_in, R.anim.translate_right_out)

fun FragmentTransaction.animateLeftToLeft() =
	addAnimations(R.anim.translate_left_in, R.anim.translate_left_out)

fun FragmentTransaction.animateRightToLeft() =
	addAnimations(R.anim.translate_right_in, R.anim.translate_left_out)

fun FragmentTransaction.animateLeftToRight() =
	addAnimations(R.anim.translate_left_in, R.anim.translate_right_out)

fun FragmentTransaction.animateTopToTop() =
	addAnimations(R.anim.translate_top_in, R.anim.translate_top_out)

fun FragmentTransaction.animateBottomToBottom() =
	addAnimations(R.anim.translate_bottom_in, R.anim.translate_bottom_out)

fun FragmentTransaction.animateBottomToTop() =
	addAnimations(R.anim.translate_top_in, R.anim.translate_bottom_out)

fun FragmentTransaction.animateTopToBottom() =
	addAnimations(R.anim.translate_bottom_in, R.anim.translate_top_out)
