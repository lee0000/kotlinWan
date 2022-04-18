package com.lee0000.WanKotlin.module.public

import android.os.Bundle
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.model.home.HomeListModel
import com.lee0000.WanKotlin.model.home.HomeTopListModel
import com.lee0000.WanKotlin.model.pub.PublicListModel
import com.lee0000.WanKotlin.module.base.BaseFragment
import com.lee0000.WanKotlin.module.web.WebActivity
import com.lee0000.WanKotlin.util.IntentUtil
import com.lee0000.WanKotlin.viewModel.PublicVM
import com.lee0000.WanKotlin.widget.itemDecoration.SimpleDividerItemDecoration
import kotlinx.android.synthetic.main.wan_fragment_home.*

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
    }

    override fun initData() {

        publicVM.requestWxArticleList(partId)
        publicVM.plmUIState.observe(viewLifecycleOwner){

            if (it.showSuccess != null){

                publicSubAdapter?.notifyDataSetChanged()
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
}