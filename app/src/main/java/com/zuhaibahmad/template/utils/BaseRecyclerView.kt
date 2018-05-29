package com.zuhaibahmad.template.utils

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.AttributeSet
import android.view.View


/**
 * Created by zuhaib.ahmad on 2/22/2018.
 */
/**
 * Simple RecyclerView subclass that supports providing an empty view (which
 * is displayed when the adapter has no data and hidden otherwise).
 */
class BaseRecyclerView : RecyclerView {

	private var mEmptyView: View? = null

	private val mDataObserver = object : RecyclerView.AdapterDataObserver() {
		override fun onChanged() {
			super.onChanged()
			updateEmptyView()
		}
	}

	constructor(context: Context) : super(context)

	constructor(context: Context, attrs: AttributeSet) : super(context, attrs)

	constructor(context: Context, attrs: AttributeSet, defStyle: Int) :
			super(context, attrs, defStyle)

	/**
	 * Designate a view as the empty view. When the backing adapter has no
	 * data this view will be made visible and the recycler view hidden.
	 *
	 */
	fun setEmptyView(emptyView: View) = let { mEmptyView = emptyView }

	override fun setAdapter(adapter: RecyclerView.Adapter<*>?) {
		getAdapter()?.unregisterAdapterDataObserver(mDataObserver)
		adapter?.registerAdapterDataObserver(mDataObserver)
		super.setAdapter(adapter)
		updateEmptyView()
	}

	private fun updateEmptyView() {
		val showEmptyView = adapter.itemCount == 0
		mEmptyView?.visibility = if (showEmptyView) View.VISIBLE else View.GONE
		visibility = if (showEmptyView) View.GONE else View.VISIBLE
	}
}
