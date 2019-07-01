package com.lxm.wanandroid.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.lxm.module_library.base.BaseViewModel
import com.lxm.module_library.helper.RxHelper
import com.lxm.wanandroid.repository.model.*
import com.lxm.wanandroid.repository.remote.RetrofitClient
import io.reactivex.functions.Consumer

class ArticleViewModel : BaseViewModel() {

    var mPage = 0
    var banner = MutableLiveData<HttpResponse<List<Banner>>>()
    val pagedList = MutableLiveData<HttpResponse<ArticleResponseBody<Any?>>>()
    val loadStatus by lazy {
        MutableLiveData<Resource<String>>()
    }

    fun getHomeList(): Listing<HttpResponse<ArticleResponseBody<Any?>>> {
        loadStatus.postValue(Resource.loading())
        val subscribe = RetrofitClient.instance.getArticleList(mPage)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer<HttpResponse<ArticleResponseBody<Any?>>> {
                if(it.data != null){
                    loadStatus.postValue(Resource.success())
                    pagedList.value = it
                }else{
                    loadStatus.postValue(Resource.error())
                }

            }, Consumer<Throwable> {
                if (mPage > 0) {
                    mPage--
                }
                loadStatus.postValue(Resource.error())
            })
        addDisposable(subscribe)
        return Listing(pagedList,loadStatus)
    }

    fun getBanners(): MutableLiveData<HttpResponse<List<Banner>>> {
        RetrofitClient.instance.getHomeBanner()
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer<HttpResponse<List<Banner>>>{
                banner.postValue(it)
            }, Consumer<Throwable> {
                banner.postValue(null)
            })
        return banner
    }
}
