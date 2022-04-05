package com.lee0000.WanKotlin.net.api

import com.blankj.utilcode.util.NetworkUtils
import com.lee0000.WanKotlin.net.base.BaseRetrofitClient
import okhttp3.Cache
import okhttp3.CacheControl
import okhttp3.OkHttpClient
import java.io.File

/**
author: Lee
date:   2022/4/5
 */
object RetrofitClient: BaseRetrofitClient() {

    val service by lazy { getService(ApiService::class.java, ApiService.BASE_URL) }

    override fun handleBuilder(builder: OkHttpClient.Builder) = Unit

}