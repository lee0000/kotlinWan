package com.lee0000.WanKotlin.model.home

/**
author: Lee
date:   2022/4/2
msg：    首页-banner
 */
data class HomeBannerModel(
    val data : List<Data>,
    val errorCode: Int,
    val errorMsg: String
){
    data class Data(
        val desc: String,
        val id: Int,
        val imagePath: String,
        val isVisible: Int,
        val order: Int,
        val title: String,
        val type: Int,
        val url: String
    )
}