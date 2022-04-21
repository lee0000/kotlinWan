package com.lee0000.WanKotlin.module.public

import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.model.pub.PublicListModel

/**
author: Lee
date:   2022/4/7
 */
class PublicSubAdapter(layoutResId: Int, data: MutableList<PublicListModel.DataX>?) : BaseQuickAdapter<PublicListModel.DataX, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: PublicListModel.DataX) {

        holder.setText(R.id.tv_group, item.superChapterName +"/"+item.chapterName)
        holder.setText(R.id.tv_author, item.author)
        holder.setText(R.id.tv_title, item.title)
        holder.setText(R.id.tv_time, TimeUtils.getFriendlyTimeSpanByNow(item.publishTime))
    }
}