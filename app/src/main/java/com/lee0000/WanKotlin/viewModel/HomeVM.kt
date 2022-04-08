package com.lee0000.WanKotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lee0000.WanKotlin.model.home.HomeTopListModel
import com.lee0000.WanKotlin.net.api.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
author: Lee
date:   2022/4/5
 */
class HomeVM: BaseViewModel() {

    private val _htlUIState = MutableLiveData<UiStateModel<HomeTopListModel>>()
    val htlUIState: LiveData<UiStateModel<HomeTopListModel>> = _htlUIState

    private val mService by lazy {
        RetrofitClient.service
    }

    sealed class ArticleType{
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

            }
            ArticleType.SystemTitle -> {

            }
            ArticleType.SystemList -> {

            }
            ArticleType.navigationList -> {

            }
        }
    }

    private fun getHomeBanner(){

        viewModelScope.launch(Dispatchers.Default) {

            val result = mService.getHomeBanner()
            withContext(Dispatchers.Main){

            }
        }
    }

    private fun getHomeTopList(){

        viewModelScope.launch(Dispatchers.Default) {

            val result = mService.getHomeTopList()
            withContext(Dispatchers.Main){
                emitUiState(_htlUIState, false, null, result, false, false)
            }
        }
    }
}