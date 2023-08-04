package com.example.rrqstore.ui.theme.screen.cart

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.rrqstore.R
import com.example.rrqstore.ViewModelFactory
import com.example.rrqstore.ui.theme.common.StateHolder
import com.example.rrqstore.ui.theme.components.CartItem
import com.example.rrqstore.ui.theme.components.OrderButton
import com.example.rrqstore.utils.Injection

@Composable
fun CartScreen(
    viewModel: CartViewModel = viewModel(
        factory = ViewModelFactory(
            Injection.provideRepository()
        )
    ),
    onOrderButtonClicked: (String) -> Unit,
) {
    viewModel.stateCart.collectAsState(initial = StateHolder.Loading).value.let { stateHolder ->
        when (stateHolder) {
            is StateHolder.Loading -> {
                viewModel.getAddedOrderMerch()
            }
            is StateHolder.Success -> {
                if (stateHolder.data.MerchOrder.isEmpty()) {
                    EmptyCart()
                } else {
                    CartContent(
                        stateHolder.data,
                        onMerchCountChanged = { merchId, count ->
                            viewModel.updateOrderMerch(merchId, count)
                        },
                        onOrderButtonClicked = onOrderButtonClicked
                    )
                }
            }
            is StateHolder.Error -> {}
        }
    }
}

@Composable
fun EmptyCart() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(
            text = stringResource(R.string.cart_empty),
            fontSize = 18.sp,
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun CartContent(
    state: CartState,
    onMerchCountChanged: (id: Long, count: Int) -> Unit,
    onOrderButtonClicked: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    val shareMessage = stringResource(
        R.string.share_message,
        state.MerchOrder.count(),
        state.totalRequiredMoney
    )
    Column(
        modifier = modifier.fillMaxSize()
    ) {
        TopAppBar(backgroundColor = MaterialTheme.colors.surface) {
            Text(
                text = stringResource(R.string.menu_cart),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center
            )
        }
        OrderButton(
            text = stringResource(R.string.total_order, state.totalRequiredMoney),
            enabled = state.MerchOrder.isNotEmpty(),
            onClick = {
                onOrderButtonClicked(shareMessage)
            },
            modifier = Modifier.padding(16.dp)
        )
        LazyColumn(
            contentPadding = PaddingValues(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            items(state.MerchOrder, key = { it.merch.id }) { item ->
                CartItem(
                    merchId = item.merch.id,
                    image = item.merch.image,
                    title = item.merch.title,
                    totalPoint = item.merch.requiredMoney * item.count,
                    count = item.count,
                    onMerchCountChanged = onMerchCountChanged,
                )
                Divider()
            }
        }
    }
}