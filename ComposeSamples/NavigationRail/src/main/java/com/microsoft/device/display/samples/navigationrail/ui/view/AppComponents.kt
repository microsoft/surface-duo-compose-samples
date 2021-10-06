/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
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

private val NavItemShape = RoundedCornerShape(percent = 15)

/**
 * Show a NavigationRail with gallery destinations
 */
@ExperimentalMaterialApi
@Composable
fun NavRail(
    galleries: Array<GallerySections>,
    navController: NavHostController,
    updateImageId: (Int?) -> Unit,
    updateRoute: (String) -> Unit,
) {
    NavigationRail(
        backgroundColor = MaterialTheme.colors.primary,
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        galleries.forEach { item ->
            NavRailItemWithSelector(isNavItemSelected(currentDestination, item.route)) {
                NavigationRailItem(
                    icon = {
                        NavItemIcon(
                            icon = item.icon,
                            description = stringResource(item.title)
                        )
                    },
                    label = { NavItemLabel(stringResource(item.title)) },
                    selected = isNavItemSelected(currentDestination, item.route),
                    onClick = {
                        navItemOnClick(
                            navController,
                            item.route,
                            updateImageId,
                            updateRoute
                        )
                    },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

/**
 * Show a BottomNavigation component with gallery destinations
 */
@Composable
fun BottomNav(
    galleries: Array<GallerySections>,
    navController: NavHostController,
    updateImageId: (Int?) -> Unit,
    updateRoute: (String) -> Unit,
) {
    BottomNavigation(
        backgroundColor = MaterialTheme.colors.primary,
        modifier = Modifier.height(60.dp)
    ) {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        galleries.forEach { item ->
            BottomNavItemWithSelector(isNavItemSelected(currentDestination, item.route)) {
                BottomNavigationItem(
                    icon = {
                        NavItemIcon(
                            icon = item.icon,
                            description = stringResource(item.title)
                        )
                    },
                    label = { NavItemLabel(stringResource(item.title)) },
                    selected = isNavItemSelected(currentDestination, item.route),
                    onClick = {
                        navItemOnClick(
                            navController,
                            item.route,
                            updateImageId,
                            updateRoute
                        )
                    },
                    selectedContentColor = MaterialTheme.colors.primary,
                    unselectedContentColor = MaterialTheme.colors.onPrimary
                )
            }
        }
    }
}

@Composable
private fun RowScope.BottomNavItemWithSelector(
    showSelected: Boolean,
    navItem: @Composable () -> Unit
) {
    Box(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
        if (showSelected) {
            Selector()
        }
        navItem()
    }
}

@Composable
private fun ColumnScope.NavRailItemWithSelector(
    showSelected: Boolean,
    navItem: @Composable () -> Unit
) {
    Box(modifier = Modifier.weight(1f, fill = false), contentAlignment = Alignment.Center) {
        if (showSelected) {
            Selector()
        }
        navItem()
    }
}

/**
 * Reference:https://github.com/android/compose-samples/blob/main/Jetsnack/app/src/main/java/com/example/jetsnack/ui/home/Home.kt
 */
@Composable
private fun Selector() {
    Spacer(
        modifier = Modifier
            .size(50.dp) // REVISIT: is it bad to hardcode this?
            .background(MaterialTheme.colors.secondary, NavItemShape)
    )
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

@ExperimentalMaterialApi
@Preview
@Composable
private fun NavRailPreview() {
    ComposeSamplesTheme {
        var imageId: Int? by remember { mutableStateOf(null) }
        var route by remember { mutableStateOf(navDestinations[0].route) }
        NavRail(
            galleries = navDestinations,
            navController = rememberNavController(),
            updateImageId = { id -> imageId = id },
            updateRoute = { new -> route = new },
        )
    }
}

@Preview
@Composable
private fun BottomNavPreview() {
    ComposeSamplesTheme {
        var imageId: Int? by remember { mutableStateOf(null) }
        var route by remember { mutableStateOf(navDestinations[0].route) }
        BottomNav(
            galleries = navDestinations,
            navController = rememberNavController(),
            updateImageId = { id -> imageId = id },
            updateRoute = { new -> route = new },
        )
    }
}
