package com.lee0000.WanKotlin.module.home

import android.os.Bundle
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.URL
import com.lee0000.WanKotlin.model.home.HomeBannerModel
import com.lee0000.WanKotlin.model.home.HomeListModel
import com.lee0000.WanKotlin.model.home.HomeTopListModel
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.module.web.WebActivity
import com.lee0000.WanKotlin.util.IntentUtil
import com.lee0000.WanKotlin.viewModel.HomeVM
import com.lee0000.WanKotlin.widget.itemDecoration.SimpleDividerItemDecoration
import com.lxj.statelayout.StateLayout
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.header.ClassicsHeader
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

        homeAdapter = HomeAdapter(homeVM.entities)
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.addItemDecoration(SimpleDividerItemDecoration(requireContext()))
        recyclerView.adapter = homeAdapter

        homeAdapter?.run {
            if (headerLayoutCount > 0) removeHeaderView(banner!!)
            addHeaderView(banner!!)
            setOnItemClickListener(onItemClick())
        }

        refreshLayout.setRefreshHeader(ClassicsHeader(requireContext()))
        refreshLayout.setRefreshFooter(ClassicsFooter(requireContext()))
        refreshLayout.setOnRefreshListener {
            refresh()
        }
        refreshLayout.setOnLoadMoreListener {
            loadMore()
        }

        scrollToTop(recyclerView, ib_top)
    }

    override fun initData() {

        stateLayout?.showLoading()
        homeVM.getArticleList(HomeVM.ArticleType.HomeAll, true)
        homeVM.halUIState.observe(viewLifecycleOwner){

            if (it.showSuccess != null){

                if (it.isRefresh){
                    banner?.setAdapter(HomeBannerAdapter(it.showSuccess.banner.data))

                    stateLayout?.showContent()
                    refreshLayout.finishRefresh()
                }else{
                    refreshLayout.finishLoadMore()
                }

                homeAdapter?.notifyDataSetChanged()
            }

            if (it.showError != null){
                stateLayout?.showError()
                stateLayout?.mRetryAction = {
                    homeVM.getArticleList(HomeVM.ArticleType.HomeAll, true)
                }
                ToastUtils.showShort(it.showError)
            }
        }
    }

    private fun refresh(){
        homeVM.getArticleList(HomeVM.ArticleType.HomeAll, true)
    }

    private fun loadMore(){
        homeVM.getArticleList(HomeVM.ArticleType.HomeAll, false)
    }

    private fun onItemClick(): OnItemClickListener {

        return OnItemClickListener { adapter, view, position ->

            val clickItem = homeAdapter?.data?.get(position)
            var linkUrl = ""
            if (clickItem is HomeTopListModel.Data){
                linkUrl = clickItem.link
            }else if(clickItem is HomeListModel.DataX){
                linkUrl = clickItem.link
            }

            val bundle = Bundle()
            bundle.putString(URL, linkUrl)
            IntentUtil.navigate(requireContext(), WebActivity::class.java, bundle)
        }
    }
}