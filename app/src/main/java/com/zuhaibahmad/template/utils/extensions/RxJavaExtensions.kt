package com.zuhaibahmad.template.utils.extensions

import io.reactivex.Completable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Zuhaib on 10/22/2017.
 *
 * Extensions for commonly used RxJava types
 */

fun Completable.inBackground(): Completable = this
		.doOnError { it.printStackTrace() }
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())

fun <T> Single<T>.inBackground(): Single<T> = this
		.doOnError { it.printStackTrace() }
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())

fun <T> Maybe<T>.inBackground(): Maybe<T> = this
		.doOnError { it.printStackTrace() }
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())

fun <T> Observable<T>.inBackground(): Observable<T> = this
		.doOnError { it.printStackTrace() }
		.subscribeOn(Schedulers.io())
		.observeOn(AndroidSchedulers.mainThread())
