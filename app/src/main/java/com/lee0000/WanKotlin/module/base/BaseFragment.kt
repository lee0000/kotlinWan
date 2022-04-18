package com.lee0000.WanKotlin.module.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView

/**
author: Lee
date:   2022/3/31
 */
abstract class BaseFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(getLayoutResId(), container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initView()
        initData()
        super.onViewCreated(view, savedInstanceState)
    }

    /**
     * 滑动为顶部
     * */
    protected fun scrollToTop(recyclerView: RecyclerView, topButton: ImageButton){

        if (recyclerView == null || topButton == null){
            throw RuntimeException("RecyclerView或者ImageButton不能为空")
        }

        recyclerView.setOnScrollChangeListener { v, scrollX, scrollY, oldScrollX, oldScrollY ->
            if(recyclerView.computeVerticalScrollOffset() > 500){
                topButton.visibility = View.VISIBLE
            }else{
                topButton.visibility = View.GONE
            }
        }

        topButton.setOnClickListener {
            recyclerView.scrollToPosition(0)
        }
    }

    abstract fun getLayoutResId(): Int

    abstract fun initView()

    abstract fun initData()
}