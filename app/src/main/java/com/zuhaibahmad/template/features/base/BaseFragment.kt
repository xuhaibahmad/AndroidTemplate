package com.zuhaibahmad.template.features.base

import android.content.Context
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import dagger.android.support.AndroidSupportInjection

/**
 * Created by Zuhaib Ahmad on 09/07/17.
 */

abstract class BaseFragment<V : BasePresenter<*>> : Fragment() {

	var baseActivity: BaseActivity<*>? = null
		private set
	private var mPresenter: V? = null
	private var mRootView: View? = null

	/**
	 * Override for set view model
	 *
	 * @return view model instance
	 */
	abstract fun getFragmentPresenter(): V

	val isNetworkConnected: Boolean
		get() = baseActivity != null && baseActivity!!.isNetworkConnected

	/**
	 * @return layout resource id
	 */
	@get:LayoutRes
	abstract val layoutId: Int

	override fun onCreate(savedInstanceState: Bundle?) {
		performDependencyInjection()
		super.onCreate(savedInstanceState)
		mPresenter = getFragmentPresenter()
		setHasOptionsMenu(false)
	}


	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		mRootView = inflater.inflate(layoutId, container, false)
		return mRootView
	}

	override fun onAttach(context: Context?) {
		super.onAttach(context)
		if (context is BaseActivity<*>) {
			val activity = context as BaseActivity<*>?
			this.baseActivity = activity
			activity!!.onFragmentAttached()
		}
	}

	override fun onDestroy() {
		if (mPresenter != null) {
			mPresenter!!.onCleared()
		}
		super.onDestroy()
	}

	override fun onDetach() {
		baseActivity = null
		super.onDetach()
	}

	fun hideKeyboard() {
		if (baseActivity != null) {
			baseActivity!!.hideKeyboard()
		}
	}

	fun openActivityOnTokenExpire() {
		if (baseActivity != null) {
			baseActivity!!.openActivityOnTokenExpire()
		}
	}

	private fun performDependencyInjection() = AndroidSupportInjection.inject(this)

	interface Callback {

		fun onFragmentAttached()

		fun onFragmentDetached(tag: String)
	}
}
