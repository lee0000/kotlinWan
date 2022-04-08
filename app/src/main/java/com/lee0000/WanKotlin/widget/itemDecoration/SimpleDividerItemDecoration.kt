package com.lee0000.WanKotlin.widget.itemDecoration

import android.content.Context
import android.graphics.Canvas
import android.graphics.drawable.Drawable
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.lee0000.WanKotlin.R

/**
author: Lee
date:   2022/4/8
 */
class SimpleDividerItemDecoration: RecyclerView.ItemDecoration {

    private var diver: Drawable? = null

    constructor(context: Context){
        diver = context.resources.getDrawable(R.drawable.wan_item_line_divider)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        val left: Int = parent.paddingLeft
        val right: Int = parent.width - parent.paddingRight

        val childCount: Int = parent.childCount
        for (i in 0 until childCount) {
            val child: View = parent.getChildAt(i)
            val params = child.layoutParams as RecyclerView.LayoutParams
            val top: Int = child.bottom + params.bottomMargin
            val bottom: Int = top + diver?.intrinsicHeight!!
            diver?.setBounds(left, top, right, bottom)
            diver?.draw(c)
        }
    }
}