package com.lee0000.WanKotlin.module.home

import android.widget.TextView
import com.blankj.utilcode.util.ConvertUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.lee0000.WanKotlin.R
import com.lee0000.WanKotlin.listener.INaviClickListener
import com.lee0000.WanKotlin.model.home.NaviListModel
import com.nex3z.flowlayout.FlowLayout

/**
author: Lee
date:   2022/4/24
 */
class NaviDetailAdapter(layoutResId: Int, data: MutableList<List<NaviListModel.Article>>, val listener: INaviClickListener): BaseQuickAdapter<List<NaviListModel.Article>, BaseViewHolder>(layoutResId, data) {

    override fun convert(holder: BaseViewHolder, item: List<NaviListModel.Article>) {

        if (item.isNotEmpty()){
            holder.setText(R.id.navigationName, item[0].chapterName)

            item.map {
                val textView = TextView(context)
                textView.text = it.title
                textView.textSize = ConvertUtils.dp2px(7f).toFloat()
                textView.setTextColor(context.resources.getColor(R.color.wan_sub_article_text_color))
                textView.setOnClickListener { tv ->
                    listener.onClickNaviText(it)
                }
                holder.getViewOrNull<FlowLayout>(R.id.flowLayout)?.addView(textView)
            }
        }
    }
}