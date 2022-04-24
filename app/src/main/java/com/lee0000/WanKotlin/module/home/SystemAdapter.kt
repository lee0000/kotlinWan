package com.lee0000.WanKotlin.module.home

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.model.home.SystemTitleModel

/**
author: Lee
date:   2022/4/22
 */
class SystemAdapter(layoutResId: Int, data: ArrayList<SystemTitleModel.Data>?): BaseQuickAdapter<SystemTitleModel.Data, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: SystemTitleModel.Data) {

        holder.setText(R.id.mtv_title, item.name)
        holder.setText(R.id.mtv_content, item.subTitle)
    }
}