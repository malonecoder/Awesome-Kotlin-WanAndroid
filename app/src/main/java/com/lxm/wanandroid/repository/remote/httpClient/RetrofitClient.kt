package com.lxm.wanandroid.repository.remote.httpClient

import com.lxm.module_library.helper.RetrofitCreateHelper


object RetrofitClient {
    const val WAN_BASE_URL = "https://www.wanandroid.com"
    const val GAN_BASE_URL = "https://gank.io"

    private var api: API? = null
    fun getInstance(type: String): API {
        api = RetrofitCreateHelper.createApi(API::class.java, type)
        return api!!
    }
}