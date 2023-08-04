package com.example.rrqstore.ui.theme.screen.detail

import androidx.activity.ComponentActivity
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.example.rrqstore.model.Merch
import com.example.rrqstore.model.MerchOrder
import com.example.rrqstore.ui.theme.RRQStoreTheme
import com.example.rrqstore.utils.onNodeWithStringId
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import com.example.rrqstore.R

class DetailScreenTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()

    private val merchOrder = MerchOrder(
        merch =  Merch(
            4,
            R.drawable.jersey,
            "Jersey RRQ Novus Pro 2023",
            "Jersey RRQ Novus Pro 2023 is made of polyester material, which makes it very comfortable to wear. It features the RRQ logo on the front and back.",
            499000
        ),
        count = 0
    )

    @Before
    fun setUp() {
        composeTestRule.setContent {
            RRQStoreTheme {
                DetailContent(
                    merchOrder.merch.image,
                    merchOrder.merch.title,
                    merchOrder.merch.desc,
                    merchOrder.merch.requiredMoney,
                    merchOrder.count,
                    onBackClick = {},
                    onAddToCart = {}
                )
            }
        }
        composeTestRule.onRoot().printToLog("currentLabelExists")
    }

    @Test
    fun detailContent_isDisplayed() {
        composeTestRule.onNodeWithText(merchOrder.merch.title).assertIsDisplayed()
        composeTestRule.onNodeWithText(
            composeTestRule.activity.getString(
                R.string.required_money,
                merchOrder.merch.requiredMoney
            )
        ).assertIsDisplayed()
    }

    @Test
    fun increaseMerch_buttonEnabled() {
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsNotEnabled()
        composeTestRule.onNodeWithStringId(R.string.plus).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").assertIsEnabled()
    }

    @Test
    fun increaseMerch_correctCounter() {
        composeTestRule.onNodeWithStringId(R.string.plus).performClick().performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("2"))
    }
    
    @Test
    fun decreaseMerch_stillZero() {
        composeTestRule.onNodeWithStringId(R.string.minus).performClick()
        composeTestRule.onNodeWithTag("count").assert(hasText("0"))
    }
}