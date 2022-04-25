package com.lee0000.WanKotlin.module.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

/**
author: Lee
date:   2022/3/31
 */
abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(getLayoutResId())
        initView(savedInstanceState)
        initData()
    }

    abstract fun getLayoutResId(): Int
    // 布局初始化
    abstract fun initView(savedInstanceState: Bundle?)
    // 数据初始化
    abstract fun initData()
}