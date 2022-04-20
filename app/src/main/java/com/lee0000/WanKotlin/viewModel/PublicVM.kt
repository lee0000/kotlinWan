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

    fun requestWxArticle() {

        viewModelScope.launch(Dispatchers.Default) {

            val result = repository.fetchWxArticleTitle()
            withContext(Dispatchers.Main){
                if (result == null) {
                    emitUiStateByFlow(_ptmUIState,false, null, null, false, false)
                } else {
                    emitUiStateByFlow(_ptmUIState,false, null, result, false, false)
                }

                emitUiStateByFlow(_ptmUIState, false, null, null, true, false)

            }
        }
    }

    fun requestWxArticleList(cid: Int){

        emitUiStateByFlow(_plmUIState,true, null, null, false, false)

        viewModelScope.launch(Dispatchers.Default) {

            val result = repository.fetchWxArticleList(cid, 0)
            withContext(Dispatchers.Main){
                if (result == null) {
                    emitUiStateByFlow(_plmUIState, false, null, null, false, false)
                } else {

                    entities.clear()
                    entities.addAll(result.data.datas)
                    emitUiStateByFlow(_plmUIState, false, null, result, false, false)
                }

                emitUiStateByFlow(_plmUIState, false, null,null, true, false)
            }
        }
    }
}