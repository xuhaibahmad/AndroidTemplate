package com.zuhaibahmad.template


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
