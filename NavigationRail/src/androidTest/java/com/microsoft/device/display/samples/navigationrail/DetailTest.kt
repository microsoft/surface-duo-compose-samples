/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail

import androidx.compose.runtime.Composable
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.SemanticsProperties.HorizontalScrollAxisRange
import androidx.compose.ui.semantics.SemanticsProperties.VerticalScrollAxisRange
import androidx.compose.ui.test.SemanticsMatcher
import androidx.compose.ui.test.SemanticsNodeInteraction
import androidx.compose.ui.test.assert
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.hasAnyChild
import androidx.compose.ui.test.hasText
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipe
import androidx.compose.ui.test.swipeDown
import androidx.compose.ui.unit.DpRect
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.rememberNavController
import com.microsoft.device.display.samples.navigationrail.models.DataProvider.plantList
import com.microsoft.device.display.samples.navigationrail.ui.components.DrawerState
import com.microsoft.device.display.samples.navigationrail.ui.components.DrawerStateKey
import com.microsoft.device.display.samples.navigationrail.ui.theme.NavigationRailAppTheme
import com.microsoft.device.display.samples.navigationrail.ui.view.GallerySections
import com.microsoft.device.display.samples.navigationrail.ui.view.ItemDetailView
import com.microsoft.device.display.samples.navigationrail.ui.view.ItemDetailViewWithTopBar
import com.microsoft.device.display.samples.navigationrail.ui.view.NavigationRailApp
import com.microsoft.device.dualscreen.testing.compose.getString
import com.microsoft.device.dualscreen.twopanelayout.twopanelayoutnav.TwoPaneNavScopeTest
import com.microsoft.device.dualscreen.windowstate.WindowState
import org.junit.Rule
import org.junit.Test

class DetailTest {
    @get: Rule
    val composeTestRule = createComposeRule()

    /**
     * Tests that the back button appears in pane 2 in single screen mode
     */
    @Test
    fun pane2_backButtonAppearsInSingleScreenMode() {
        composeTestRule.setContent {
            Pane2Plants(isSinglePane = true, isDualPortrait = false, isDualLandscape = false)
        }

        // Assert that back button is visible
        composeTestRule.onNodeWithContentDescription(getString(R.string.back)).assertIsDisplayed()
    }

    /**
     * Tests that the back button appears in pane 2 in dual landscape mode
     */
    @Test
    fun pane2_backButtonAppearsInDualLandscapeMode() {
        composeTestRule.setContent {
            Pane2Plants(isSinglePane = true, isDualPortrait = false, isDualLandscape = true)
        }

        // Assert that back button is visible
        composeTestRule.onNodeWithContentDescription(getString(R.string.back)).assertIsDisplayed()
    }

    /**
     * Tests that the back button doesn't appear in pane 2 in dual portrait mode
     */
    @Test
    fun pane2_backButtonHiddenInDualPortraitMode() {
        composeTestRule.setContent {
            Pane2Plants(isSinglePane = false, isDualPortrait = true, isDualLandscape = false)
        }

        // Assert that back button does not exist
        composeTestRule.onNodeWithContentDescription(getString(R.string.back)).assertDoesNotExist()
    }

    /**
     * Tests that the back button successfully returns to the gallery view from the item detail view when
     * in single screen mode
     */
    @Test
    fun app_backButtonChangesPanesInSingleScreenMode() {
        composeTestRule.setContent {
            NavigationRailAppTheme {
                NavigationRailApp(WindowState())
            }
        }

        // Assert "plants" title is visible
        composeTestRule.onNodeWithText(getString(R.string.plants).lowercase()).assertIsDisplayed()

        // Click on first plant item
        val firstEntryDescription =
            getString(R.string.image_description, plantList[0].name, plantList[0].id)
        composeTestRule.onNodeWithContentDescription(firstEntryDescription).performClick()

        // Assert that back button is visible
        composeTestRule.onNodeWithContentDescription(getString(R.string.back)).assertIsDisplayed()

        // Assert that "plants" title is no longer visible
        composeTestRule.onNodeWithText(getString(R.string.plants).lowercase()).assertDoesNotExist()

        // Click back button
        composeTestRule.onNodeWithContentDescription(getString(R.string.back)).performClick()

        // Assert that "plants" title is visible again
        composeTestRule.onNodeWithText(getString(R.string.plants).lowercase()).assertIsDisplayed()
    }

