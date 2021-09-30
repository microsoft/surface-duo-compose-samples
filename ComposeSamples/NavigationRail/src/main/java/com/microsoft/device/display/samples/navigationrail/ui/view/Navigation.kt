/*
 * Copyright (c) Microsoft Corporation. All rights reserved.
 * Licensed under the MIT License.
 */

package com.microsoft.device.display.samples.navigationrail.ui.view

import android.util.Log
import androidx.annotation.StringRes
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
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.MutableLiveData
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.navigationrail.R
import com.microsoft.device.display.samples.navigationrail.models.DataProvider
import com.microsoft.device.display.samples.navigationrail.models.Image
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane1
import com.microsoft.device.dualscreen.twopanelayout.navigateToPane2

private lateinit var currentRoute: String

object NavDestinations {
    const val GALLERY_ROUTE = "gallery"
    val gallerySections = GallerySections.values()
}

enum class GallerySections(
    @StringRes val title: Int,
    val icon: ImageVector,
    val route: String,
    val list: List<Image>
) {
    PLANTS(R.string.plants, Icons.Filled.Favorite, "${NavDestinations.GALLERY_ROUTE}/plants", DataProvider.plantList),
    BIRDS(R.string.birds, Icons.Filled.AccountCircle, "${NavDestinations.GALLERY_ROUTE}/birds", DataProvider.birdList),
    ANIMALS(R.string.animals, Icons.Filled.Face, "${NavDestinations.GALLERY_ROUTE}/animals", DataProvider.animalList),
    ROCKS(R.string.rocks, Icons.Filled.AddCircle, "${NavDestinations.GALLERY_ROUTE}/rocks", DataProvider.rockList),
    LAKES(R.string.lakes, Icons.Filled.ArrowDropDown, "${NavDestinations.GALLERY_ROUTE}/lakes", DataProvider.lakeList)
}

@ExperimentalFoundationApi
fun NavGraphBuilder.addGalleryGraph(
    onImageSelected: (Int, NavBackStackEntry) -> Unit,
    showItemView: Boolean,
) {
    NavDestinations.gallerySections.forEach { section ->
        composable(section.route) { from ->
            GalleryOrItemView(
                galleryList = section.list,
                onImageClick = { id -> onImageSelected(id, from) },
                showItemView = showItemView,
            )
        }
    }
}

private fun NavBackStackEntry.lifecycleIsResumed() =
    this.lifecycle.currentState == Lifecycle.State.RESUMED

@ExperimentalMaterialApi
@ExperimentalFoundationApi
@Composable
fun ShowWithNavigation(isDualScreen: Boolean, imageLiveData: MutableLiveData<Image>, isDualPortrait: Boolean, route: String, updateRoute: (String) -> Unit) {
    // store current navigation route to use as starting destination for nav graph
    currentRoute = route

    // set up nav controller and nav graph
    val navController = rememberNavController()
    val navHost: @Composable () -> Unit = {
        NavHost(
            navController = navController,
            startDestination = currentRoute,
        ) {
            addGalleryGraph(
                onImageSelected = { id, from ->
                    imageLiveData.value = DataProvider.getImage(id)

                    if (from.lifecycleIsResumed() && !isDualPortrait) {
                        navigateToPane2()
                    }
                },
                showItemView = !isDualPortrait && imageLiveData.value != null,
            )
        }
    }

    // Use navigation rail when dual screen (more space), otherwise use bottom navigation
    ShowWithTopBar(
        title = stringResource(R.string.app_name),
        bottomBar = {
            if (!isDualScreen)
                ShowBottomNavigation(
                    galleries = NavDestinations.gallerySections,
                    navController = navController,
                    imageLiveData = imageLiveData,
                    updateRoute = updateRoute,
                )
        },
    ) { mod ->
        Row(mod) {
            if (isDualScreen) {
                ShowNavigationRail(
                    galleries = NavDestinations.gallerySections,
                    navController = navController,
                    imageLiveData = imageLiveData,
                    updateRoute = updateRoute,
                )
            }
            navHost()
        }
    }

    // check that nav controller is at correct current route
    // if not, try to navigate to the current route (unless nav graph hasn't been created yet)
    if (navController.currentDestination?.route != currentRoute) {
        try {
            navController.graph
            navController.navigate(currentRoute)
        } catch (e: IllegalStateException) {
            // graph may be null if this is the first run through
            Log.i("Navigation Rail Sample", "Caught the following exception: ${e.message}")
        }
    }
}

@Composable
fun ShowWithTopBar(
    modifier: Modifier = Modifier,
    title: String? = null,
    titleColor: Color = MaterialTheme.colors.onPrimary,
    color: Color = MaterialTheme.colors.primary,
    bottomBar: @Composable () -> Unit = {},
    navIcon: (@Composable () -> Unit)? = null,
    content: @Composable (Modifier) -> Unit
) {
    Scaffold(
        modifier = modifier,
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

@ExperimentalMaterialApi
@Composable
fun ShowNavigationRail(
    galleries: Array<GallerySections>,
    navController: NavHostController,
    imageLiveData: MutableLiveData<Image>,
    updateRoute: (String) -> Unit,
) {
    NavigationRail {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        galleries.forEach { item ->
            NavigationRailItem(
                icon = { NavItemIcon(icon = item.icon, description = stringResource(item.title)) },
                label = { NavItemLabel(stringResource(item.title)) },
                selected = isNavItemSelected(currentDestination, item.route),
                onClick = { navItemOnClick(navController, item.route, imageLiveData, updateRoute) }
            )
        }
    }
}

@Composable
fun ShowBottomNavigation(
    galleries: Array<GallerySections>,
    navController: NavHostController,
    imageLiveData: MutableLiveData<Image>,
    updateRoute: (String) -> Unit,
) {
    BottomNavigation {
        val currentDestination = navController.currentBackStackEntryAsState().value?.destination
        galleries.forEach { item ->
            BottomNavigationItem(
                icon = { NavItemIcon(icon = item.icon, description = stringResource(item.title)) },
                label = { NavItemLabel(stringResource(item.title)) },
                selected = isNavItemSelected(currentDestination, item.route),
                onClick = { navItemOnClick(navController, item.route, imageLiveData, updateRoute) },
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
@Composable
private fun isNavItemSelected(currentDestination: NavDestination?, navItemRoute: String,): Boolean {
    return currentDestination?.hierarchy?.any { it.route == navItemRoute } == true
}

// Reference: https://developer.android.com/jetpack/compose/navigation#bottom-nav
private fun navItemOnClick(
    navController: NavController,
    navItem: String,
    imageLiveData: MutableLiveData<Image>,
    updateRoute: (String) -> Unit
) {
    // Navigate to new destination
    navController.navigate(navItem) {
        popUpTo(navController.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }

    // update current route to new destination
    updateRoute(navItem)

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
