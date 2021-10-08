/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.components

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material.BottomNavigation
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.microsoft.device.display.samples.navigationrail.ui.view.GallerySections

private val NAV_RAIL_TOP_SPACING = 32.dp

@ExperimentalMaterialApi
@Composable
fun GalleryNavRail(
    navController: NavHostController,
    galleries: Array<GallerySections>,
    updateImageId: (Int?) -> Unit,
    updateRoute: (String) -> Unit,
) {
    NavigationRail(
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        Spacer(Modifier.height(NAV_RAIL_TOP_SPACING))
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        galleries.forEach { gallery ->
            NavRailItemWithSelector(
                icon = {
                    NavItemIcon(icon = gallery.icon, description = stringResource(gallery.title))
                },
                label = { NavItemLabel(stringResource(gallery.title)) },
                selected = isNavItemSelected(currentDestination, gallery.route),
                onClick = {
                    navItemOnClick(navController, gallery.route, updateImageId, updateRoute)
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun GalleryBottomNav(
    navController: NavHostController,
    galleries: Array<GallerySections>,
    updateImageId: (Int?) -> Unit,
    updateRoute: (String) -> Unit,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        galleries.forEach { gallery ->
            BottomNavItemWithSelector(
                icon = {
                    NavItemIcon(icon = gallery.icon, description = stringResource(gallery.title))
                },
                label = { NavItemLabel(stringResource(gallery.title)) },
                selected = isNavItemSelected(currentDestination, gallery.route),
                onClick = {
                    navItemOnClick(navController, gallery.route, updateImageId, updateRoute)
                },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = MaterialTheme.colors.onPrimary
            )
        }
    }
}

@Composable
private fun NavItemIcon(icon: Int, description: String) {
    Icon(painterResource(icon), description)
}

@Composable
private fun NavItemLabel(navItem: String) {
    Text(navItem)
}

/**
 * Reference: https://developer.android.com/jetpack/compose/navigation#bottom-nav
 */
@Composable
private fun isNavItemSelected(
    currentDestination: NavDestination?,
    navItemRoute: String,
): Boolean {
    return currentDestination?.hierarchy?.any { it.route == navItemRoute } == true
}

/**
 * Reference: https://developer.android.com/jetpack/compose/navigation#bottom-nav
 */
private fun navItemOnClick(
    navController: NavController,
    navItem: String,
    updateImageId: (Int?) -> Unit,
    updateRoute: (String) -> Unit,
) {
    // Navigate to new destination
    navController.navigate(navItem) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    // Update current route to new destination
    updateRoute(navItem)

    // Reset selected image when switching gallery
    updateImageId(null)
}
