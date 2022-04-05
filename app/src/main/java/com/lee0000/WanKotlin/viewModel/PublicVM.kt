package com.lee0000.WanKotlin.viewModel

import com.lee0000.WanKotlin.model.public.PublicTitleModel
import com.lee0000.WanKotlin.net.api.ApiResponse
import com.lee0000.WanKotlin.net.repository.PublicRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

/**
author: Lee
date:   2022/4/5
 */
class PublicVM: BaseViewModel() {

    private val repository by lazy { PublicRepository() }

    private val _uiState = MutableStateFlow<ApiResponse<List<PublicTitleModel>>>(ApiResponse())
    val uiState: StateFlow<ApiResponse<List<PublicTitleModel>>> = _uiState.asStateFlow()

    
}