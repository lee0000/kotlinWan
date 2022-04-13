package com.lee0000.WanKotlin.net.api

import com.lee0000.WanKotlin.model.home.*
import com.lee0000.WanKotlin.model.project.ProjectListModel
import com.lee0000.WanKotlin.model.project.ProjectTitleModel
import com.lee0000.WanKotlin.model.pub.PublicListModel
import com.lee0000.WanKotlin.model.pub.PublicTitleModel
import retrofit2.http.GET
import retrofit2.http.Path

/**
author: Lee
date:   2022/4/5
 */
interface ApiService {

    companion object {
        const val BASE_URL = "https://wanandroid.com/"
    }

    /** 公众号 **/
    @GET("wxarticle/chapters/json")
    suspend fun getWxArticle(): PublicTitleModel

    @GET("wxarticle/list/{id}/{page}/json")
    suspend fun getWxArticleList(@Path("id") id: Int, @Path("page") page: Int): PublicListModel

    /** 项目 **/
    @GET("project/tree/json")
    suspend fun getProject(): ProjectTitleModel

    @GET("project/list/{page}/json?cid={cid}")
    suspend fun getProjectList(@Path("cid") cid: Int, @Path("page") page: Int): ProjectListModel

    /** 首页 banner **/
    @GET("banner/json")
    suspend fun getHomeBanner(): HomeBannerModel

    /** 首页 置顶文章 **/
    @GET("article/top/json")
    suspend fun getHomeTopList(): HomeTopListModel

    /** 首页 文章列表 **/
    @GET("article/list/{page}/json")
    suspend fun getHomeList(@Path("page") page: Int): HomeListModel

    /** 首页 导航 **/
    @GET("navi/json")
    suspend fun getNaviList(): NaviListModel

    /** 首页 体系 **/
    @GET("tree/json")
    suspend fun getSystem(): SystemTitleModel

    @GET("article/list/{page}/json?cid={cid}")
    suspend fun getSystemList(@Path("cid") cid: Int, @Path("page") page: Int): SystemListModel
}