package com.lee0000.WanKotlin.module.home

import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.viewModel.PublicVM
import kotlinx.coroutines.launch

/**
author: Lee
date:   2022/3/31
 */
class HomeFragment: BaseFragment() {

    private val publicVM by viewModels<PublicVM>()

    override fun getLayoutResId(): Int {

        return R.layout.wan_fragment_home
    }

    override fun initView() {

    }

    override fun initData() {

    }
}