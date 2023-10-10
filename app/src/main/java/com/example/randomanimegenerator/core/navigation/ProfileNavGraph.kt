package com.example.randomanimegenerator.core.navigation

import android.app.Activity
import android.content.Intent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.randomanimegenerator.core.constants.navigationItems
import com.example.randomanimegenerator.feature_profile.presentation.AuthenticationClientImpl
import com.example.randomanimegenerator.feature_profile.presentation.ProfileEvent
import com.example.randomanimegenerator.feature_profile.presentation.ProfileScreen
import com.example.randomanimegenerator.feature_profile.presentation.ProfileViewModel
import com.example.randomanimegenerator.feature_profile.presentation.SignInEvent
import com.example.randomanimegenerator.feature_profile.presentation.SignInScreen
import com.example.randomanimegenerator.feature_profile.presentation.SignInViewModel
import com.example.randomanimegenerator.feature_profile.presentation.SignUpEvent
import com.example.randomanimegenerator.feature_profile.presentation.SignUpScreen
import com.example.randomanimegenerator.feature_profile.presentation.SignUpViewModel
import kotlinx.coroutines.runBlocking

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    authenticationClientImpl: AuthenticationClientImpl
) {
    navigation(
        route = Destinations.ProfileNavGraph.route,
        startDestination = Destinations.SignIn.route
    ) {
        composable(
            route = Destinations.SignIn.route
        ) {
            val viewModel = hiltViewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val snackBarHostState = remember {
                SnackbarHostState()
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        runBlocking {
                            val signInResult = authenticationClientImpl.signInWithIntent(
                                intent = result.data ?: return@runBlocking
                            )
                            viewModel.onEvent(SignInEvent.OnSignInResult(signInResult))
                        }
                    }
                }
            )

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    navController.navigate(Destinations.Profile.route)
                    viewModel.onEvent(SignInEvent.ResetState)
                }
            }

            SignInScreen(
                state = state,
                bottomNavItems = navigationItems,
                eventFlow = viewModel.eventFlow,
                onEvent = viewModel::onEvent,
                snackBarHostState = snackBarHostState,
                onSignInWithGoogle = {
                    runBlocking {
                        val signInIntendSender = authenticationClientImpl.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntendSender ?: return@runBlocking
                            ).build()
                        )
                    }
                },
                onNavigateToSignUpScreen = {
                    navController.navigate(Destinations.SignUp.route)
                },
                onNavigateToBottomNavItem = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(
            route = Destinations.Profile.route
        ) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            val context = LocalContext.current

            LaunchedEffect(key1 = Unit) {
                if (authenticationClientImpl.getSignedInUser() == null) {
                    navController.navigate(Destinations.SignIn.route)
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {
                    if (it == null) {
                        return@rememberLauncherForActivityResult
                    }
                    context.contentResolver.takePersistableUriPermission(it, Intent.FLAG_GRANT_READ_URI_PERMISSION)
                    viewModel.onEvent(ProfileEvent.ChangeProfilePicture(it.toString()))
                }
            )

            ProfileScreen(
                state = state,
                onEvent = viewModel::onEvent,
                bottomNavItems = navigationItems,
                onNavigateToSignInScreen = {
                    navController.navigate(Destinations.SignIn.route)
                },
                onProfilePictureChange = {
                    launcher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                },
                onNavigateToBottomNavItem = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
        composable(route = Destinations.SignUp.route) {
            val viewModel = hiltViewModel<SignUpViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle()

            LaunchedEffect(key1 = state.isSignInSuccessful) {
                if (state.isSignInSuccessful) {
                    navController.navigate(Destinations.Profile.route)
                    viewModel.onEvent(SignUpEvent.ResetState)
                }
            }

            val snackBarHostState = remember {
                SnackbarHostState()
            }

            SignUpScreen(
                state = state,
                bottomNavItems = navigationItems,
                eventFlow = viewModel.eventFlow,
                onEvent = viewModel::onEvent,
                snackBarHostState = snackBarHostState,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToBottomNavItem = { route ->
                    navController.navigate(route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}