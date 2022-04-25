package com.lee0000.WanKotlin.module.home

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.google.android.material.tabs.TabLayoutMediator
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.SUB_PUBLIC_MODEL
import com.lee0000.WanKotlin.model.home.SystemTitleModel
import com.lee0000.WanKotlin.module.base.BaseActivity
import com.lee0000.WanKotlin.util.IntentUtil
import kotlinx.android.synthetic.main.wan_activity_sys_detail.*
import kotlinx.android.synthetic.main.wan_activity_sys_detail.iv_back
import kotlinx.android.synthetic.main.wan_activity_sys_detail.tv_title
import kotlinx.android.synthetic.main.wan_activity_web.*
import kotlinx.android.synthetic.main.wan_fragment_main.tabLayout
import java.io.Serializable

/**
author: Lee
date:   2022/4/24
 */
class SystemDetailActivity: BaseActivity() {

    private var subPublicModel: SystemTitleModel.Data? = null

    override fun getLayoutResId(): Int {
        return R.layout.wan_activity_sys_detail
    }

    override fun initView(savedInstanceState: Bundle?) {

        subPublicModel = if (savedInstanceState != null){
            savedInstanceState.getSerializable(SUB_PUBLIC_MODEL) as SystemTitleModel.Data
        }else{
            val paramBundle = intent.getBundleExtra(IntentUtil.PARAM_BUNDLE)
            paramBundle?.getSerializable(SUB_PUBLIC_MODEL) as SystemTitleModel.Data
        }

        tv_title.text = subPublicModel?.name

        viewPager.offscreenPageLimit = 1
        viewPager.adapter = object : FragmentStateAdapter(this) {
            override fun getItemCount(): Int {
                return subPublicModel?.children?.size!!
            }

            override fun createFragment(position: Int): Fragment {

                val partId = subPublicModel?.children?.get(position)?.id
                return SystemDetailSubFragment(partId!!)
            }
        }
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = subPublicModel?.children?.get(position)?.name
        }.attach()

        iv_back.setOnClickListener {
            finish()
        }
    }

    override fun initData() {

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putSerializable(SUB_PUBLIC_MODEL, subPublicModel as Serializable)
    }
}