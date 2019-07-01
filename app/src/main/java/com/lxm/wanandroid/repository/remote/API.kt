package com.lxm.wanandroid.repository.remote

import com.lxm.wanandroid.repository.model.ArticleResponseBody
import com.lxm.wanandroid.repository.model.Banner
import com.lxm.wanandroid.repository.model.HttpResponse
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path


const val BASE_URL = "https://www.wanandroid.com"
interface API{


   /*
    *
    * 获取首页新闻列表
    *
    * */

    @GET("/article/list/{page}/json")
    fun getArticleList(@Path(value = "page")page:Int):Observable<HttpResponse<ArticleResponseBody<Any?>>>
    /*
    *
    * 获取首页Banner
    *
    * */

    @GET("/banner/json")
    fun getHomeBanner():Observable<HttpResponse<List<Banner>>>

}