package com.example.rrqstore.ui.theme.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.rrqstore.ui.theme.RRQStoreTheme
import com.example.rrqstore.ui.theme.Shapes
import com.example.rrqstore.R

@Composable
fun CartItem(
    merchId: Long,
    image: Int,
    title: String,
    totalPoint: Int,
    count: Int,
    onMerchCountChanged: (id: Long, count: Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(image),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp)
                .clip(Shapes.small)
        )
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(8.dp)
                .weight(1.0f)
        ) {
            Text(
                text = title,
                maxLines = 3,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.subtitle1.copy(
                    fontWeight = FontWeight.ExtraBold
                )
            )
            Text(
                text = stringResource(
                    R.string.required_money,
                    totalPoint
                ),
                color = MaterialTheme.colors.secondary,
                style = MaterialTheme.typography.subtitle2,
            )
        }
        MerchCount(
            orderId = merchId,
            orderCount = count,
            onMerchIncreased = { onMerchCountChanged(merchId, count + 1) },
            onMerchDecreased = { onMerchCountChanged(merchId, count - 1) },
            modifier = Modifier.padding(8.dp)
        )
    }
}

@Composable
@Preview(showBackground = true)
fun CartItemPreview() {
    RRQStoreTheme {
        CartItem(
            1, R.drawable.jersey, "Jersey RRQ Novus Pro 2023", 499000, 0,
            onMerchCountChanged = { _, _ -> },
        )
    }
}