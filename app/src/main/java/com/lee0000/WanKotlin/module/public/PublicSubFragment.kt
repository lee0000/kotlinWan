package com.lee0000.WanKotlin.module.public

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.RecycledViewPool
import androidx.recyclerview.widget.SimpleItemAnimator
import com.blankj.utilcode.util.ThreadUtils
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.module.home.HomeBannerAdapter
import com.lee0000.WanKotlin.module.web.WebActivity
import com.lee0000.WanKotlin.util.IntentUtil
import com.lee0000.WanKotlin.viewModel.HomeVM
import com.lee0000.WanKotlin.viewModel.PublicVM
import com.lee0000.WanKotlin.widget.itemDecoration.SimpleDividerItemDecoration
import com.lxj.statelayout.StateLayout
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.wan_activity_web.*
import kotlinx.android.synthetic.main.wan_fragment_home.*
import kotlinx.coroutines.launch
import com.lee0000.WanKotlin.module.*




/**
author: Lee
date:   2022/4/18
 */
class PublicSubFragment(private val partId: Int): BaseFragment() {

    private var publicSubAdapter: PublicSubAdapter? = null

    private val publicVM by viewModels<PublicVM>()

    override fun getLayoutResId(): Int {
        return R.layout.wan_fragment_home
    }

    override fun initView() {

        publicSubAdapter = PublicSubAdapter(R.layout.wan_item_article, publicVM.entities)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(SimpleDividerItemDecoration(requireContext()))
        recyclerView.adapter = publicSubAdapter

        publicSubAdapter?.setOnItemClickListener(onItemClick())

        refreshLayout.setRefreshHeader(ClassicsHeader(requireContext()))
        refreshLayout.setRefreshFooter(ClassicsFooter(requireContext()))
        refreshLayout.setOnRefreshListener {
            refresh()
        }
        refreshLayout.setOnLoadMoreListener {
            loadMore()
        }
    }

    override fun initData() {

        publicVM.requestWxArticleList(true, partId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                publicVM.plmUIState.collect{

                    if (it.showLoading) {

                        stateLayout?.showLoading()
                        publicVM.titleEntities.clear()
                    }

                    if (it.showSuccess != null) {

                        if (it.isRefresh){

                            refreshLayout.finishRefresh()
                        }else{
                            refreshLayout.finishLoadMore()
                        }

                        publicSubAdapter?.notifyDataSetChanged()
                        ThreadUtils.runOnUiThreadDelayed({
                            stateLayout?.showContent()
                        }, 1000)
                    }
                }
            }
        }
    }

    private fun onItemClick(): OnItemClickListener {

        return OnItemClickListener { adapter, view, position ->

            val clickItem = publicSubAdapter?.data?.get(position)
            var linkUrl = clickItem?.link

            val bundle = Bundle()
            bundle.putString("url", linkUrl)
            IntentUtil.navigate(requireContext(), WebActivity::class.java, bundle)
        }
    }

    private fun refresh(){
        publicVM.requestWxArticleList(true, partId)
    }

    private fun loadMore(){
        publicVM.requestWxArticleList(false, partId)
    }
}