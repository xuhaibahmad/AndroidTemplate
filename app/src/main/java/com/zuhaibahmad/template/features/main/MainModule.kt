package com.zuhaibahmad.template.features.main

import android.content.Context
import com.zuhaibahmad.template.data.DataManager

import com.zuhaibahmad.template.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides

/**
 * Created by zuhaib.ahmad on 1/16/2018.
 */

@Module
class MainModule {

	@Provides internal fun provideMainPresenter(
		context: Context,
		dataManager: DataManager,
		schedulerProvider: SchedulerProvider
	): MainPresenter = MainPresenter(context, dataManager, schedulerProvider)

}
