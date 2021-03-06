package com.lee0000.WanKotlin.model.home

import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lee0000.WanKotlin.HOME_HOTLIST_TYPE

/**
author: Lee
date:   2022/4/2
msg：    首页-置顶文章
 */
data class HomeTopListModel(
    val data : List<Data>,
    val errorCode: Int,
    val errorMsg: String
) {
    data class Data(
        val apkLink: String,
        val audit: Int,
        val author: String,
        val canEdit: Boolean,
        val chapterId: Int,
        val chapterName: String,
        val collect: Boolean,
        val courseId: Int,
        val desc: String,
        val descMd: String,
        val envelopePic: String,
        val fresh: Boolean,
        val host: String,
        val id: Int,
        val link: String,
        val niceDate: String,
        val niceShareDate: String,
        val origin: String,
        val prefix: String,
        val projectLink: String,
        val publishTime: Long,
        val realSuperChapterId: Int,
        val selfVisible: Int,
        val shareDate: Long,
        val shareUser: String,
        val superChapterId: Int,
        val superChapterName: String,
        val tags: List<Tag>,
        val title: String,
        val type: Int,
        val userId: Int,
        val visible: Int,
        val zan: Int
    ) : MultiItemEntity{
        override val itemType: Int
            get() = HOME_HOTLIST_TYPE
    }

    data class Tag(
        val name: String,
        val url: String
    )
}
