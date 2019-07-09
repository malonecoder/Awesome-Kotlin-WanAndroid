package com.lxm.wanandroid.repository.remote

import com.lxm.module_library.helper.RetrofitCreateHelper


object RetrofitClient {
    val BASE_URL = "https://www.wanandroid.com"
    val GAN_BASE_URL = "https://gank.io"

    private var api: API? = null
    fun getInstance(type: String): API {
        api = RetrofitCreateHelper.createApi(API::class.java, type)
        return api!!
    }
}