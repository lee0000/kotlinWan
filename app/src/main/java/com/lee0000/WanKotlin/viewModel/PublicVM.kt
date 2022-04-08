package com.lee0000.WanKotlin.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.lee0000.WanKotlin.model.pub.PublicListModel
import com.lee0000.WanKotlin.model.pub.PublicTitleModel
import com.lee0000.WanKotlin.net.repository.PublicRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
author: Lee
date:   2022/4/5
 */
class PublicVM : BaseViewModel() {

    private val repository by lazy { PublicRepository() }

    private val _ptmUIState = MutableLiveData<UiStateModel<PublicTitleModel>>()
    val ptmUIState: LiveData<UiStateModel<PublicTitleModel>> = _ptmUIState

    private val _plmUIState = MutableLiveData<UiStateModel<PublicListModel>>()
    val plmUIState: LiveData<UiStateModel<PublicListModel>> = _plmUIState

    fun requestWxArticle() {

        emitUiState(_ptmUIState,true, null, null, false, false)

        viewModelScope.launch(Dispatchers.Default) {

            val result = repository.fetchWxArticleTitle()
            withContext(Dispatchers.Main){
                if (result == null) {
                    emitUiState(_ptmUIState,false, null, null, false, false)
                } else {
                    emitUiState(_ptmUIState,false, null, result, false, false)
                }

                emitUiState(_ptmUIState, false, null, null, true, false)

            }
        }
    }

    fun requestWxArticleList(){

//        viewModelScope.launch(Dispatchers.Default) {
//
//            val result = repository.fetchWxArticleList()
//            withContext(Dispatchers.Main){
//                if (result == null) {
//                    emitUiState(false, null, null, false, false)
//                } else {
//                    emitUiState(false, null, result, false, false)
//                }
//
//                emitUiState(false, null, null, true, false)
//
//            }
//        }
    }
}