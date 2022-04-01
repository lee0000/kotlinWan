package com.lee0000.WanKotlin

import android.app.Application
import com.umeng.commonsdk.UMConfigure

/**
author: Lee
date:   2022/3/31
 */
class WanApp: Application() {

    override fun onCreate() {
        super.onCreate()

        initStatistics()
    }

    private fun initStatistics(){

        UMConfigure.setLogEnabled(true)
        UMConfigure.preInit(this, YM_KEY, "Official")
        UMConfigure.init(this, YM_KEY, "Official", UMConfigure.DEVICE_TYPE_PHONE, "")
    }
}