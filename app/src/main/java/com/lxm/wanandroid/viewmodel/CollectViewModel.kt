package com.lxm.wanandroid.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.lxm.module_library.base.BaseViewModel
import com.lxm.module_library.helper.RxHelper
import com.lxm.wanandroid.repository.model.*
import com.lxm.wanandroid.repository.remote.httpClient.RetrofitClient
import com.lxm.wanandroid.ui.adapter.ArticleAdapter
import io.reactivex.functions.Consumer

class CollectViewModel : BaseViewModel() {

    var mPage = 0
    val collectionList = MutableLiveData<HttpResponse<ArticleResponseBody<ArticleBean>>>()
    val loadStatus by lazy {
        MutableLiveData<Resource<String>>()
    }

    fun getCollect(): Listing<HttpResponse<ArticleResponseBody<ArticleBean>>> {
        loadStatus.postValue(Resource.loading())
        val subscribe = RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).getCollectList(mPage)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer<HttpResponse<ArticleResponseBody<ArticleBean>>> {
                if(it.data != null){
                    loadStatus.postValue(Resource.success())
                    collectionList.value = it
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
        return Listing(collectionList,loadStatus)
    }

    fun collect(id:Int,collectionObserver: ArticleAdapter.CollectionObserver<HttpResponse<Any>>) {
        val subscribe = RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).collect(id)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer<HttpResponse<Any>> {
                collectionObserver.onChanged(it)
            }, Consumer<Throwable> {
                collectionObserver.onChanged(null)
            })
        addDisposable(subscribe)
    }
    fun unCollect(id:Int,collectionObserver: ArticleAdapter.CollectionObserver<HttpResponse<Any>>) {
        val subscribe = RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).unCollect(id)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer<HttpResponse<Any>> {
                collectionObserver.onChanged(it)
            }, Consumer<Throwable> {
                collectionObserver.onChanged(null)
            })
        addDisposable(subscribe)
    }
}
