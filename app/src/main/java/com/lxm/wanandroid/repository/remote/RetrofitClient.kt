package com.lxm.wanandroid.repository.remote

import com.lxm.module_library.helper.RetrofitCreateHelper


object RetrofitClient{

    private var api: API? = null


    val instance: API
    get() {
        if(api == null){
            synchronized(RetrofitClient::class.java){
                if(api == null){
                    api = RetrofitCreateHelper.createApi(API::class.java, BASE_URL)
                }
            }
        }
        return api!!
    }
}