package com.lxm.wanandroid.repository.remote

import com.lxm.wanandroid.repository.model.*
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query


const val BASE_URL = "https://www.wanandroid.com"
interface API{


   /*
    *
    * 获取首页新闻列表
    *
    * */

    @GET("/article/list/{page}/json")
    fun getArticleList(@Path(value = "page")page:Int):Observable<HttpResponse<ArticleResponseBody<ArticleBean>>>
    /*
    *
    * 获取首页Banner
    *
    * */

    @GET("/banner/json")
    fun getHomeBanner():Observable<HttpResponse<List<Banner>>>
    /*
      *
      * 获取知识体系
      *
      * */

    @GET("/tree/json")
    fun getTrees():Observable<HttpResponse<List<TreeBean>>>

    /*
  *
  * 获取知识体系目录
  * /article/list/0/json?cid=60
  * */

    @GET("/article/list/{page}/json")
    fun getCategory(@Path(value = "page")page:Int,@Query(value = "cid")cid:Int):Observable<HttpResponse<ArticleResponseBody<ArticleBean>>>

}