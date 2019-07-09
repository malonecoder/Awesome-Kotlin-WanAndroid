package com.lxm.wanandroid.viewmodel

import android.arch.lifecycle.MutableLiveData
import com.lxm.module_library.base.BaseViewModel
import com.lxm.module_library.helper.RxHelper
import com.lxm.wanandroid.repository.model.*
import com.lxm.wanandroid.repository.remote.RetrofitClient
import io.reactivex.functions.Consumer

class ProjectViewModel : BaseViewModel() {

    var mPage = 0
    val pagedList = MutableLiveData<HttpResponse<ArticleResponseBody<ArticleBean>>>()
    val loadStatus by lazy {
        MutableLiveData<Resource<String>>()
    }

    fun getProjects(): Listing<HttpResponse<ArticleResponseBody<ArticleBean>>> {
        loadStatus.postValue(Resource.loading())
        val subscribe = RetrofitClient.getInstance(RetrofitClient.WAN_BASE_URL).getProjectList(mPage)
            .compose(RxHelper.rxSchedulerHelper())
            .subscribe(Consumer<HttpResponse<ArticleResponseBody<ArticleBean>>> {
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
