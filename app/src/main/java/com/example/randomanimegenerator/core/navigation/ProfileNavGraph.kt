package com.example.randomanimegenerator.core.navigation

import android.app.Activity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.PickVisualMediaRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.randomanimegenerator.feature_profile.presentation.AuthenticationClient
import com.example.randomanimegenerator.feature_profile.presentation.ProfileEvent
import com.example.randomanimegenerator.feature_profile.presentation.ProfileScreen
import com.example.randomanimegenerator.feature_profile.presentation.ProfileState
import com.example.randomanimegenerator.feature_profile.presentation.ProfileViewModel
import com.example.randomanimegenerator.feature_profile.presentation.SignInEvent
import com.example.randomanimegenerator.feature_profile.presentation.SignInScreen
import com.example.randomanimegenerator.feature_profile.presentation.SignInState
import com.example.randomanimegenerator.feature_profile.presentation.SignInViewModel
import com.example.randomanimegenerator.feature_profile.presentation.SignUpEvent
import com.example.randomanimegenerator.feature_profile.presentation.SignUpScreen
import com.example.randomanimegenerator.feature_profile.presentation.SignUpViewModel
import kotlinx.coroutines.runBlocking

fun NavGraphBuilder.profileNavGraph(
    navController: NavHostController,
    paddingValues: PaddingValues,
    authenticationClient: AuthenticationClient
) {
    navigation(
        route = Destinations.ProfileNavGraph.route,
        startDestination = Destinations.SignIn.route
    ) {
        composable(
            route = Destinations.SignIn.route
        ) {
            val viewModel = hiltViewModel<SignInViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(SignInState())

            val snackBarHostState = remember {
                SnackbarHostState()
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.StartIntentSenderForResult(),
                onResult = { result ->
                    if (result.resultCode == Activity.RESULT_OK) {
                        runBlocking {
                            val signInResult = authenticationClient.signInWithIntent(
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
                eventFlow = viewModel.eventFlow,
                paddingValues = paddingValues,
                onEvent = viewModel::onEvent,
                snackBarHostState = snackBarHostState,
                onSignInWithGoogle = {
                    runBlocking {
                        val signInIntendSender = authenticationClient.signIn()
                        launcher.launch(
                            IntentSenderRequest.Builder(
                                signInIntendSender ?: return@runBlocking
                            ).build()
                        )
                    }
                },
                onNavigateToSignUpScreen = {
                    navController.navigate(Destinations.SignUp.route)
                }
            )
        }
        composable(
            route = Destinations.Profile.route
        ) {
            val viewModel = hiltViewModel<ProfileViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(ProfileState())

            LaunchedEffect(key1 = Unit) {
                if (authenticationClient.getSignedInUser() == null) {
                    navController.navigate(Destinations.SignIn.route)
                }
            }

            val launcher = rememberLauncherForActivityResult(
                contract = ActivityResultContracts.PickVisualMedia(),
                onResult = {
                    if (it == null) {
                        return@rememberLauncherForActivityResult
                    }
                    viewModel.onEvent(ProfileEvent.ChangeProfilePicture(it.toString()))
                }
            )

            ProfileScreen(
                paddingValues = paddingValues,
                state = state,
                onEvent = viewModel::onEvent,
                onNavigateToSignInScreen = {
                    navController.navigate(Destinations.SignIn.route)
                },
                onProfilePictureChange = {
                    launcher.launch(
                        PickVisualMediaRequest(ActivityResultContracts.PickVisualMedia.ImageOnly)
                    )
                }
            )
        }
        composable(route = Destinations.SignUp.route) {
            val viewModel = hiltViewModel<SignUpViewModel>()
            val state by viewModel.state.collectAsStateWithLifecycle(SignInState())

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
                eventFlow = viewModel.eventFlow,
                paddingValues = paddingValues,
                onEvent = viewModel::onEvent,
                snackBarHostState = snackBarHostState,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}