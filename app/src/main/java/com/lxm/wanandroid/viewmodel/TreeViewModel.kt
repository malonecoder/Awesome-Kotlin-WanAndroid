package com.lxm.wanandroid.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.lxm.module_library.base.BaseViewModel
import com.lxm.module_library.helper.RxHelper
import com.lxm.wanandroid.repository.model.Resource
import com.lxm.wanandroid.repository.model.TreeBean
import com.lxm.wanandroid.repository.remote.RetrofitClient

class TreeViewModel : BaseViewModel() {
    private val treeList = MutableLiveData<List<TreeBean>>()
    val loadStatus by lazy {
        MutableLiveData<Resource<String>>()
    }
    fun getTrees(): MutableLiveData<List<TreeBean>> {
        RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).getTrees()
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe({
                treeList.postValue(it.data)
            }, {
                loadStatus.postValue(Resource.error())
            })
        return treeList
    }
}
