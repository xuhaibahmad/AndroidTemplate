package com.zuhaibahmad.template.features.main

import android.view.ViewGroup
import com.zuhaibahmad.template.R
import com.zuhaibahmad.template.features.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

/**
 * Created by zuhaib.ahmad on 1/16/2018.
 */

class MainActivity : BaseActivity<MainPresenter>(), MainView {

	@Inject lateinit var presenter: MainPresenter

	override val layoutId: Int get() = R.layout.activity_main
	override val containerView: ViewGroup get() = container


	override fun onResume() {
		super.onResume()

		presenter.view = this
		presenter.onStart()
	}
}
