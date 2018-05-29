package com.zuhaibahmad.template.utils.extensions

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.net.ConnectivityManager
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.text.TextUtils
import android.util.Log
import android.view.ViewGroup
import com.zuhaibahmad.template.AppConstants
import com.zuhaibahmad.template.R
import java.util.*


/**
 * Created by zuhaib.ahmad on 2/14/2018.
 */

private var uniqueID: String? = null
private const val PREF_UNIQUE_ID = "PREF_UNIQUE_ID"

val Context.deviceId: String
	get() {
		if (uniqueID == null) {
			val sharedPrefs = getSharedPreferences(PREF_UNIQUE_ID, Context.MODE_PRIVATE)
			uniqueID = sharedPrefs.getString(PREF_UNIQUE_ID, null)

			if (uniqueID == null) {
				uniqueID = UUID.randomUUID().toString()
				sharedPrefs.edit().putString(PREF_UNIQUE_ID, uniqueID).apply()
			}
		}

		return "${uniqueID}-android"
	}


fun Context.displayMessage(
	container: ViewGroup, message: String,
	length: Int = Snackbar.LENGTH_SHORT
) =
	Snackbar.make(container, message, length).show()


//Check if network is online
@SuppressLint("MissingPermission")
val isNetworkOnline = fun Context.(): Boolean {
	val manager = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
	val netInfo = manager.activeNetworkInfo
	return netInfo != null && netInfo.isConnectedOrConnecting
}


fun Context.validate(
	container: ViewGroup,
	value: Pair<String, String> = Pair("", "value"),
	validation: (text: String) -> Boolean,
	message: String = "Please provide a valid ${value.second}!"
) = if (!validation(value.first)) {
	displayMessage(container, message)
	false
} else {
	true
}


fun Context.validate(
	container: ViewGroup, validationResult: Boolean,
	valueName: String? = "value", message: String? = null
): Boolean {
	return if (!validationResult) {
		val msg = if (TextUtils.isEmpty(message)) "Please provide a valid $valueName!" else message
		displayMessage(container, msg!!)
		false
	} else {
		true
	}
}

fun Context.readFile(id: Int) = resources.openRawResource(id).reader().readText()


fun Context.isValidEmail(email: String) =
	!TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()

fun Context.doIfNetworkConnected(operation: () -> Unit) {
	if (isNetworkOnline()) {
		operation()
	} else {
		// Notify user about no connection
		displayAlert(str(R.string.error_no_network), str(R.string.err_msg_no_network))
	}
}

val str = fun Context.(resId: Int): String = getString(resId)

val drawable = fun Context.(resId: Int): Drawable? = ContextCompat.getDrawable(this, resId)

val color = fun Context.(resId: Int): Int = ContextCompat.getColor(this, resId)

val font = fun Context.(resId: Int): Typeface? = ResourcesCompat.getFont(this, resId)

fun Context.displayShareDialog(message: String) {
	val sendIntent = Intent()
	sendIntent.action = Intent.ACTION_SEND
	sendIntent.putExtra(Intent.EXTRA_TEXT, message)
	sendIntent.type = "text/plain"
	startActivity(sendIntent)
}

fun Context.log(message: String) = e(message)
fun Context.d(message: String) = Log.d(AppConstants.TAG, message)
fun Context.e(message: String) = Log.e(AppConstants.TAG, message)
fun Context.i(message: String) = Log.i(AppConstants.TAG, message)
fun Context.v(message: String) = Log.v(AppConstants.TAG, message)
fun Context.w(message: String) = Log.w(AppConstants.TAG, message)
fun Context.wtf(message: String) = Log.wtf(AppConstants.TAG, message)
fun Context.logError(msg: String, e: Throwable? = null) =
	Log.e(AppConstants.TAG, msg, e)

