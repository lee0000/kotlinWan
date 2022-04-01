package com.lee0000.WanKotlin.module

import com.blankj.utilcode.util.ToastUtils
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.module.base.BaseActivity
import com.umeng.analytics.MobclickAgent
import com.umeng.commonsdk.UMConfigure
import kotlin.system.exitProcess

/**
author: Lee
date:   2022/3/27
 */
class MainActivity: BaseActivity() {

    override fun getLayoutResId(): Int {
        return R.layout.wan_activity_main
    }

    override fun initView() {

    }

    override fun initData() {

    }

    // 双击退出的时间标识
    private var lastClickOutTime = 0L
    override fun onBackPressed() {

        val nowClickOutTime = System.currentTimeMillis()
        if (nowClickOutTime - lastClickOutTime < 2000){

            MobclickAgent.onKillProcess(this)
            finish()
            exitProcess(0)
        }else{
            lastClickOutTime = nowClickOutTime
            ToastUtils.showShort("再按一次退出程序")
        }

    }

}