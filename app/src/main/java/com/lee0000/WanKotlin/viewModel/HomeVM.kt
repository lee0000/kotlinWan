package com.lee0000.WanKotlin.viewModel

import androidx.lifecycle.*
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lee0000.WanKotlin.model.home.*
import com.lee0000.WanKotlin.net.api.RetrofitClient
import kotlinx.coroutines.*

/**
author: Lee
date:   2022/4/5
 */
class HomeVM: BaseViewModel(), LifecycleObserver{

    private var homeAllModel: HomeAllModel? = null

    val entities = arrayListOf<MultiItemEntity>()

    // 首页
    private val _halUIState = MutableLiveData<UiStateModel<HomeAllModel>>()
    val halUIState: LiveData<UiStateModel<HomeAllModel>> = _halUIState

    // 首页-体系Title
    private val _hstUIState = MutableLiveData<UiStateModel<SystemTitleModel>>()
    val hstUIState: LiveData<UiStateModel<SystemTitleModel>> = _hstUIState

    // 首页-体系List
    private val _hslUIState = MutableLiveData<UiStateModel<SystemListModel>>()
    val hslUIState: LiveData<UiStateModel<SystemListModel>> = _hslUIState

    // 首页-导航
    private val _hnlUIState = MutableLiveData<UiStateModel<NaviListModel>>()
    val hnlUIState: LiveData<UiStateModel<NaviListModel>> = _hnlUIState

    // 首页列表page
    var homeListCurPage: Int = 0

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
        object NavigationList: ArticleType()
    }

    fun getArticleList(articleType: ArticleType, isRefresh: Boolean = false, cid: Int = 0){

        when(articleType){

            ArticleType.HomeBanner -> {
                getHomeBanner()
            }
            ArticleType.HomeTopList -> {
                getHomeTopList()
            }
            ArticleType.HomeList -> {
                getHomeList(isRefresh)
            }
            ArticleType.HomeAll -> {
                getHomeAll(isRefresh)
            }
            ArticleType.SystemTitle -> {
                getSystemTitle()
            }
            ArticleType.SystemList -> {
                getSystemList(cid)
            }
            ArticleType.NavigationList -> {
                getNavigationList()
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

    private fun getHomeList(isRefresh: Boolean): Deferred<HomeListModel> {

        return viewModelScope.async{
            if (isRefresh){
                homeListCurPage = 0
                mService.getHomeList(homeListCurPage)
            }else{
                homeListCurPage++
                mService.getHomeList(homeListCurPage)
            }
        }
    }

    private fun getHomeAll(isRefresh: Boolean){

        viewModelScope.launch {

            if (isRefresh){
                val banner = getHomeBanner().await()
                val homeTopList = getHomeTopList().await()
                val homeList = getHomeList(isRefresh).await()

                entities.clear()
                entities.addAll(homeTopList.data)
                entities.addAll(homeList.data.datas)

                homeAllModel = HomeAllModel(banner, entities)

            }else{
                val nextHomeList = getHomeList(isRefresh).await()
                homeAllModel?.normalList!!.addAll(nextHomeList.data.datas)
            }
            emitUiState(_halUIState, false, null, homeAllModel, false, isRefresh)
        }
    }

    private fun getSystemTitle(){

        viewModelScope.launch {
            val systemTitleModel = mService.getSystem()
            emitUiState(_hstUIState, false, null, systemTitleModel, false, false)
        }
    }

    private fun getSystemList(cid: Int){
        viewModelScope.launch {
            val systemListModel = mService.getSystemList(cid, 0)
            emitUiState(_hslUIState, false, null, systemListModel, false, false)
        }
    }

    private fun getNavigationList(){

        viewModelScope.launch {
            val naviListModel = mService.getNaviList()
            emitUiState(_hnlUIState, false, null, naviListModel, false, false)
        }
    }
}