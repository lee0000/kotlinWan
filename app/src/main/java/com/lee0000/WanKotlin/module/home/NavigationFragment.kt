package com.lee0000.WanKotlin.module.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.URL
import com.lee0000.WanKotlin.listener.INaviClickListener
import com.lee0000.WanKotlin.model.home.NaviListModel
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.module.web.WebActivity
import com.lee0000.WanKotlin.util.IntentUtil
import com.lee0000.WanKotlin.viewModel.HomeVM
import kotlinx.android.synthetic.main.wan_fragment_home.recyclerView
import kotlinx.android.synthetic.main.wan_fragment_navi.*

/**
author: Lee
date:   2022/4/7
 */
class NavigationFragment: BaseFragment() {

    private val homeVM by viewModels<HomeVM>()

    private var naviTitleAdapter: NaviTitleAdapter? = null
    private var naviDetailAdapter: NaviDetailAdapter? = null

    override fun getLayoutResId(): Int {
        return R.layout.wan_fragment_navi
    }

    override fun initView() {

        naviTitleAdapter = NaviTitleAdapter(R.layout.wan_item_navi_title, homeVM.naviEntities.keys.toMutableList())
        tabRecyclerView.layoutManager = LinearLayoutManager(requireContext())
        tabRecyclerView.adapter = naviTitleAdapter
        naviTitleAdapter?.setOnItemClickListener { adapter, view, position ->

            // 左边点击快速定位到对应的详细链接
            try {
                val llManager = recyclerView.layoutManager as LinearLayoutManager
                llManager.scrollToPositionWithOffset(position, 0)
            }catch (e: Exception){
                e.printStackTrace()
            }
        }

        naviDetailAdapter = NaviDetailAdapter(R.layout.wan_item_navi_detatil, homeVM.naviEntities.values.toMutableList()
            , handleNaviClickListener())
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = naviDetailAdapter
    }

    override fun initData() {

        stateLayout?.showLoading()
        homeVM.getArticleList(HomeVM.ArticleType.NavigationList)
        homeVM.hnlUIState.observe(viewLifecycleOwner){

            if (it.showSuccess != null){

                naviTitleAdapter?.setList(homeVM.naviEntities.keys.toMutableList())

                naviDetailAdapter?.setList(homeVM.naviEntities.values.toMutableList())

                stateLayout?.showContent()
            }
        }
    }

    private fun handleNaviClickListener(): INaviClickListener{

        return object : INaviClickListener {
            override fun onClickNaviText(article: NaviListModel.Article) {

                var linkUrl = article.link

                val bundle = Bundle()
                bundle.putString(URL, linkUrl)
                IntentUtil.navigate(requireContext(), WebActivity::class.java, bundle)
            }
        }
    }
}