package com.zuhaibahmad.template.di.modules;

import android.content.Context;

import com.zuhaibahmad.template.App;
import com.zuhaibahmad.template.AppConstants;
import com.zuhaibahmad.template.data.AppDataManager;
import com.zuhaibahmad.template.data.DataManager;
import com.zuhaibahmad.template.data.local.db.AppDbHelper;
import com.zuhaibahmad.template.data.local.db.DbHelper;
import com.zuhaibahmad.template.data.local.prefs.AppPreferencesHelper;
import com.zuhaibahmad.template.data.local.prefs.PreferencesHelper;
import com.zuhaibahmad.template.data.remote.ApiHelper;
import com.zuhaibahmad.template.data.remote.AppApiHelper;
import com.zuhaibahmad.template.database.AppDatabase;
import com.zuhaibahmad.template.di.scopes.ApiScope;
import com.zuhaibahmad.template.di.scopes.DatabaseScope;
import com.zuhaibahmad.template.di.scopes.PreferenceScope;
import com.zuhaibahmad.template.utils.rx.AppSchedulerProvider;
import com.zuhaibahmad.template.utils.rx.SchedulerProvider;

import javax.inject.Singleton;

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
		return AppConstants.DATABASE_NAME;
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
