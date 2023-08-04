package com.example.rrqstore.ui.theme.screen.cart

import com.example.rrqstore.model.MerchOrder

data class CartState(
    val MerchOrder: List<MerchOrder>,
    val totalRequiredMoney: Int
)