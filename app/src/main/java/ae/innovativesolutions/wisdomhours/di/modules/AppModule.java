package ae.innovativesolutions.wisdomhours.di.modules;

import android.content.Context;

import javax.inject.Singleton;

import ae.innovativesolutions.wisdomhours.App;
import ae.innovativesolutions.wisdomhours.data.AppDataManager;
import ae.innovativesolutions.wisdomhours.data.DataManager;
import ae.innovativesolutions.wisdomhours.data.local.db.AppDatabase;
import ae.innovativesolutions.wisdomhours.data.local.db.AppDbHelper;
import ae.innovativesolutions.wisdomhours.data.local.db.DbHelper;
import ae.innovativesolutions.wisdomhours.data.local.prefs.AppPreferencesHelper;
import ae.innovativesolutions.wisdomhours.data.local.prefs.PreferencesHelper;
import ae.innovativesolutions.wisdomhours.data.remote.ApiHelper;
import ae.innovativesolutions.wisdomhours.data.remote.AppApiHelper;
import ae.innovativesolutions.wisdomhours.di.scopes.ApiScope;
import ae.innovativesolutions.wisdomhours.di.scopes.DatabaseScope;
import ae.innovativesolutions.wisdomhours.di.scopes.PreferenceScope;
import ae.innovativesolutions.wisdomhours.utils.AppConstants;
import ae.innovativesolutions.wisdomhours.utils.rx.AppSchedulerProvider;
import ae.innovativesolutions.wisdomhours.utils.rx.SchedulerProvider;
import dagger.Module;
import dagger.Provides;

@Module
public final class AppModule {

	@Provides
	public Context provideAppContext(App application) {
		return application;
	}

	@Provides
	SchedulerProvider provideSchedulerProvider() {
		return new AppSchedulerProvider();
	}

	@Provides
	@DatabaseScope
	String provideDatabaseName() {
		return AppConstants.DB_NAME;
	}

	@Provides
	@ApiScope
	String provideApiKey() {
		return "";
	}

	@Provides
	@PreferenceScope
	String providePreferenceName() {
		return AppConstants.PREFERENCES_NAME;
	}

	@Provides
	@Singleton
	DataManager provideDataManager(AppDataManager appDataManager) {
		return appDataManager;
	}

	@Provides
	@Singleton
	AppDatabase provideAppDatabase(@DatabaseScope String dbName, Context context) {
		return new AppDatabase();
	}

	@Provides
	@Singleton
	DbHelper provideDbHelper(AppDbHelper appDbHelper) {
		return appDbHelper;
	}

	@Provides
	@Singleton
	PreferencesHelper providePreferencesHelper(AppPreferencesHelper appPreferencesHelper) {
		return appPreferencesHelper;
	}

	@Provides
	@Singleton
	ApiHelper provideApiHelper(AppApiHelper appApiHelper) {
		return appApiHelper;
	}
}
