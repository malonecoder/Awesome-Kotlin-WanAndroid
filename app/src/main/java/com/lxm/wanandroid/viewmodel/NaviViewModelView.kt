package com.lxm.wanandroid.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.lxm.module_library.base.BaseViewModel
import com.lxm.module_library.helper.RxHelper
import com.lxm.wanandroid.repository.model.Navigation
import com.lxm.wanandroid.repository.model.Resource
import com.lxm.wanandroid.repository.remote.RetrofitClient

class NaviViewModelView : BaseViewModel() {
    private val naviList = MutableLiveData<List<Navigation>>()
    val loadStatus by lazy {
        MutableLiveData<Resource<String>>()
    }
    fun getVavigations(): MutableLiveData<List<Navigation>> {
        RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).getNavigation()
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe({
                naviList.postValue(it.data)
            }, {
                loadStatus.postValue(Resource.error())
            })
        return naviList
    }
}
