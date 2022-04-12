package com.lee0000.WanKotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lee0000.WanKotlin.model.home.HomeAllModel
import com.lee0000.WanKotlin.model.home.HomeBannerModel
import com.lee0000.WanKotlin.model.home.HomeListModel
import com.lee0000.WanKotlin.model.home.HomeTopListModel
import com.lee0000.WanKotlin.net.api.RetrofitClient
import kotlinx.coroutines.*

/**
author: Lee
date:   2022/4/5
 */
class HomeVM: BaseViewModel() {

    private val _halUIState = MutableLiveData<UiStateModel<HomeAllModel>>()
    val halUIState: LiveData<UiStateModel<HomeAllModel>> = _halUIState

    private val mService by lazy {
        RetrofitClient.service
    }

    sealed class ArticleType{

        object HomeAll: ArticleType()
        object HomeBanner: ArticleType()
        object HomeTopList: ArticleType()
        object HomeList: ArticleType()
        object SystemTitle: ArticleType()
        object SystemList: ArticleType()
        object navigationList: ArticleType()
    }

    fun getArticleList(articleType: ArticleType, isRefresh: Boolean){

        when(articleType){

            ArticleType.HomeBanner -> {
                getHomeBanner()
            }
            ArticleType.HomeTopList -> {
                getHomeTopList()
            }
            ArticleType.HomeList -> {
                getHomeList()
            }
            ArticleType.HomeAll -> {
                getHomeAll()
            }
            ArticleType.SystemTitle -> {

            }
            ArticleType.SystemList -> {

            }
            ArticleType.navigationList -> {

            }
        }
    }

    private fun getHomeBanner(): Deferred<HomeBannerModel> {

        return viewModelScope.async {
            mService.getHomeBanner()
        }
    }

    private fun getHomeTopList(): Deferred<HomeTopListModel> {

        return viewModelScope.async {
            mService.getHomeTopList()
        }
    }

    private fun getHomeList(): Deferred<HomeListModel> {

        return viewModelScope.async{
            mService.getHomeList(0)
        }
    }

    private fun getHomeAll(){

        viewModelScope.launch {
            val banner = getHomeBanner().await()
            val homeTopList = getHomeTopList().await()
            val homeList = getHomeList().await()

            val homeAllModel = HomeAllModel(banner, homeTopList, homeList)
            emitUiState(_halUIState, false, null, homeAllModel, false, false)
        }
    }
}