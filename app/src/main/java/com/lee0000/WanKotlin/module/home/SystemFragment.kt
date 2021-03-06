package com.lee0000.WanKotlin.module.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.SUB_PUBLIC_MODEL
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.util.IntentUtil
import com.lee0000.WanKotlin.viewModel.HomeVM
import com.lee0000.WanKotlin.widget.itemDecoration.SimpleDividerItemDecoration
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.wan_fragment_home.recyclerView
import kotlinx.android.synthetic.main.wan_fragment_home.refreshLayout
import java.io.Serializable

/**
author: Lee
date:   2022/4/7
 */
class SystemFragment: BaseFragment() {

    private val homeVM by viewModels<HomeVM>()

    private var systemAdapter: SystemAdapter? = null

    override fun getLayoutResId(): Int {
        return R.layout.wan_fragment_system
    }

    override fun initView() {

        systemAdapter = SystemAdapter(R.layout.wan_item_system, homeVM.sysEntities)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(SimpleDividerItemDecoration(requireContext()))
        recyclerView.adapter = systemAdapter

        systemAdapter?.setOnItemClickListener(onItemClick())

        refreshLayout.setRefreshHeader(ClassicsHeader(requireContext()))
        refreshLayout.setOnRefreshListener {
            refresh()
        }
    }

    override fun initData() {

        stateLayout?.showLoading()
        homeVM.getArticleList(HomeVM.ArticleType.SystemTitle)
        homeVM.hstUIState.observe(viewLifecycleOwner){

            systemAdapter?.notifyDataSetChanged()

            stateLayout?.showContent()
            refreshLayout.finishRefresh()
        }
    }

    private fun onItemClick(): OnItemClickListener {

        return OnItemClickListener { adapter, view, position ->

            val clickItem = systemAdapter?.data?.get(position)

            val bundle = Bundle()
            bundle.putSerializable(SUB_PUBLIC_MODEL, clickItem as Serializable)
            IntentUtil.navigate(requireContext(), SystemDetailActivity::class.java, bundle)
        }
    }

    private fun refresh(){

        homeVM.getArticleList(HomeVM.ArticleType.SystemTitle)
    }
}