/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.navigationrail.R
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane1

/**
 * Show the given content with a TopAppBar (customizable title/color/navigation icons) and a
 * bottom bar if desired
 */
@Composable
fun ShowWithTopBar(
    title: String? = null,
    titleColor: Color = MaterialTheme.colors.onPrimary,
    color: Color = MaterialTheme.colors.primary,
    bottomBar: @Composable () -> Unit = {},
    navIcon: (@Composable () -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title ?: "", color = titleColor) },
                backgroundColor = color,
                navigationIcon = if (navIcon == null) null else { { navIcon() } }
            )
        },
        bottomBar = bottomBar,
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}

/**
 * Show a "back" navigation icon that returns from ItemDetailView to GalleryView
 */
@Composable
fun BackNavIcon(updateImageId: (Int?) -> Unit) {
    IconButton(onClick = { goBack(updateImageId) }) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back),
        )
    }
}

/**
 * Return to gallery view (pane 1) and clear image selection
 */
private fun goBack(updateImageId: (Int?) -> Unit) {
    navigateToPane1()
    updateImageId(null)
}

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
    NavigationRail {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        galleries.forEach { item ->
            NavigationRailItem(
                icon = { NavItemIcon(icon = item.icon, description = stringResource(item.title)) },
                label = { NavItemLabel(stringResource(item.title)) },
                selected = isNavItemSelected(currentDestination, item.route),
                onClick = { navItemOnClick(navController, item.route, updateImageId, updateRoute) }
            )
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
    BottomNavigation {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        galleries.forEach { item ->
            BottomNavigationItem(
                icon = { NavItemIcon(icon = item.icon, description = stringResource(item.title)) },
                label = { NavItemLabel(stringResource(item.title)) },
                selected = isNavItemSelected(currentDestination, item.route),
                onClick = { navItemOnClick(navController, item.route, updateImageId, updateRoute) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
            )
        }
    }
}

@Composable
private fun NavItemIcon(icon: ImageVector, description: String) {
    Icon(icon, description)
}

@Composable
private fun NavItemLabel(navItem: String) {
    Text(navItem)
}

/**
 * Reference: https://developer.android.com/jetpack/compose/navigation#bottom-nav
 */
@Composable
private fun isNavItemSelected(currentDestination: NavDestination?, navItemRoute: String,): Boolean {
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
