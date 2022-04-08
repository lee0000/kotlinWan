package com.lee0000.WanKotlin.module.home

import android.util.Log
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseMultiItemQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lee0000.WanKotlin.HOME_HOTLIST_TYPE
import com.lee0000.WanKotlin.HOME_LIST_TYPE
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.model.home.HomeTopListModel


/**
author: Lee
date:   2022/4/8
 */
class HomeAdapter(data: MutableList<MultiItemEntity>?): BaseMultiItemQuickAdapter<MultiItemEntity, BaseViewHolder>(data) {

    init {
        // 绑定 layout 对应的 type
//        addItemType(QuickMultipleEntity.TEXT, R.layout.item_text_view)
        addItemType(HOME_HOTLIST_TYPE, R.layout.wan_item_article)
        addItemType(HOME_LIST_TYPE, R.layout.wan_item_article)
    }

    override fun convert(holder: BaseViewHolder, item: MultiItemEntity) {

        when(holder.itemViewType){

            HOME_HOTLIST_TYPE -> {

                val topListModel = item as HomeTopListModel.Data
                holder.setText(R.id.tv_group, topListModel.superChapterName+"/"+topListModel.chapterName)
                holder.setText(R.id.tv_author, topListModel.author)
                holder.setText(R.id.tv_title, topListModel.title)
                holder.setText(R.id.tv_time, TimeUtils.getFriendlyTimeSpanByNow(topListModel.publishTime))
            }

            HOME_LIST_TYPE -> {

//                val listModel = item as HomeListModel.Data
//                val model = listModel.datas
//                holder.setText(R.id.tv_group, model.super +"/"+listModel.chapterName)
//                holder.setText(R.id.tv_author, listModel.author)
//                holder.setText(R.id.tv_title, listModel.title)
//                holder.setText(R.id.tv_time, listModel.publishTime.toString())
            }
        }
    }
}