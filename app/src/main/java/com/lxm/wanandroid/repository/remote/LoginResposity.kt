package com.lxm.wanandroid.repository.remote

import android.arch.lifecycle.MutableLiveData
import com.lxm.module_library.helper.RxHelper
import com.lxm.wanandroid.repository.model.HttpResponse
import com.lxm.wanandroid.repository.model.LoginBean
import com.lxm.wanandroid.repository.remote.httpClient.RetrofitClient
import io.reactivex.functions.Consumer

class LoginRepository {

    val login = MutableLiveData<HttpResponse<LoginBean>>()
    val register = MutableLiveData<HttpResponse<LoginBean>>()
    val logout = MutableLiveData<HttpResponse<Any>>()

    fun login(account: String, password: String) {

        RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).login(account, password)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer {
                login.value = it
            }, Consumer {
                login.value = HttpResponse(null, 500, it.message!!)
            })

    }

    fun register(account: String, password: String, rPassword: String) {

        RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).register(account, password, rPassword)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer {
                register.value = it
            }, Consumer {
                register.value = HttpResponse(null, 500, it.message!!)
            })

    }

    fun logout() {
        RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).logout()
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer {
                logout.value = it
            }, Consumer {
                logout.value = HttpResponse(null, 500, it.message!!)
            })

    }



}