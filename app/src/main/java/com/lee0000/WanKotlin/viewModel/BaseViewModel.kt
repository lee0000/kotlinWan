package com.lee0000.WanKotlin.viewModel

import androidx.lifecycle.ViewModel

/**
author: Lee
date:   2022/4/5
 */
abstract class BaseViewModel: ViewModel()

sealed class ViewStatus{
    data class ShowLoading(val isShow: Boolean): ViewStatus()
    data class ShowToast(val message: String): ViewStatus()
    data class Success<T>(val data: T): ViewStatus()

    object Empty: ViewStatus()
}