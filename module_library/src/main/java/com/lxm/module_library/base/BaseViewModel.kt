package com.lxm.module_library.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable

open class BaseViewModel : ViewModel() {

    private var mCompositeDisposable: CompositeDisposable? = null


    protected fun addDisposable(disposable: Disposable) {
        if (this.mCompositeDisposable == null) {
            this.mCompositeDisposable = CompositeDisposable()
        }
        this.mCompositeDisposable?.add(disposable)
    }

    override fun onCleared() {
        super.onCleared()
        if (this.mCompositeDisposable != null && !mCompositeDisposable?.isDisposed!!) {
            this.mCompositeDisposable?.clear()
        }
    }
}
