/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Row
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
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.AddCircle
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.filled.Face
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
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
import com.microsoft.device.display.samples.navigationrail.models.DataProvider
import com.microsoft.device.display.samples.navigationrail.models.Gallery
import com.microsoft.device.display.samples.navigationrail.models.Image
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane1

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ShowWithNavigation(isDualScreen: Boolean, imageLiveData: MutableLiveData<Image>, isDualPortrait: Boolean) {
    // Set up navigation components
    val galleries = listOf(
        Gallery(stringResource(R.string.plants), DataProvider.plantList, Icons.Filled.Favorite),
        Gallery(stringResource(R.string.birds), DataProvider.birdList, Icons.Filled.AccountCircle),
        Gallery(stringResource(R.string.animals), DataProvider.animalList, Icons.Filled.Face),
        Gallery(stringResource(R.string.rocks), DataProvider.rockList, Icons.Filled.AddCircle),
        Gallery(stringResource(R.string.lakes), DataProvider.lakeList, Icons.Filled.ArrowDropDown)
    )

    val navController = rememberNavController()
    val navHost: @Composable (Modifier) -> Unit = { modifier ->
        NavHost(
            modifier = modifier,
            navController = navController,
            startDestination = galleries[0].name,
        ) {
            galleries.forEachIndexed { index, item ->
                composable(galleries[index].name) {
                    GalleryOrItemView(item.name, imageLiveData, isDualPortrait)
                }
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
                galleries = galleries,
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
                    galleries = galleries,
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
    navIcon: (@Composable () -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    navIcon?.let { navigationIcon ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title ?: "", color = titleColor) },
                    backgroundColor = color,
                    navigationIcon = { navigationIcon() }
                )
            },
            bottomBar = bottomBar,
        ) { innerPadding ->
            content(Modifier.padding(innerPadding))
        }
    } ?: run {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = title ?: "", color = titleColor) },
                    backgroundColor = color,
                )
            },
            bottomBar = bottomBar,
        ) { innerPadding ->
            content(Modifier.padding(innerPadding))
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun ShowWithNavigationRail(
    modifier: Modifier,
    galleries: List<Gallery>,
    navController: NavController,
    imageLiveData: MutableLiveData<Image>,
    content: @Composable () -> Unit,
) {
    Row(modifier = modifier) {
        NavigationRail {
            val currentDestination = navController.currentBackStackEntryAsState().value?.destination
            galleries.forEach { item ->
                NavigationRailItem(
                    icon = { NavItemIcon(icon = item.icon, description = item.name) },
                    label = { NavItemLabel(item.name) },
                    selected = isNavItemSelected(currentDestination, item.name),
                    onClick = { navItemOnClick(navController, item.name, imageLiveData) }
                )
            }
        }
        content()
    }
}

@Composable
// Reference: https://developer.android.com/jetpack/compose/navigation#bottom-nav
fun ShowBottomNavigation(
    galleries: List<Gallery>,
    navController: NavController,
    imageLiveData: MutableLiveData<Image>,
) {
    BottomNavigation {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        galleries.forEach { item ->
            BottomNavigationItem(
                icon = { NavItemIcon(icon = item.icon, description = item.name) },
                label = { NavItemLabel(item.name) },
                selected = isNavItemSelected(currentDestination, item.name),
                onClick = { navItemOnClick(navController, item.name, imageLiveData) },
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

@Composable
fun TopBarNavIcon(imageLiveData: MutableLiveData<Image>) {
    IconButton(
        onClick = {
            navigateToPane1()
            imageLiveData.value = null
        }
    ) {
        Icon(
            imageVector = Icons.Filled.ArrowBack,
            contentDescription = stringResource(R.string.back),
        )
    }
}
