package com.lee0000.WanKotlin

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.blankj.utilcode.util.StringUtils

/**
author: Lee
date:   2022/3/27
 */
class MainActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)

        Log.e("abcd", "测试用例："+ StringUtils.isEmpty("aaa"))
    }
}