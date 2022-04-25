package com.lee0000.WanKotlin.listener

import com.lee0000.WanKotlin.model.home.NaviListModel

/**
author: Lee
date:   2022/4/24
 */
interface INaviClickListener {

    fun onClickNaviText(article: NaviListModel.Article)
}