package com.zuhaibahmad.template.utils.extensions

import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import com.zuhaibahmad.template.AppConstants

/**
 * Created by Zuhaib on 12/17/2017.
 */


fun DialogFragment.display(activity: AppCompatActivity, tag: String) {
	this.show(activity.supportFragmentManager, tag)
	Log.e(AppConstants.TAG, "Starting fragment: ${this.javaClass.simpleName}")
}
