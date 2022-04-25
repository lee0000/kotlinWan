package com.lee0000.WanKotlin.module.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lee0000.WanKotlin.R

/**
author: Lee
date:   2022/4/24
 */
class NaviTitleAdapter(layoutResId: Int, data: MutableList<String>?): BaseQuickAdapter<String, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: String) {

        holder.setText(R.id.mtv_title, item)
    }
}