package com.lxm.wanandroid.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.lxm.module_library.base.BaseViewModel
import com.lxm.module_library.helper.RxHelper
import com.lxm.wanandroid.repository.model.Resource
import com.lxm.wanandroid.repository.model.WelfareResponse
import com.lxm.wanandroid.repository.remote.RetrofitClient
const val PAGE_SIZE = 20
class WelfareModelView : BaseViewModel() {

    var mPage = 1

    val loadStatus by lazy {
        MutableLiveData<Resource<String>>()
    }
    fun getWelfare(): MutableLiveData<WelfareResponse> {
        val welfare = MutableLiveData<WelfareResponse>()
        RetrofitClient.getInstance(RetrofitClient.GAN_BASE_URL).getWelfare("福利",PAGE_SIZE,mPage)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe({
                welfare.postValue(it)
            }, {
                loadStatus.postValue(Resource.error())
            })
        return welfare
    }
}
