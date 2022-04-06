package com.lee0000.WanKotlin.net.repository

import com.lee0000.WanKotlin.model.pub.PublicListModel
import com.lee0000.WanKotlin.model.pub.PublicTitleModel
import com.lee0000.WanKotlin.net.api.RetrofitClient

/**
author: Lee
date:   2022/4/5
 */
class PublicRepository {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchWxArticleTitle(): PublicTitleModel{
        return mService.getWxArticle()
    }

    suspend fun fetchWxArticleList(id: Int, page: Int): PublicListModel{
        return mService.getWxArticleList(id, page)
    }
}