/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.example.navigationrail.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.ContentAlpha
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.LocalContentColor
import androidx.compose.material.MaterialTheme
import androidx.compose.material.NavigationRail
import androidx.compose.material.NavigationRailItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigationrail.R
import com.example.navigationrail.models.Image

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ShowWithNavigation(isDualScreen: Boolean, imageLiveData: MutableLiveData<Image>) {
    // Set up navigation destinations
    val galleries = stringArrayResource(id = R.array.galleries)
    // REVISIT: add this and any other hardcoded text to string xml
    if (galleries.isEmpty()) throw Error("gallery string array is empty")
    val icons = listOf(
        // REVISIT: placeholder icons
        Icons.Filled.Favorite,
        Icons.Filled.AccountCircle,
        Icons.Filled.Face,
        Icons.Filled.AddCircle,
        Icons.Filled.ArrowDropDown
    )

    val navController = rememberNavController()
    val navHost: @Composable (Modifier) -> Unit = { modifier ->
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = galleries[0],
        ) {
            galleries.forEachIndexed { index, item ->
                composable(galleries[index]) { Gallery(item, imageLiveData, isDualScreen) }
            }
        }
    }

    // Use navigation rail when dual screen (more space), otherwise use bottom navigation
    if (isDualScreen) {
        ShowWithTopBar(
            title = stringResource(R.string.app_name)
        ) { modifier ->
            ShowWithNavigationRail(
                modifier = modifier,
                names = galleries,
                icons = icons,
                navController = navController,
                imageLiveData = imageLiveData,
            ) {
                navHost(Modifier)
            }
        }
    } else {
        ShowWithTopBar(
            title = stringResource(R.string.app_name),
            bottomBar = {
                ShowBottomNavigation(
                    names = galleries,
                    icons = icons,
                    navController = navController,
                    imageLiveData = imageLiveData,
                )
            },
        ) { modifier ->
            navHost(modifier)
        }
    }
}

@Composable
fun ShowWithTopBar(
    title: String? = null,
    titleColor: Color = MaterialTheme.colors.onPrimary,
    color: Color = MaterialTheme.colors.primary,
    bottomBar: @Composable () -> Unit = {},
    actions: @Composable RowScope.() -> Unit = {},
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = title ?: "", color = titleColor) },
                backgroundColor = color,
                actions = { actions() }
            )
        },
        bottomBar = bottomBar,
    ) { innerPadding ->
        content(Modifier.padding(innerPadding))
    }
}

@ExperimentalMaterialApi
@Composable
fun ShowWithNavigationRail(
    modifier: Modifier,
    names: Array<String>,
    icons: List<ImageVector>,
    navController: NavController,
    imageLiveData: MutableLiveData<Image>,
    content: @Composable () -> Unit,
) {
    Row(modifier = modifier) {
        NavigationRail(modifier = modifier) {
            val currentDestination = navController.currentBackStackEntryAsState().value?.destination
            names.forEachIndexed { index, item ->
                NavigationRailItem(
                    icon = { NavItemIcon(icons = icons, index = index, description = item) },
                    label = { NavItemLabel(item) },
                    selected = isNavItemSelected(currentDestination, item),
                    onClick = { navItemOnClick(navController, item, imageLiveData) }
                )
            }
        }
        content()
    }
}

@Composable
// Reference: https://developer.android.com/jetpack/compose/navigation#bottom-nav
fun ShowBottomNavigation(
    names: Array<String>,
    icons: List<ImageVector>,
    navController: NavController,
    imageLiveData: MutableLiveData<Image>,
) {
    BottomNavigation {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        names.forEachIndexed { index, item ->
            BottomNavigationItem(
                icon = { NavItemIcon(icons = icons, index = index, description = item) },
                label = { NavItemLabel(item) },
                selected = isNavItemSelected(currentDestination, item),
                onClick = { navItemOnClick(navController, item, imageLiveData) },
                selectedContentColor = MaterialTheme.colors.primary,
                unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.medium)
            )
        }
    }
}

@Composable
private fun NavItemIcon(icons: List<ImageVector>, index: Int, description: String) {
    Icon(icons[index], description)
}

@Composable
private fun NavItemLabel(navItem: String) {
    Text(navItem)
}

// Reference: https://developer.android.com/jetpack/compose/navigation#bottom-nav
private fun isNavItemSelected(currentDestination: NavDestination?, navItem: String): Boolean {
    return currentDestination?.hierarchy?.any { it.route == navItem } == true
}

// Reference: https://developer.android.com/jetpack/compose/navigation#bottom-nav
private fun navItemOnClick(
    navController: NavController,
    navItem: String,
    imageLiveData: MutableLiveData<Image>
) {
    // Navigate to new destination
    navController.navigate(navItem) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    // Reset selected image when switching gallery
    imageLiveData.value = null
}
