package com.lee0000.WanKotlin.net.repository

import com.lee0000.WanKotlin.model.home.*
import com.lee0000.WanKotlin.net.api.RetrofitClient

/**
author: Lee
date:   2022/4/8
 */
class HomeRepository {

    private val mService by lazy {
        RetrofitClient.service
    }

    suspend fun fetchHomeBanner(): HomeBannerModel{
        return mService.getHomeBanner()
    }

    suspend fun fetchHomeTopList(): HomeTopListModel {
        return mService.getHomeTopList()
    }

    suspend fun fetchHomeList(page: Int): HomeListModel{
        return mService.getHomeList(page)
    }

    suspend fun fetchSystemTitle(): SystemTitleModel {
        return mService.getSystem()
    }

    suspend fun fetchSystemList(cid: Int, page: Int): SystemListModel {
        return mService.getSystemList(page, cid)
    }

    suspend fun fetchNaviList(): NaviListModel {
        return mService.getNaviList()
    }

}