package com.lxm.wanandroid.repository.remote.httpClient

import com.lxm.wanandroid.repository.model.*
import io.reactivex.Observable
import retrofit2.http.*


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
    /*
     *
     * 获取知识体系目录
     * /navi/json
     * */

    @GET("/navi/json")
    fun getNavigation():Observable<HttpResponse<List<Navigation>>>

    /*
     *
     * 获取Gan福利
     * /navi/json
     * */
    @GET("/api/data/{type}/{page_size}/{page}")
    fun getWelfare(@Path("type") id: String,  @Path("page_size") page_size: Int,@Path("page") page: Int): Observable<WelfareResponse>


    /*
     *
    * 获取首页新闻列表
    *
    * */

    @GET("/article/listproject/{page}/json")
    fun getProjectList(@Path(value = "page")page:Int):Observable<HttpResponse<ArticleResponseBody<ArticleBean>>>

    @FormUrlEncoded
    @POST("/user/login")
    fun login(@Field(value = "username")username: String, @Field(value = "password")password: String): Observable<LoginResponse>

    @FormUrlEncoded
    @POST("/user/register")
    fun register(@Field(value = "username")account: String, @Field(value = "password")password: String, @Field(value = "repassword")rPassword: String): Observable<LoginResponse>

}