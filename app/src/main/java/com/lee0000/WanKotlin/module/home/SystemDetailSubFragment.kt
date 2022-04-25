package com.lee0000.WanKotlin.module.home

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.URL
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.module.public.PublicSubAdapter
import com.lee0000.WanKotlin.module.web.WebActivity
import com.lee0000.WanKotlin.util.IntentUtil
import com.lee0000.WanKotlin.viewModel.HomeVM
import com.lee0000.WanKotlin.viewModel.PublicVM
import com.lee0000.WanKotlin.widget.itemDecoration.SimpleDividerItemDecoration
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.wan_fragment_home.*
import kotlinx.coroutines.launch

/**
author: Lee
date:   2022/4/24
 */
class SystemDetailSubFragment(private val partId: Int): BaseFragment() {

    private var sysSubAdapter: SysSubAdapter? = null

    private val homeVM by viewModels<HomeVM>()

    override fun getLayoutResId(): Int {
        return R.layout.wan_fragment_home
    }

    override fun initView() {

        sysSubAdapter = SysSubAdapter(R.layout.wan_item_article, homeVM.sysSubEntities)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(SimpleDividerItemDecoration(requireContext()))
        recyclerView.adapter = sysSubAdapter

        sysSubAdapter?.setOnItemClickListener(onItemClick())

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

        stateLayout?.showLoading()
        homeVM.getArticleList(HomeVM.ArticleType.SystemList, true, partId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                homeVM.hslUIState.observe(viewLifecycleOwner){

                    if (it.showSuccess != null) {

                        if (it.isRefresh){

                            stateLayout?.showContent()
                            refreshLayout.finishRefresh()
                        }else{
                            refreshLayout.finishLoadMore()
                        }

                        sysSubAdapter?.notifyDataSetChanged()
                    }
                }
            }
        }
    }

    private fun onItemClick(): OnItemClickListener {

        return OnItemClickListener { adapter, view, position ->

            val clickItem = sysSubAdapter?.data?.get(position)
            var linkUrl = clickItem?.link

            val bundle = Bundle()
            bundle.putString(URL, linkUrl)
            IntentUtil.navigate(requireContext(), WebActivity::class.java, bundle)
        }
    }

    private fun refresh(){
        homeVM.getArticleList(HomeVM.ArticleType.SystemList, true, partId)
    }

    private fun loadMore(){
        homeVM.getArticleList(HomeVM.ArticleType.SystemList, false, partId)
    }
}