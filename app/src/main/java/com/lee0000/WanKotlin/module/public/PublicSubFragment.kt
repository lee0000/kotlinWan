package com.lee0000.WanKotlin.module.public

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
import com.lee0000.WanKotlin.module.web.WebActivity
import com.lee0000.WanKotlin.util.IntentUtil
import com.lee0000.WanKotlin.viewModel.PublicVM
import com.lee0000.WanKotlin.widget.itemDecoration.SimpleDividerItemDecoration
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
import kotlinx.android.synthetic.main.wan_fragment_home.*
import kotlinx.coroutines.launch

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

        stateLayout?.showLoading()
        publicVM.requestWxArticleList(true, partId)
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
                publicVM.plmUIState.collect{

                    if (it.showSuccess != null) {

                        if (it.isRefresh){

                            stateLayout?.showContent()
                            refreshLayout.finishRefresh()
                        }else{
                            refreshLayout.finishLoadMore()
                        }

                        publicSubAdapter?.notifyDataSetChanged()
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
            bundle.putString(URL, linkUrl)
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