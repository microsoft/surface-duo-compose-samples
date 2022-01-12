package com.microsoft.device.display.samples.listdetail

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import androidx.window.testing.layout.WindowLayoutInfoPublisherRule
import com.microsoft.device.display.samples.listdetail.models.images
import com.microsoft.device.display.samples.listdetail.ui.theme.ListDetailComposeSampleTheme
import com.microsoft.device.display.samples.listdetail.ui.view.ListDetailApp
import com.microsoft.device.dualscreen.testutils.assertScreenshotMatchesReference
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

    @ExperimentalTestApi
    @Test
    fun app_verticalFold_showDetailAfterListClicks() {
        composeTestRule.setContent {
            ListDetailComposeSampleTheme() {
                ListDetailApp(WindowState(hasFold = true, isFoldHorizontal = false))
            }
        }

        // Simulate vertical fold
        publisherRule.simulateVerticalFold(composeTestRule)

        images.forEachIndexed { index, _ ->
            // Click on list item
            composeTestRule.onNodeWithTag(index.toString()
            ).performClick()

            composeTestRule.onNodeWithTag(composeTestRule.getString(R.string.image_tag) + index.toString()
            ).assertExists()
        }
    }
}