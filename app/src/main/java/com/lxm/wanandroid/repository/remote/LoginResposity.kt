package com.lxm.wanandroid.repository.remote

import android.arch.lifecycle.MutableLiveData
import com.lxm.module_library.helper.RxHelper
import com.lxm.wanandroid.repository.model.LoginResponse
import com.lxm.wanandroid.repository.remote.httpClient.RetrofitClient
import io.reactivex.functions.Consumer

class LoginRepository {

    val login = MutableLiveData<LoginResponse>()
    val register = MutableLiveData<LoginResponse>()

    fun login(account: String, password: String) {

        RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).login(account, password)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer {
                login.value = it
            }, Consumer {
                login.value = LoginResponse(null, 500, it.message!!)
            })

    }

    fun register(account: String, password: String, rPassword: String) {

        RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).register(account, password, rPassword)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer {
                register.value = it
            }, Consumer {
                register.value = LoginResponse(null, 500, it.message!!)
            })

    }


}