    /**
     * Tests that the content drawer can be swiped between the expanded and collapsed states
     */
    @Test
    fun detailView_contentDrawerSwipes() {
        composeTestRule.setContent {
            DetailViewForFirstPlant()
        }

        // Assert drawer is collapsed on start
        composeTestRule.onNodeWithTag(getString(R.string.content_drawer))
            .assertDrawerStateEquals(DrawerState.Collapsed)

        // Swipe content drawer up
        composeTestRule.onNodeWithTag(getString(R.string.content_drawer)).performTouchInput {
            val start = this.topCenter
            val end = Offset(start.x, start.y - 200)
            swipe(start, end, 200)
        }

        // Assert drawer is now expanded
        composeTestRule.onNodeWithTag(getString(R.string.content_drawer))
            .assertDrawerStateEquals(DrawerState.Expanded)

        // Swipe content drawer back down
        composeTestRule.onNodeWithTag(getString(R.string.content_drawer))
            .performTouchInput { swipeDown() }

        // Assert drawer is collapsed again
        composeTestRule.onNodeWithTag(getString(R.string.content_drawer))
            .assertDrawerStateEquals(DrawerState.Collapsed)
    }

    /**
     * Tests that the info box fact contents are horizontally scrollable
     */
    @Test
    fun detailView_factsAreHorizontallyScrollable() {
        composeTestRule.setContent {
            DetailViewForFirstPlant()
        }

        // Assert that facts are horizontally scrollable
        composeTestRule.onNode(
            hasAnyChild(hasText(plantList[0].fact1))
                and SemanticsMatcher.keyIsDefined(HorizontalScrollAxisRange)
        ).assertExists()
    }

    /**
     * Tests that the long details in the content drawer are vertically scrollable
     */
    @Test
    fun detailView_detailsAreVerticallyScrollable() {
        composeTestRule.setContent {
            DetailViewForFirstPlant()
        }

        // Assert that details are vertically scrollable
        composeTestRule.onNode(
            hasText(plantList[0].details)
                and SemanticsMatcher.keyIsDefined(VerticalScrollAxisRange)
        ).assertExists()
    }

    /**
     * Composable for testing that shows the first plant image in pane 2
     */
    @Composable
    private fun Pane2Plants(isSinglePane: Boolean, isDualPortrait: Boolean, isDualLandscape: Boolean) {
        NavigationRailAppTheme {
            TwoPaneNavScopeTest(isSinglePane = isSinglePane).ItemDetailViewWithTopBar(
                isDualPortrait = isDualPortrait,
                isDualLandscape = isDualLandscape,
                foldIsOccluding = false,
                foldBoundsDp = DpRect(0.dp, 0.dp, 0.dp, 0.dp),
                windowHeight = LocalConfiguration.current.screenHeightDp.dp,
                imageId = 1,
                updateImageId = {},
                navController = rememberNavController()
            )
        }
    }

    /**
     * Composable for testing that shows the first plant image in the item detail view
     */
    @Composable
    private fun DetailViewForFirstPlant() {
        NavigationRailAppTheme {
            ItemDetailView(
                isDualPortrait = false,
                isDualLandscape = false,
                foldIsOccluding = false,
                foldBoundsDp = DpRect(0.dp, 0.dp, 0.dp, 0.dp),
                windowHeight = LocalConfiguration.current.screenHeightDp.dp,
                selectedImage = plantList[0],
                gallerySection = GallerySections.PLANTS
            )
        }
    }

    /**
     * Asserts that the drawer state of the node matches the given drawer state
     */
    private fun SemanticsNodeInteraction.assertDrawerStateEquals(drawerState: DrawerState) =
        assert(SemanticsMatcher.expectValue(DrawerStateKey, drawerState))
}
