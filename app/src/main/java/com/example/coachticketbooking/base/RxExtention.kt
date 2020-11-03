package com.example.coachticketbooking.base

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.schedulers.Schedulers

fun Disposable.addToCompositeDisposable(compositeDisposable: CompositeDisposable) {
    if (!compositeDisposable.isDisposed) {
        compositeDisposable.add(compositeDisposable)
    }
}

fun <T> Observable<T>.applyScheduler(): Observable<T> {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Single<T>.applyScheduler(): Single<T> {
    return this
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
}