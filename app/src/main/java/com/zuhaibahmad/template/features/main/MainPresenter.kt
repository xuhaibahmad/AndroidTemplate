package com.zuhaibahmad.template.features.main

import android.content.Context
import android.widget.Toast
import com.zuhaibahmad.template.data.DataManager

import com.zuhaibahmad.template.features.base.BasePresenter
import com.zuhaibahmad.template.utils.OpenClass
import com.zuhaibahmad.template.utils.rx.SchedulerProvider

/**
 * Created by zuhaib.ahmad on 1/16/2018.
 */

@OpenClass
class MainPresenter(
	private val context: Context,
	dataManager: DataManager,
	scheduler: SchedulerProvider
) : BasePresenter<MainView>(dataManager, scheduler) {

	override fun onStart() {
		super.onStart()
		Toast.makeText(context, "Hello World", Toast.LENGTH_SHORT).show()
	}

	override fun subscribeToUiEvents() = false
	override fun subscribeToDataEvents() = false
}
