package com.zuhaibahmad.template.data.remote

import android.content.Context
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Zuhaib Ahmad on 07/07/17.
 */

@Singleton
class AppApiHelper @Inject constructor(val context: Context) : ApiHelper {

	override val isUserOnline
		get() = false // TODO Some condition
}
