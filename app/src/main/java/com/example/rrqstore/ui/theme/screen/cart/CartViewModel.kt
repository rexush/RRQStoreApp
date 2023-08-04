package com.example.rrqstore.ui.theme.screen.cart

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.rrqstore.data.RrqRepository
import com.example.rrqstore.ui.theme.common.StateHolder
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CartViewModel(private val repository: RrqRepository) : ViewModel() {
    private val _stateCart: MutableStateFlow<StateHolder<CartState>> =
        MutableStateFlow(StateHolder.Loading)
    val stateCart: StateFlow<StateHolder<CartState>>
        get() = _stateCart

    fun getAddedOrderMerch() {
        viewModelScope.launch {
            _stateCart.value = StateHolder.Loading
            repository.getAddedMerchOrder()
                .collect { orderMerch ->
                    val totalRequiredPoint =
                        orderMerch.sumOf { it.merch.requiredMoney * it.count }
                    _stateCart.value =
                        StateHolder.Success(CartState(orderMerch, totalRequiredPoint))
                }
        }
    }

    fun updateOrderMerch(merchId: Long, count: Int) {
        viewModelScope.launch {
            repository.updateMerchOrder(merchId, count)
                .collect { isUpdated ->
                    if (isUpdated) {
                        getAddedOrderMerch()
                    }
                }
        }
    }
}