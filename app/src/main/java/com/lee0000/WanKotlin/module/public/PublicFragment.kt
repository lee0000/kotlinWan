package com.lee0000.WanKotlin.module.public

import androidx.fragment.app.Fragment
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.viewModel.PublicVM
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.android.synthetic.main.wan_fragment_main.*
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

        viewPager.offscreenPageLimit = 1
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return publicVM.titleEntities.size
            }

            override fun createFragment(position: Int): Fragment {

                val partId = publicVM.titleEntities[position].id
                return PublicSubFragment(partId)
            }
        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = publicVM.titleEntities[position].name
        }.attach()
    }

    override fun initData() {

        // title
        publicVM.requestWxArticle()
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                publicVM.ptmUIState.collect{

                    if (it.showSuccess != null) {
                        it.showSuccess.data.map { titleModelData ->
                            publicVM.titleEntities.add(titleModelData)
                        }
                        viewPager.adapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }
}