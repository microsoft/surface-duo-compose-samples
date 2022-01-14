package com.microsoft.device.display.samples.listdetail

import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.performClick
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.listdetail.models.images
import com.microsoft.device.display.samples.listdetail.ui.theme.ListDetailComposeSampleTheme
import com.microsoft.device.display.samples.listdetail.ui.view.ListDetailApp
import com.microsoft.device.dualscreen.testutils.getString
import com.microsoft.device.dualscreen.testutils.simulateHorizontalFold
import com.microsoft.device.dualscreen.testutils.simulateVerticalFold
import com.microsoft.device.dualscreen.windowstate.WindowState
import org.junit.Rule
import org.junit.Test
import org.junit.rules.RuleChain
import org.junit.rules.TestRule

class ListDetailTest {
    private val composeTestRule = createAndroidComposeRule<MainActivity>()
    private val publisherRule = WindowLayoutInfoPublisherRule()

    @get: Rule
    val testRule: TestRule

    init {
        testRule = RuleChain.outerRule(publisherRule).around(composeTestRule)
        RuleChain.outerRule(composeTestRule)
    }

    /**
     * Tests that the detail image will be shown accordingly after the image
     * item is selected from the list on the vertical fold(dual-portrait) mode
     */
    @Test
    fun app_verticalFold_showDetailAfterListClicks() {
        composeTestRule.setContent {
            ListDetailComposeSampleTheme {
                ListDetailApp(WindowState(hasFold = true, isFoldHorizontal = false))
            }
        }

        // Simulate vertical fold
        publisherRule.simulateVerticalFold(composeTestRule)

        images.forEachIndexed { index, _ ->
            // Click on list item
            composeTestRule.onNodeWithContentDescription(
                index.toString()
            ).performClick()

            // Assert the shown detail image matches the item clicked from the list
            composeTestRule.onNodeWithContentDescription(
                composeTestRule.getString(R.string.image_tag) + index.toString()
            ).assertExists()
        }
    }

    /**
     * Tests that only the list view will be shown without the detail view
     * on the horizontal fold(dual-landscape) mode
     */
    @Test
    fun app_horizontalFold_showsList() {
        composeTestRule.setContent {
            ListDetailComposeSampleTheme {
                ListDetailApp(WindowState(hasFold = true, isFoldHorizontal = true))
            }
        }

        // Simulate horizontal fold
        publisherRule.simulateHorizontalFold(composeTestRule)

        // Assert the list view is now shown
        composeTestRule.onNodeWithTag(
            composeTestRule.getString(R.string.list_view)
        ).assertExists()

        // Assert the detail view is not shown
        composeTestRule.onNodeWithTag(
            composeTestRule.getString(R.string.detail_view)
        ).assertDoesNotExist()
    }
}
