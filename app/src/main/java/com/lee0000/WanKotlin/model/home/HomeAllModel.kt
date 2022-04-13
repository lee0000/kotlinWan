package com.lee0000.WanKotlin.model.home

import com.chad.library.adapter.base.entity.MultiItemEntity

/**
author: Lee
date:   2022/4/11
 */
data class HomeAllModel(val banner : HomeBannerModel,
                        var normalList: MutableList<MultiItemEntity>)
