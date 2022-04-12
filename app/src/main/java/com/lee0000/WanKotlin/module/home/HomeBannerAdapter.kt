package com.lee0000.WanKotlin.module.home

import android.view.View
import android.view.ViewGroup
import com.lee0000.WanKotlin.model.home.HomeBannerModel
import com.youth.banner.adapter.BannerAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lee0000.WanKotlin.R
import kotlinx.android.synthetic.main.wan_item_banner.view.*

/**
author: Lee
date:   2022/4/12
 */
class HomeBannerAdapter: BannerAdapter<HomeBannerModel.Data, HomeBannerAdapter.BannerViewHolder> {

    constructor( bannerData: List<HomeBannerModel.Data>) : super(bannerData) {

    }

    override fun onCreateHolder(parent: ViewGroup?, viewType: Int): BannerViewHolder {

        val layoutView = View.inflate(parent?.context, R.layout.wan_item_banner, null)

        // viewpager2必须添加，否则报 Pages must fill the whole ViewPager2
        val lp = ViewGroup.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.MATCH_PARENT
        )
        layoutView.layoutParams = lp

        return BannerViewHolder(layoutView)
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: HomeBannerModel.Data?,
        position: Int,
        size: Int
    ) {

        Glide.with(holder?.itemView!!).load(data?.imagePath).into(holder?.bannerIv!!)
//        holder?.bannerTv?.text = data?.title
    }

    class BannerViewHolder(layoutView: View) : RecyclerView.ViewHolder(layoutView) {

        val bannerIv = layoutView.iv_banner
        val bannerTv = layoutView.tv_banner
    }
}