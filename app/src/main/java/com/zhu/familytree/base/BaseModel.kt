package com.zhu.familytree.base

import io.reactivex.Single
import io.reactivex.SingleObserver
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * @description model基类
 *
 * @author QQ657036139
 * @since 2019/11/9
 */
open class BaseModel {
    fun <T> Single<T>.subscribeDbResult(
        onSuccess: (data: T) -> Unit,
        onFailed: (e: Throwable) -> Unit
    ) {
        subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())
            .subscribe(object : SingleObserver<T> {
                override fun onSuccess(t: T) {
                    onSuccess(t)
                }

                override fun onError(e: Throwable) {
                    onFailed(e)
                }

                override fun onSubscribe(d: Disposable) {

                }
            })
    }
}