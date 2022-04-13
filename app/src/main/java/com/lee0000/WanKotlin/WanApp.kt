package com.lee0000.WanKotlin

import android.app.Application
import com.tencent.smtt.sdk.QbSdk
import com.umeng.commonsdk.UMConfigure
import com.tencent.smtt.export.external.TbsCoreSettings




/**
author: Lee
date:   2022/3/31
 */
class WanApp: Application() {

    override fun onCreate() {
        super.onCreate()

        initStatistics()
        initTbsWebView()
    }

    private fun initStatistics(){

        UMConfigure.setLogEnabled(true)
        UMConfigure.preInit(this, YM_KEY, "Official")
        UMConfigure.init(this, YM_KEY, "Official", UMConfigure.DEVICE_TYPE_PHONE, "")
    }

    private fun initTbsWebView(){
        // 在调用TBS初始化、创建WebView之前进行如下配置
        val map = HashMap<String, Any>()
        map[TbsCoreSettings.TBS_SETTINGS_USE_SPEEDY_CLASSLOADER] = true
        map[TbsCoreSettings.TBS_SETTINGS_USE_DEXLOADER_SERVICE] = true
        QbSdk.initTbsSettings(map)
        QbSdk.initX5Environment(applicationContext, object : QbSdk.PreInitCallback{
            override fun onCoreInitFinished() {
            }

            override fun onViewInitFinished(p0: Boolean) {
            }

        })
    }
}