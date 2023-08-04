package com.example.rrqstore.ui.theme.screen.profile

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.rrqstore.ui.theme.RRQStoreTheme
import com.example.rrqstore.R

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
) {
    val backgroundImage = painterResource(R.drawable.ikan)
    Box(
        modifier = modifier
            .fillMaxSize()
    ) {
        Image(
            painter = backgroundImage,
            contentDescription = stringResource(R.string.profile_picture),
            contentScale = ContentScale.FillBounds,
            modifier = Modifier
                .fillMaxSize()
        )
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(8.dp),
            modifier = modifier
                .padding(85.dp)
                .fillMaxSize()
        ) {
            Image(
                painter = painterResource(R.drawable.profile),
                contentDescription = stringResource(R.string.profile_picture),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .size(200.dp)
                    .clip(CircleShape)
            )
            Text(
                text = stringResource(R.string.name),
                style = MaterialTheme.typography.body1.copy(
                    fontWeight = FontWeight.ExtraBold
                ),
                color = Color.White,
                fontSize = 25.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
            Text(
                text = stringResource(R.string.email),
                color = Color.White,
                style = MaterialTheme.typography.subtitle1,
                fontWeight = FontWeight.SemiBold,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 16.dp)
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProfileScreenPreview() {
    RRQStoreTheme {
        ProfileScreen()
    }
}