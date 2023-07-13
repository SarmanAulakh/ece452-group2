package com.example.smilie

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.smilie.model.User
import com.example.smilie.screens.HomeScreen
import com.example.smilie.screens.MainViewModel
import com.example.smilie.screens.RateYourDay
import com.example.smilie.screens.login.LoginScreen
import com.example.smilie.screens.profile.ProfileScreen
import com.example.smilie.screens.settings.SettingsScreen
import com.example.smilie.screens.sign_up.SignUpScreen
import com.example.smilie.screens.sign_up.UserRegisterScreen
import com.example.smilie.ui.navigation.BottomNavBar
import com.example.smilie.ui.navigation.Home
import com.example.smilie.ui.navigation.LOGIN_SCREEN
import com.example.smilie.ui.navigation.Profile
import com.example.smilie.ui.navigation.RateYourDay
import com.example.smilie.ui.navigation.SIGN_UP_SCREEN
import com.example.smilie.ui.navigation.Settings
import com.example.smilie.ui.navigation.USER_REGISTER_SCREEN
import com.example.smilie.ui.navigation.smilieTabRowScreens
import com.example.smilie.ui.theme.SMILIETheme
import com.google.firebase.auth.FirebaseAuth

// REFERENCE: https://github.com/android/codelab-android-compose/blob/main/NavigationCodelab
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainApp(
    viewModel: MainViewModel = hiltViewModel()
) {
    val isUserSignedIn = FirebaseAuth.getInstance().currentUser != null
    val startingRoute = if (isUserSignedIn) Home.route else LOGIN_SCREEN

    SMILIETheme {
        val navController = rememberNavController()
        val currentBackStack by navController.currentBackStackEntryAsState()
        val currentDestination = currentBackStack?.destination
        val currentScreen = smilieTabRowScreens.find { it.route == currentDestination?.route } ?: Home

        Scaffold(
            bottomBar = {
                BottomNavBar(
                    allScreens = smilieTabRowScreens,
                    onTabSelected = { newScreen ->
                        navController.navigateSingleTopTo(newScreen.route)
                    },
                    currentScreen = currentScreen
                )
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = startingRoute,
                modifier = Modifier.padding(innerPadding),
            ) {
                composable(route = Home.route) {
                    HomeScreen()
                }
                composable(route = Profile.route) {
                    var userData = viewModel.userData
                    ProfileScreen(user=userData)
                }
                composable(route = Settings.route) {
                    SettingsScreen(openAndPopUp = { route -> navController.navigateSingleTopTo(route) })
                }
                composable(LOGIN_SCREEN) {
                    LoginScreen(
                        openAndPopUp = {
                            navController.navigateSingleTopTo(it)
                            viewModel.getUserDetails() },
                    )
                }
                composable(SIGN_UP_SCREEN) {
                    SignUpScreen(openAndPopUp = { route -> navController.navigateSingleTopTo(route) })
                }
                composable(USER_REGISTER_SCREEN) {
                    UserRegisterScreen(
                        openAndPopUp = { route -> navController.navigateSingleTopTo(route) },
                    )
                }
                composable(route = RateYourDay.route) {
                    RateYourDay()
                }
            }
        }
    }
}

// REFERENCE: https://github.com/android/codelab-android-compose/blob/main/NavigationCodelab
fun NavHostController.navigateSingleTopTo(route: String) =
    this.navigate(route) {
        popUpTo(this@navigateSingleTopTo.graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }