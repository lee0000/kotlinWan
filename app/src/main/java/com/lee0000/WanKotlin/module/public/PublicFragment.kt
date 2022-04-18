package com.lee0000.WanKotlin.module.public

import androidx.fragment.app.Fragment
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.viewModel.PublicVM
import androidx.fragment.app.viewModels
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.lee0000.WanKotlin.model.pub.PublicTitleModel
import kotlinx.android.synthetic.main.wan_fragment_main.*

/**
author: Lee
date:   2022/3/31
 */
class PublicFragment : BaseFragment() {

    private val titleList = arrayListOf<PublicTitleModel.Data>()
    private val publicVM by viewModels<PublicVM>()

    override fun getLayoutResId(): Int {

        return R.layout.wan_fragment_public
    }

    override fun initView() {

        viewPager.offscreenPageLimit = 1
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return titleList.size
            }

            override fun createFragment(position: Int): Fragment {

                val partId = titleList[position].id
                return PublicSubFragment(partId)
            }
        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titleList[position].name
        }.attach()
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
                it.showSuccess.data.map { titleModelData ->
                    titleList.add(titleModelData)
                }
                viewPager.adapter?.notifyDataSetChanged()
            }

        }
    }
}