package com.lee0000.WanKotlin.viewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
author: Lee
date:   2022/4/5
 */
abstract class BaseViewModel: ViewModel(){

    protected fun <T> emitUiState(
        stateUILiveData: MutableLiveData<UiStateModel<T>>,
        showLoading: Boolean = false,
        showError: String? = null,
        showSuccess: T? = null,
        showEnd: Boolean = false,
        isRefresh: Boolean = false
    ) {
        val uiModel = UiStateModel(showLoading, showError, showSuccess, showEnd, isRefresh)
        stateUILiveData.value = uiModel
    }
}

data class UiStateModel<T>(
    val showLoading: Boolean,
    val showError: String?,
    val showSuccess: T?,
    val showEnd: Boolean, // 加载更多
    val isRefresh: Boolean, // 刷新
)