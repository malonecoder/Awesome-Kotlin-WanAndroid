package com.lxm.wanandroid.viewmodel

import android.arch.lifecycle.MutableLiveData
import androidx.navigation.NavAction
import com.lxm.module_library.base.BaseViewModel
import com.lxm.module_library.helper.RxHelper
import com.lxm.wanandroid.repository.model.Navigation
import com.lxm.wanandroid.repository.model.Resource
import com.lxm.wanandroid.repository.model.TreeBean
import com.lxm.wanandroid.repository.remote.RetrofitClient

class NaviViewModel : BaseViewModel() {
    private val naviList = MutableLiveData<List<Navigation>>()
    val loadStatus by lazy {
        MutableLiveData<Resource<String>>()
    }
    fun getVavigations(): MutableLiveData<List<Navigation>> {
        RetrofitClient.instance.getNavigation()
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe({
                naviList.postValue(it.data)
            }, {
                loadStatus.postValue(Resource.error())
            })
        return naviList
    }
}
