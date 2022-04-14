package com.lee0000.WanKotlin.util

import android.content.Context
import android.content.Intent
import android.os.Bundle
import com.lee0000.WanKotlin.module.base.BaseActivity
import com.lee0000.WanKotlin.module.web.WebActivity

/**
author: Lee
date:   2022/4/14
 */
object IntentUtil {

    const val PARAM_BUNDLE = "Param_Bundle"

    // 常规跳转，能携带bundle参数； 后续使用alibaba的Route替代
    fun <T> navigate(context: Context, destinationCls: Class<T>, paramBundle: Bundle? = null){

        val intent = Intent(context, destinationCls)
        paramBundle?.let {
            intent.putExtra(PARAM_BUNDLE, paramBundle)
        }
        context.startActivity(intent)
    }
}