package com.example.rrqstore.ui.theme.screen.detail

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rrqstore.data.RrqRepository
import com.example.rrqstore.model.Merch
import com.example.rrqstore.model.MerchOrder
import com.example.rrqstore.ui.theme.common.StateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class DetailMerchViewModel(private val repository: RrqRepository) : ViewModel() {
    private val _stateDetail: MutableStateFlow<StateHolder<MerchOrder>> =
        MutableStateFlow(StateHolder.Loading)
    val stateDetail: StateFlow<StateHolder<MerchOrder>>
        get() = _stateDetail

    fun getMerchById(merchId: Long) {
        viewModelScope.launch {
            _stateDetail.value = StateHolder.Loading
            _stateDetail.value = StateHolder.Success(repository.getMerchOrderById(merchId))
        }
    }

    fun addToCart(merch: Merch, count: Int) {
        viewModelScope.launch {
            repository.updateMerchOrder(merch.id, count)
        }
    }
}