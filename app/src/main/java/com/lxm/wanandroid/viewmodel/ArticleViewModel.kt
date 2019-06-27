package com.lxm.wanandroid.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.lxm.module_library.base.BaseViewModel
import com.lxm.module_library.helper.RxHelper
import com.lxm.wanandroid.repository.model.ArticleResponseBody
import com.lxm.wanandroid.repository.model.HttpResponse
import com.lxm.wanandroid.repository.model.Listing
import com.lxm.wanandroid.repository.model.Resource
import com.lxm.wanandroid.repository.remote.RetrofitClient
import io.reactivex.functions.Consumer

class ArticleViewModel : BaseViewModel() {

    var mPage = 0

    val pagedList = MutableLiveData<HttpResponse<ArticleResponseBody>>()
    val loadStatus by lazy {
        MutableLiveData<Resource<String>>()
    }

    fun getHomeList(): Listing<HttpResponse<ArticleResponseBody>> {
        loadStatus.postValue(Resource.loading())
        val subscribe = RetrofitClient.instance.getArticleList(mPage)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer<HttpResponse<ArticleResponseBody>> {
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
}
