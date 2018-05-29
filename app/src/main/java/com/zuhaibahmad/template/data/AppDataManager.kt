package com.zuhaibahmad.template.data

import android.content.Context
import com.zuhaibahmad.template.data.local.db.DbHelper
import com.zuhaibahmad.template.data.local.prefs.PreferencesHelper
import com.zuhaibahmad.template.data.remote.ApiHelper
import com.zuhaibahmad.template.utils.OpenClass
import javax.inject.Inject
import javax.inject.Singleton


/**
 * Created by Zuhaib on 9/16/2017.
 *
 * A centralized object for management of data throughout application from various sources
 */

@OpenClass
@Singleton

class AppDataManager
@Inject constructor(
	private val context: Context,
	private val dbHelper: DbHelper,
	private val preferencesHelper: PreferencesHelper,
	private val apiHelper: ApiHelper
) : DataManager {

	override val isUserOnline
		get() = apiHelper.isUserOnline

	override var isUserSignedIn = preferencesHelper.isUserSignedIn
}
