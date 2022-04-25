package com.lee0000.WanKotlin.module.home

import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.model.home.SystemListModel

/**
author: Lee
date:   2022/4/24
 */
class SysSubAdapter(layoutResId: Int, data: MutableList<SystemListModel.DataX>?) : BaseQuickAdapter<SystemListModel.DataX, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: SystemListModel.DataX) {

        holder.setText(R.id.tv_group, item.superChapterName +"/"+item.chapterName)
        holder.setText(R.id.tv_author, item.author)
        holder.setText(R.id.tv_title, item.title)
        holder.setText(R.id.tv_time, TimeUtils.getFriendlyTimeSpanByNow(item.publishTime))
    }
}