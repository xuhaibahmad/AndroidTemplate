package ae.innovativesolutions.wisdomhours;

import android.app.Activity;
import android.app.Application;
import android.util.Log;

import com.joanzapata.iconify.Iconify;
import com.joanzapata.iconify.fonts.FontAwesomeModule;
import com.squareup.leakcanary.LeakCanary;

import javax.inject.Inject;

import ae.innovativesolutions.wisdomhours.di.components.DaggerAppComponent;
import ae.innovativesolutions.wisdomhours.utils.AppConstants;
import ae.innovativesolutions.wisdomhours.utils.AppLogger;
import ae.innovativesolutions.wisdomhours.utils.Utils;
import dagger.android.AndroidInjector;
import dagger.android.DispatchingAndroidInjector;
import dagger.android.HasActivityInjector;

public final class App extends Application implements HasActivityInjector {

	@Inject DispatchingAndroidInjector<Activity> dispatchingAndroidInjector;

	@Override
	public void onCreate() {
		super.onCreate();

		DaggerAppComponent
				.builder()
				.application(this)
				.build()
				.inject(this);

		AppLogger.init();

		if (BuildConfig.DEBUG && !AppConstants.TEST_BUILD) {
			initLeakCanary();                                           // Initialize Leak Canary
			setupGlobalExceptionHandler();                              // Global exception handling
		}

		Iconify.with(new FontAwesomeModule());
	}
	/**
	 * Sets up a global exception handler that logs the stacktrace to a log file
	 */
	private void setupGlobalExceptionHandler() {
		// Set up global exception handler
		Thread.setDefaultUncaughtExceptionHandler((thread, e) -> {
			Utils.appendLog(Log.getStackTraceString(e), AppConstants.LOG_FILE_NAME);
			Log.e(AppConstants.TAG, "ERROR! " + e.toString(), e);
		});
	}

	/**
	 * Initializes the Leak Canary library
	 */
	private void initLeakCanary() {
		if (LeakCanary.isInAnalyzerProcess(this)) {
			// This process is dedicated to LeakCanary for heap analysis.
			// You should not init your app in this process.
			return;
		}

		LeakCanary.install(this);
	}

	@Override
	public AndroidInjector<Activity> activityInjector() {
		return dispatchingAndroidInjector;
	}
}