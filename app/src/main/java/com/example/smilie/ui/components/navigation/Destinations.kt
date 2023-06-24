package com.example.smilie.ui.components.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountCircle
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector

// REFERENCE: https://github.com/android/codelab-android-compose/blob/main/NavigationCodelab
// SPECIFICALLY: https://github.com/android/codelab-android-compose/blob/main/NavigationCodelab/app/src/main/java/com/example/compose/rally/RallyDestinations.kt

interface NavDestination {
    val name: String
    val route: String
    val icon: ImageVector
}

object Home : NavDestination {
    override val name = "Home"
    override val route = "home"
    override val icon = Icons.Rounded.Home
}

object Profile : NavDestination {
    override val name = "Profile"
    override val route = "profile"
    override val icon = Icons.Rounded.AccountCircle
}

object Settings : NavDestination {
    override val name = "Settings"
    override val route = "settings"
    override val icon = Icons.Rounded.Settings
}

val smilieTabRowScreens = listOf(Home, Profile, Settings)