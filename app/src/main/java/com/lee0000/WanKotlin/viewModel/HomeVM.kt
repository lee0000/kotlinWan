package com.lee0000.WanKotlin.viewModel

import androidx.lifecycle.*
import com.blankj.utilcode.util.NetworkUtils
import com.chad.library.adapter.base.entity.MultiItemEntity
import com.lee0000.WanKotlin.model.home.*
import com.lee0000.WanKotlin.net.repository.HomeRepository
import kotlinx.coroutines.*

/**
author: Lee
date:   2022/4/5
 */
class HomeVM: BaseViewModel(), LifecycleObserver{

    private var homeAllModel: HomeAllModel? = null

    // 首页数据列表
    val entities = arrayListOf<MultiItemEntity>()
    // 首页体系数据列表
    val sysEntities = arrayListOf<SystemTitleModel.Data>()
    // 首页导航数据列表
    val naviEntities = LinkedHashMap<String, List<NaviListModel.Article>>()
    // 首页体系下列表数据列表
    val sysSubEntities = arrayListOf<SystemListModel.DataX>()

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
    private val _hnlUIState = MutableLiveData<UiStateModel<LinkedHashMap<String, List<NaviListModel.Article>>>>()
    val hnlUIState: LiveData<UiStateModel<LinkedHashMap<String, List<NaviListModel.Article>>>> = _hnlUIState

    // 首页列表page
    var homeListCurPage: Int = 0
    // 体系列表page
    var sysSubListCurPage: Int = 0

    private val homeRepository = HomeRepository()

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

        if (!NetworkUtils.isConnected()) {
            emitUiStateByLiveData(_halUIState,"网络连接错误", null, false, false)
            return
        }

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
                getSystemList(isRefresh, cid)
            }
            ArticleType.NavigationList -> {
                getNavigationList()
            }
        }

    }

    private fun getHomeBanner(): Deferred<HomeBannerModel> {

        return viewModelScope.async {
            homeRepository.fetchHomeBanner()
        }
    }

    private fun getHomeTopList(): Deferred<HomeTopListModel> {

        return viewModelScope.async {
            homeRepository.fetchHomeTopList()
        }
    }

    private fun getHomeList(isRefresh: Boolean): Deferred<HomeListModel> {

        return viewModelScope.async{
            if (isRefresh){
                homeListCurPage = 0
                homeRepository.fetchHomeList(homeListCurPage)
            }else{
                homeListCurPage++
                homeRepository.fetchHomeList(homeListCurPage)
            }
        }
    }

    private fun getHomeAll(isRefresh: Boolean){

        emitUiStateByLiveData(_halUIState,null, null, false, false)

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
            emitUiStateByLiveData(_halUIState, null, homeAllModel, false, isRefresh)
        }
    }

    private fun getSystemTitle(){

        viewModelScope.launch {
            val systemTitleModel = homeRepository.fetchSystemTitle()
            sysEntities.clear()
            sysEntities.addAll(systemTitleModel.data)

            for (index in systemTitleModel.data.indices){

                val data = systemTitleModel.data[index]
                val subTitleBuffer = StringBuffer()
                data.children.map { child ->
                    subTitleBuffer.append(child.name)
                    subTitleBuffer.append("     ")
                }
                data.subTitle = subTitleBuffer.toString()
            }

            emitUiStateByLiveData(_hstUIState, null, systemTitleModel, false, false)
        }
    }

    private fun getSystemList(refresh: Boolean, cid: Int){

        viewModelScope.launch {

            if (refresh){
                sysSubListCurPage = 0
            }else{
                sysSubListCurPage++
            }

            val result = homeRepository.fetchSystemList(cid, sysSubListCurPage)
            if (result == null) {
                emitUiStateByLiveData(_hslUIState, null, null, true, false)
            } else {

                if (refresh){// 刷新
                    sysSubEntities.clear()
                    sysSubEntities.addAll(result.data.datas)

                    emitUiStateByLiveData(_hslUIState, null, result, false, true)
                }else{
                    sysSubEntities.addAll(result.data.datas)
                    emitUiStateByLiveData(_hslUIState, null, result, false, false)
                }
            }
        }
    }

    private fun getNavigationList(){

        viewModelScope.launch {
            val naviListModel = homeRepository.fetchNaviList()

            naviListModel.data.map {
                if (it.articles.isNotEmpty()){
                    naviEntities[it.name] = it.articles
                }
            }

            emitUiStateByLiveData(_hnlUIState, null, naviEntities, false, false)
        }
    }
}