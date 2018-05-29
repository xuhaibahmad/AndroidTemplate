package com.zuhaibahmad.template.features.base

import android.annotation.TargetApi
import android.app.ProgressDialog
import android.content.Context
import android.content.pm.ActivityInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.WindowManager.LayoutParams.FLAG_FULLSCREEN
import android.view.inputmethod.InputMethodManager
import com.joanzapata.iconify.Icon
import com.joanzapata.iconify.IconDrawable
import com.zuhaibahmad.template.R
import com.zuhaibahmad.template.utils.CommonUtils
import com.zuhaibahmad.template.utils.NetworkUtils
import com.zuhaibahmad.template.utils.extensions.str
import dagger.android.AndroidInjection

const val ICON_SIZE = 16

/**
 * Created by Zuhaib Ahmad on 07/07/17.
 *
 *
 * Base class for all activity instances
 */


abstract class BaseActivity<V : BasePresenter<*>> : AppCompatActivity(), BaseFragment.Callback {

	// TODO
	// this can probably depend on isLoading variable of BasePresenter,
	// since its going to be common for all the activities
	private var mProgressDialog: ProgressDialog? = null

	private var mPresenter: V? = null

	val isNetworkConnected: Boolean
		get() = NetworkUtils.isNetworkConnected(applicationContext)

	val navigationBarHeight: Int
		get() {
			val resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android")
			return if (resourceId > 0) {
				resources.getDimensionPixelSize(resourceId)
			} else 0
		}

	val statusBarHeight: Int
		get() {
			val resourceId = resources.getIdentifier("status_bar_height", "dimen", "android")
			return if (resourceId > 0) {
				resources.getDimensionPixelSize(resourceId)
			} else 0
		}

	abstract val containerView: ViewGroup

	fun getPresenter(): V? = lastNonConfigurationInstance as V?

	/**
	 * @return layout resource id
	 */
	@get:LayoutRes
	abstract val layoutId: Int
	//private Unbinder binder;

	override fun onCreate(savedInstanceState: Bundle?) {
		performDependencyInjection()
		super.onCreate(savedInstanceState)
		performDataBinding()
	}

	private fun performDataBinding() {
		setStatusBarColor()
		setContentView(layoutId)

		//binder = ButterKnife.bind(this);
		mPresenter = if (mPresenter == null) getPresenter() else mPresenter
	}

	protected fun color(color: Int): Int = ContextCompat.getColor(this, color)

	protected fun setStatusBarColor() = window.let {

		// clear FLAG_TRANSLUCENT_STATUS flag:
		it.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)

		// add FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS flag to the window
		it.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

		// finally change the color
		it.statusBarColor = ContextCompat.getColor(this, R.color.colorPrimaryDark)
	}

	protected fun setTransparentStatusBar() {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			val w = window // in Activity's onCreate() for instance
			w.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)
		}
	}

	override fun onRetainCustomNonConfigurationInstance(): Any? = mPresenter

	override fun onDestroy() {
		//binder.unbind();
		if (mPresenter != null) {
			mPresenter!!.onCleared()
		}
		super.onDestroy()
	}

	@TargetApi(Build.VERSION_CODES.M)
	fun requestPermissionsSafely(permissions: Array<String>, requestCode: Int) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			requestPermissions(permissions, requestCode)
		}
	}

	@TargetApi(Build.VERSION_CODES.M)
	fun hasPermission(permission: String): Boolean = Build.VERSION.SDK_INT < Build.VERSION_CODES.M
			|| checkSelfPermission(permission) == PackageManager.PERMISSION_GRANTED

	override fun onFragmentAttached() {

	}

	override fun onFragmentDetached(tag: String) {

	}

	fun hideKeyboard() = this.currentFocus.run {
		(getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
				.hideSoftInputFromWindow(windowToken, 0)
	}

	fun showLoading() {
		hideLoading()
		mProgressDialog = CommonUtils.showLoadingDialog(this)
	}


	fun forceLandscapeOrientation(isLandscape: Boolean) {
		requestedOrientation = if (isLandscape)
			ActivityInfo.SCREEN_ORIENTATION_SENSOR_LANDSCAPE
		else
			ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
	}

	fun toggleFullscreen(isFullscreen: Boolean?) {
		val attrs = window.attributes
		if (isFullscreen!!)
			attrs.flags = attrs.flags or FLAG_FULLSCREEN
		else
			attrs.flags = attrs.flags and FLAG_FULLSCREEN.inv()

		window.attributes = attrs
	}

	fun hideSystemUI() {
		window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
				or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
				or View.SYSTEM_UI_FLAG_FULLSCREEN
				or View.SYSTEM_UI_FLAG_IMMERSIVE)
	}

	fun showSystemUI() {
		window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
				or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
				or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
	}

	fun hideLoading() {
		if (mProgressDialog != null && mProgressDialog!!.isShowing) {
			mProgressDialog!!.cancel()
		}
	}

	fun setLoadingProgress(progress: Int) {
		if (mProgressDialog != null && mProgressDialog!!.isShowing) {
			mProgressDialog!!.progress = progress
		}
	}

	@JvmOverloads
	protected fun getIcon(icon: Icon, color: Int = R.color.light_gray, size: Int = ICON_SIZE) =
		IconDrawable(this, icon).colorRes(color).sizeDp(size)

	fun openActivityOnTokenExpire() {
		// TODO Implement if needed
	}

	fun displayMessage(resId: Int = 0, message: String = "") = Snackbar
			.make(containerView, if (resId > 0) str(resId) else message, Snackbar.LENGTH_LONG)
			.show()

	fun performDependencyInjection() = AndroidInjection.inject(this)

	fun Fragment.isSameFragment() = supportFragmentManager?.fragments.run {
		this?.isNotEmpty() ?: false && javaClass == this?.last()?.javaClass
	}

}

