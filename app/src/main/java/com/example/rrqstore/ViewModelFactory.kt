package com.example.rrqstore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rrqstore.data.RrqRepository
import com.example.rrqstore.ui.theme.screen.cart.CartViewModel
import com.example.rrqstore.ui.theme.screen.detail.DetailMerchViewModel
import com.example.rrqstore.ui.theme.screen.home.HomeViewModel

class ViewModelFactory(private val repository: RrqRepository) :
    ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewModel::class.java)) {
            return HomeViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(DetailMerchViewModel::class.java)) {
            return DetailMerchViewModel(repository) as T
        } else if (modelClass.isAssignableFrom(CartViewModel::class.java)) {
            return CartViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}