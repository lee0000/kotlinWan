package com.lee0000.WanKotlin.module.base

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.lxj.statelayout.StateLayout

/**
author: Lee
date:   2022/3/31
 */
abstract class BaseFragment: Fragment() {

    private var fragmentView: View? = null
    // 加载的loading layout
    protected var stateLayout: StateLayout? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        if (fragmentView == null){
            fragmentView = inflater.inflate(getLayoutResId(), container, false)
            stateLayout = StateLayout(requireContext()).wrap(fragmentView).showContent()
        }

        return stateLayout
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