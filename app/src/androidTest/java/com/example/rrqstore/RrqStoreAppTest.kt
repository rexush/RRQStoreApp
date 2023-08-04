package com.example.rrqstore

import androidx.activity.ComponentActivity
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.navigation.compose.ComposeNavigator
import androidx.navigation.testing.TestNavHostController
import com.example.rrqstore.model.MerchData
import com.example.rrqstore.ui.theme.RRQStoreTheme
import com.example.rrqstore.ui.theme.navigation.Screen
import com.example.rrqstore.utils.assertCurrentRouteName
import com.example.rrqstore.utils.onNodeWithStringId
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class RrqStoreAppTest {
    @get:Rule
    val composeTestRule = createAndroidComposeRule<ComponentActivity>()
    private lateinit var navController: TestNavHostController

    @Before
    fun setUp() {
        composeTestRule.setContent {
            RRQStoreTheme {
                navController = TestNavHostController(LocalContext.current)
                navController.navigatorProvider.addNavigator(ComposeNavigator())
                RrqStoreApp(navController = navController)
            }
        }
    }

    @Test
    fun navHost_verifyStartDestination() {
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesToDetailWithData() {
        composeTestRule.onNodeWithTag("MerchList").performScrollToIndex(8)
        composeTestRule.onNodeWithText(MerchData.dummyMerch[8].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailMerch.route)
        composeTestRule.onNodeWithText(MerchData.dummyMerch[8].title).assertIsDisplayed()
    }

    @Test
    fun navHost_bottomNavigation_working() {
        composeTestRule.onNodeWithStringId(R.string.menu_cart).performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_profile).performClick()
        navController.assertCurrentRouteName(Screen.Profile.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_clickItem_navigatesBack() {
        composeTestRule.onNodeWithTag("MerchList").performScrollToIndex(8)
        composeTestRule.onNodeWithText(MerchData.dummyMerch[8].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailMerch.route)
        composeTestRule.onNodeWithContentDescription(composeTestRule.activity.getString(R.string.back)).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }

    @Test
    fun navHost_checkout_rightBackStack() {
        composeTestRule.onNodeWithText(MerchData.dummyMerch[1].title).performClick()
        navController.assertCurrentRouteName(Screen.DetailMerch.route)
        composeTestRule.onNodeWithStringId(R.string.plus).performClick()
        composeTestRule.onNodeWithContentDescription("Order Button").performClick()
        navController.assertCurrentRouteName(Screen.Cart.route)
        composeTestRule.onNodeWithStringId(R.string.menu_home).performClick()
        navController.assertCurrentRouteName(Screen.Home.route)
    }
}