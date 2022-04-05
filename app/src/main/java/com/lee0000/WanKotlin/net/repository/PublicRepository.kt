package com.lee0000.WanKotlin.net.repository

import com.lee0000.WanKotlin.net.api.RetrofitClient
import com.lee0000.WanKotlin.net.base.BaseRepository

/**
author: Lee
date:   2022/4/5
 */
class PublicRepository: BaseRepository() {

    private val mService by lazy {
        RetrofitClient.service
    }
}