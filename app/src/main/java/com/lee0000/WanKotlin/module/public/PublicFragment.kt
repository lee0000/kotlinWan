package com.lee0000.WanKotlin.module.public

import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.viewModel.PublicVM
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

/**
author: Lee
date:   2022/3/31
 */
class PublicFragment : BaseFragment() {

    private val publicVM by viewModels<PublicVM>()

    override fun getLayoutResId(): Int {

        return R.layout.wan_fragment_public
    }

    override fun initView() {

    }

    override fun initData() {

        // title
        publicVM.requestWxArticle()
        publicVM.ptmUIState.observe(viewLifecycleOwner) {
            if (it.isRefresh) {

            }

            if (it.showLoading) {

            }

            if (it.showSuccess != null) {

            }

        }

        // list
        publicVM.requestWxArticleList()
        publicVM.plmUIState.observe(viewLifecycleOwner){

        }

    }
}