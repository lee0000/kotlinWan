package com.lee0000.WanKotlin.net.repository

import com.lee0000.WanKotlin.model.home.HomeTopListModel
import com.lee0000.WanKotlin.net.api.RetrofitClient

/**
author: Lee
date:   2022/4/8
 */
class HomeRepository {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchTopList(): HomeTopListModel {
        return mService.getHomeTopList()
    }
}