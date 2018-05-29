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

package com.zuhaibahmad.template

import com.zuhaibahmad.template.App
import com.zuhaibahmad.template.BuildConfig


/**
 * Created by Zuhaib on 4/5/2016.
 *
 *
 * Class carrying all the constant values used throughout the application
 */
object AppConstants {
	// @formatter:off

	var TEST_BUILD                                  = true
	var IS_UNIT_TEST                                = false
	var ADS_ENABLED                                 = !BuildConfig.DEBUG

	val TAG 									    = App::class.java.simpleName
	const val DIRECTORY_NAME 						= "App"
	const val PREFERENCES_NAME 						= "$DIRECTORY_NAME.prefs"
	const val DATABASE_NAME 						= "$DIRECTORY_NAME.db"
	const val LOG_FILE_NAME 						= "${DIRECTORY_NAME}_Log.txt"
	const val DATABASE_VERSION 						= 2

	const val SPLASH_TIME_OUT 						= 3000
	const val TOOLBAR_ICON_SIZE                     = 18

	const val REQUEST_PERMISSION_ALL 				= 0

	private const val PROD_DELAY                    = 4L
	private const val TEST_DELAY                    = 0L
	val ANIMATION_DELAY                             = if (TEST_BUILD) TEST_DELAY else PROD_DELAY
	// @formatter:on
}
