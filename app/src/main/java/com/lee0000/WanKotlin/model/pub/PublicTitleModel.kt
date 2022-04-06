package com.lee0000.WanKotlin.model.pub

/**
author: Lee
date:   2022/4/2
msg:    公众号-公众号列表
 */
data class PublicTitleModel(
    val data: List<Data>,
    val errorCode: Int,
    val errorMsg: String
){
    data class Data(
        val author: String,
        val children: List<Any>,
        val courseId: Int,
        val cover: String,
        val desc: String,
        val id: Int,
        val lisense: String,
        val lisenseLink: String,
        val name: String,
        val order: Int,
        val parentChapterId: Int,
        val userControlSetTop: Boolean,
        val visible: Int
    )
}