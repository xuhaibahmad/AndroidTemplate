package com.zuhaibahmad.template

import android.Manifest
import android.app.Activity
import android.app.Application
import android.support.v7.app.AppCompatDelegate
import android.util.Log
import com.github.moduth.blockcanary.BlockCanary
import com.joanzapata.iconify.Iconify
import com.joanzapata.iconify.fonts.FontAwesomeModule
import com.raizlabs.android.dbflow.config.DatabaseConfig
import com.raizlabs.android.dbflow.config.FlowConfig
import com.raizlabs.android.dbflow.config.FlowManager
import com.squareup.leakcanary.LeakCanary
import com.zuhaibahmad.template.database.AppDatabase
import com.zuhaibahmad.template.di.components.AppComponent
import com.zuhaibahmad.template.di.components.DaggerAppComponent
import com.zuhaibahmad.template.utils.AppLogger
import com.zuhaibahmad.template.utils.extensions.appendLog
import com.zuhaibahmad.template.utils.extensions.hasPermissions
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

/**
 * Created by Zuhaib on 1/22/2016.
 *
 *
 * Application class for global data access and management
 */
open class App : Application(), HasActivityInjector {

	@Inject lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Activity>

	lateinit var component: AppComponent

	override fun onCreate() {
		super.onCreate()

		AppLogger.e("App Version Name: ${BuildConfig.VERSION_NAME}")
		AppLogger.e("App Version Code: ${BuildConfig.VERSION_CODE}")

		performInjection()
		AppLogger.init()
		Iconify.with(FontAwesomeModule())
		initDbFlow()

		if (!AppConstants.IS_UNIT_TEST) {
			//FirebaseApp.initializeApp(this)
			initCrashReporting()
		}


		if (BuildConfig.DEBUG && !AppConstants.IS_UNIT_TEST) {
			initLeakCanary()
			initBlockCanary()
			setupGlobalExceptionHandler()
		}
	}

	private fun initCrashReporting() {
		/*val crashlyticsCore = CrashlyticsCore.Builder()
			.disabled(BuildConfig.DEBUG)
			.build()
		val crashlytics = Crashlytics.Builder()
			.core(crashlyticsCore)
			.build()
		Fabric.with(this, crashlytics)*/
	}

	private fun performInjection() {
		component = DaggerAppComponent
			.builder()
			.application(this)
			.build()
		component.inject(this)
	}

	override fun activityInjector(): AndroidInjector<Activity> = dispatchingAndroidInjector

	open fun initDbFlow() {
		val databaseConfig = DatabaseConfig
			.builder(AppDatabase::class.java)
			.databaseName("Cricketer")
			.build()
		val flowConfig = FlowConfig.builder(this).addDatabaseConfig(databaseConfig).build()
		FlowManager.init(flowConfig)
	}

	private fun initBlockCanary() {
		BlockCanary.install(this, AppBlockCanaryContext(this)).start()
	}

	private fun initLeakCanary() {
		if (LeakCanary.isInAnalyzerProcess(this)) {
			// This process is dedicated to LeakCanary for heap analysis.
			// You should not init your app in this process.
			return
		}
		LeakCanary.install(this)
	}

	/**
	 * Sets up a global exception handler that logs all the exceptions to console, a log file
	 * inside the device's local storage and optionally to Firebase Crash Reporting
	 *
	 *
	 * Requires Manifest.permission.WRITE_EXTERNAL_STORAGE permission to write to log file
	 */
	private fun setupGlobalExceptionHandler() {
		// Set up global exception handler
		val hasPermission = applicationContext.hasPermissions(Manifest.permission.WRITE_EXTERNAL_STORAGE)
		Thread.setDefaultUncaughtExceptionHandler { _, e ->
			if (hasPermission) appendLog(Log.getStackTraceString(e), AppConstants.LOG_FILE_NAME)
			e.printStackTrace()
			//if (!isIgnoredException(e)) Crashlytics.logException(e)
		}
	}

	/**
	 * Returns boolean flag indicating whether to ignore the supplied exception or not
	 * Ignores the exceptions declared inside the method body or all the exception by default
	 * when the build flavor is Debug or Demo
	 *
	 * @param e The supplied exception
	 * @return Should ignore the exception or report to Firebase Crash reporting
	 */
	private fun isIgnoredException(e: Throwable): Boolean {
		var isIgnored = false

		// Ignore all exceptions by default when it's mock version
		@Suppress("ConstantConditionIf")
		if (BuildConfig.DEBUG) isIgnored = true

		// Check for each declared ignored exception afterwards
		if (e.message.equals("No teams found", ignoreCase = true)) isIgnored = true

		return isIgnored
	}

	companion object {

		init {
			AppCompatDelegate.setCompatVectorFromResourcesEnabled(true)
		}
	}
}
