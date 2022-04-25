package com.lee0000.WanKotlin.viewModel

import androidx.lifecycle.viewModelScope
import com.lee0000.WanKotlin.model.pub.PublicListModel
import com.lee0000.WanKotlin.model.pub.PublicTitleModel
import com.lee0000.WanKotlin.net.repository.PublicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
author: Lee
date:   2022/4/5
 */
class PublicVM : BaseViewModel() {

    private val repository by lazy { PublicRepository() }

    val titleEntities = arrayListOf<PublicTitleModel.Data>()
    val entities = arrayListOf<PublicListModel.DataX>()

    private val _ptmUIState = MutableStateFlow<UiStateModel<PublicTitleModel>>(UiStateModel(false, null, null, false, false))
    val ptmUIState: StateFlow<UiStateModel<PublicTitleModel>> = _ptmUIState.asStateFlow()

    private val _plmUIState = MutableStateFlow<UiStateModel<PublicListModel>>(UiStateModel(false, null, null, false, false))
    val plmUIState: StateFlow<UiStateModel<PublicListModel>> = _plmUIState.asStateFlow()

    // 页面page, 公众号文章都是从index 1开始
    private var page = 1

    fun requestWxArticle() {

        viewModelScope.launch(Dispatchers.Default) {

            emitUiStateByFlow(_ptmUIState, true, null, null, false, false)
            val result = repository.fetchWxArticleTitle()
            withContext(Dispatchers.Main){
                if (result == null) {
                    emitUiStateByFlow(_ptmUIState,false, null, null, false, false)
                } else {
                    emitUiStateByFlow(_ptmUIState,false, null, result, false, false)
                }
            }
        }
    }

    fun requestWxArticleList(refresh: Boolean, cid: Int){

        viewModelScope.launch(Dispatchers.Default) {

            emitUiStateByFlow(_plmUIState, true, null, null, true, false)

            if (refresh){
                page = 1
            }else{
                page++
            }

            val result = repository.fetchWxArticleList(cid, page)
            withContext(Dispatchers.Main){
                if (result == null) {
                    emitUiStateByFlow(_plmUIState, false, null, null, true, false)
                } else {

                    if (refresh){// 刷新
                        entities.clear()
                        entities.addAll(result.data.datas)

                        emitUiStateByFlow(_plmUIState, false, null, result, false, true)
                    }else{
                        entities.addAll(result.data.datas)
                        emitUiStateByFlow(_plmUIState, false, null, result, false, false)
                    }
                }
            }
        }
    }
}