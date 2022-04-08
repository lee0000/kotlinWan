package com.lee0000.WanKotlin.module.home

import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.viewModel.HomeVM
import com.lee0000.WanKotlin.widget.itemDecoration.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.wan_fragment_home.*

/**
author: Lee
date:   2022/4/7
 */
class HomeFragment: BaseFragment() {

    private val homeVM by viewModels<HomeVM>()

    private var homeAdapter: HomeAdapter? = null

    private val entities = arrayListOf<MultiItemEntity>()

    override fun getLayoutResId(): Int {
        return R.layout.wan_fragment_home
    }

    override fun initView() {

        homeAdapter = HomeAdapter(entities)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(SimpleDividerItemDecoration(requireContext()))
        recyclerView.adapter = homeAdapter
    }

    override fun initData() {

        homeVM.getArticleList(HomeVM.ArticleType.HomeTopList, false)
        homeVM.htlUIState.observe(viewLifecycleOwner){

            if (it.showSuccess != null){
                entities.addAll(it.showSuccess!!.data)

                homeAdapter?.setList(entities)
            }
        }
    }
}