package com.lee0000.WanKotlin.module.home

import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.model.home.HomeBannerModel
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.viewModel.HomeVM
import com.lee0000.WanKotlin.widget.itemDecoration.SimpleDividerItemDecoration
import com.youth.banner.Banner
import com.youth.banner.indicator.CircleIndicator
import kotlinx.android.synthetic.main.wan_fragment_home.*

/**
author: Lee
date:   2022/4/7
 */
class HomeFragment: BaseFragment() {

    private val homeVM by viewModels<HomeVM>()

    private var homeAdapter: HomeAdapter? = null

    private val entities = arrayListOf<MultiItemEntity>()

    private var banner: Banner<HomeBannerModel.Data, HomeBannerAdapter>? = null

    override fun getLayoutResId(): Int {
        return R.layout.wan_fragment_home
    }

    override fun initView() {

        banner = Banner(requireContext())
        banner?.run {
            layoutParams = LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT)
            addBannerLifecycleObserver(viewLifecycleOwner)
            indicator = CircleIndicator(requireContext())
        }

        homeAdapter = HomeAdapter(entities)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(SimpleDividerItemDecoration(requireContext()))
        recyclerView.adapter = homeAdapter

        homeAdapter?.run {
            if (headerLayoutCount > 0) removeHeaderView(banner!!)
            addHeaderView(banner!!)
        }
    }

    override fun initData() {

        homeVM.getArticleList(HomeVM.ArticleType.HomeAll, false)
        homeVM.halUIState.observe(viewLifecycleOwner){

            if (it.showSuccess != null){
                banner?.setAdapter(HomeBannerAdapter(it.showSuccess.banner.data))
                entities.addAll(it.showSuccess.topList.data)
                entities.addAll(it.showSuccess.normalList.data.datas)

                homeAdapter?.setList(entities)
            }
        }
    }
}