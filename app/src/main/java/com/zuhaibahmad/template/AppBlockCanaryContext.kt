package com.zuhaibahmad.template

import android.content.Context
import android.content.pm.PackageManager
import com.github.moduth.blockcanary.BlockCanaryContext
import com.zuhaibahmad.template.utils.AppLogger

class AppBlockCanaryContext(val context: Context) : BlockCanaryContext() {

	override fun provideQualifier(): String {
		var qualifier = ""
		try {
			val info = context.packageManager.getPackageInfo(context.packageName, 0)
			qualifier += info.versionCode.toString() + "_" + info.versionName + "_YYB"
		} catch (e: PackageManager.NameNotFoundException) {
			AppLogger.e("provideQualifier exception", e)
		}

		return qualifier
	}

	override fun provideBlockThreshold() = 2000

	override fun displayNotification() = BuildConfig.DEBUG

	override fun stopWhenDebugging() = true

	override fun concernPackages(): List<String> {
		val list = super.provideWhiteList()
		list.add("com.zuhaibahmad.template")
		return list
	}
}
