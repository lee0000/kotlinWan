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

    private val _ptmUIState = MutableLiveData<PublicUiModel<PublicTitleModel>>()
    val ptmUIState: LiveData<PublicUiModel<PublicTitleModel>> = _ptmUIState

    private val _plmUIState = MutableLiveData<PublicUiModel<PublicListModel>>()
    val plmUIState: LiveData<PublicUiModel<PublicListModel>> = _plmUIState

    fun requestWxArticle() {

        emitUiState(true, null, null, false, false)

        viewModelScope.launch(Dispatchers.Default) {

            val result = repository.fetchWxArticleTitle()
            withContext(Dispatchers.Main){
                if (result == null) {
                    emitUiState(false, null, null, false, false)
                } else {
                    emitUiState(false, null, result, false, false)
                }

                emitUiState(false, null, null, true, false)

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

    private fun emitUiState(
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: PublicTitleModel? = null,
        showEnd: Boolean = false,
        isRefresh: Boolean = false,
    ) {
        val uiModel = PublicUiModel(showLoading, showError, showSuccess, showEnd, isRefresh)
        _ptmUIState.value = uiModel
    }

    data class PublicUiModel<T>(
        val showLoading: Boolean,
        val showError: String?,
        val showSuccess: T?,
        val showEnd: Boolean, // 加载更多
        val isRefresh: Boolean, // 刷新
    )
}