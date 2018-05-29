package com.zuhaibahmad.template.data.local.prefs

import android.content.Context
import com.zuhaibahmad.template.di.scopes.PreferenceScope
import javax.inject.Inject

/**
 * Created by Zuhaib Ahmad on 07/07/17.
 */

class AppPreferencesHelper
@Inject constructor(
	context: Context,
	@PreferenceScope prefFileName: String
) : PreferencesHelper {

	private val mPrefs = context.getSharedPreferences(prefFileName, Context.MODE_PRIVATE)

	override var isUserSignedIn: Boolean
		get() = mPrefs.getBoolean("isUserSignedIn", false)
		set(value) = mPrefs.edit().putBoolean("isUserSignedIn", value).apply()
}
