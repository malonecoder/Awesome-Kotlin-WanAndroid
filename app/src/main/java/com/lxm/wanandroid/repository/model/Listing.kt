package com.lxm.wanandroid.repository.model

import android.arch.lifecycle.MutableLiveData

data class Listing<T>(
        val pagedList: MutableLiveData<T>,
        val loadStatus: MutableLiveData<Resource<String>>
        )
