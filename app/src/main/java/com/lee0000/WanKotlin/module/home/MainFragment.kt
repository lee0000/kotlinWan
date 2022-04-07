package com.lee0000.WanKotlin.module.home

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.module.base.BaseFragment
import kotlinx.android.synthetic.main.wan_fragment_main.*

/**
author: Lee
date:   2022/3/31
 */
class MainFragment: BaseFragment() {

    private val titleList = arrayOf("首页", "体系", "导航")

    override fun getLayoutResId(): Int {

        return R.layout.wan_fragment_main
    }

    override fun initView() {

        initPager()
    }

    override fun initData() {

    }

    private fun initPager(){
        viewPager.offscreenPageLimit = 1
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return titleList.size
            }

            override fun createFragment(position: Int): Fragment {

                return when (position) {
                    0 -> {
                        HomeFragment()
                    }
                    1 -> {
                        SystemFragment()
                    }
                    2 -> {
                        NavigationFragment()
                    }
                    else -> {
                        HomeFragment()
                    }
                }
            }
        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = titleList[position]
        }.attach()
    }
}