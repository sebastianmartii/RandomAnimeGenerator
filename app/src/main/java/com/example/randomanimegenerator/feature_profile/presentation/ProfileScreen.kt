package com.example.randomanimegenerator.feature_profile.presentation

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.surfaceColorAtElevation
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import coil.transform.CircleCropTransformation
import com.example.randomanimegenerator.R
import com.example.randomanimegenerator.core.navigation.BottomNavItem
import com.example.randomanimegenerator.core.navigation.BottomNavigationBar
import com.example.randomanimegenerator.feature_generator.presentation.Type
import com.example.randomanimegenerator.feature_generator.presentation.toTypeString

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(
    state: ProfileState,
    bottomNavItems: List<BottomNavItem>,
    onEvent: (ProfileEvent) -> Unit,
    onNavigateToSignInScreen: () -> Unit,
    onNavigateToBottomNavItem: (String) -> Unit,
    onProfilePictureChange: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(id = R.string.profile_text),
                        style = MaterialTheme.typography.titleLarge
                    )
                },
                actions = {
                    IconButton(onClick = { onEvent(ProfileEvent.OpenSignOutDialog) }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Default.Logout,
                            contentDescription = stringResource(id = R.string.sign_out_text)
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
                )
            )
        },
        bottomBar = {
            BottomNavigationBar(
                items = bottomNavItems,
                selectedItemIndex = 3,
                onItemClick = onNavigateToBottomNavItem
            )
        }
    ) { values ->
        Column(
            modifier = Modifier
                .padding(values)
                .verticalScroll(rememberScrollState())
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            AsyncImage(
                model = ImageRequest.Builder(LocalContext.current)
                    .data(state.profilePictureUrl)
                    .transformations(CircleCropTransformation())
                    .build(),
                contentDescription = stringResource(id = R.string.profile_picture_text),
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .padding(vertical = 8.dp)
                    .size(180.dp)
                    .clickable {
                        onProfilePictureChange()
                    }
            )
            Text(
                text = state.userName.ifBlank { stringResource(id = R.string.user_name_text) },
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.clickable {
                    onEvent(ProfileEvent.OpenChangeUserNameDialog)
                }
            )
            StatsSection(
                entriesCount = state.animeEntriesCount,
                type = Type.ANIME
            )
            StatsSection(
                entriesCount = state.mangaEntriesCount,
                type = Type.MANGA
            )
        }
        SignOutDialog(
            openDialog = state.openSignOutDialog,
            closeDialog = {
                onEvent(ProfileEvent.CloseSignOutDialog)
            },
            onSignOut = {
                onEvent(ProfileEvent.SignOut)
                onNavigateToSignInScreen()
            }
        )
        ChangeUserNameDialog(
            openDialog = state.openChangeUserNameDialog,
            closeDialog = {
                onEvent(ProfileEvent.CloseChangeUserNameDialog)
            },
            changeUserName = { newUserName ->
                onEvent(ProfileEvent.ChangeUserName(newUserName))
            }
        )
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun StatsSection(
    entriesCount: List<EntriesCount>,
    type: Type,
    modifier: Modifier = Modifier
) {
    Spacer(modifier = Modifier.height(12.dp))
    Box(modifier = Modifier.fillMaxWidth()) {
        Text(
            text = type.toTypeString(),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier
                .align(Alignment.BottomStart)
                .padding(start = 24.dp)
        )
    }
    Surface(
        modifier = modifier
            .padding(horizontal = 16.dp, vertical = 4.dp)
            .fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        color = MaterialTheme.colorScheme.surfaceColorAtElevation(4.dp)
    ) {
        FlowRow(
            maxItemsInEachRow = 3,
            modifier = Modifier.padding(2.dp)
        ) {
            entriesCount.onEach { entry ->
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Surface(
                        shape = MaterialTheme.shapes.small,
                        color = if (type == Type.ANIME) MaterialTheme.colorScheme.tertiary else MaterialTheme.colorScheme.secondary,
                        contentColor = if (type == Type.ANIME) MaterialTheme.colorScheme.onTertiary else MaterialTheme.colorScheme.onSecondary,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(vertical = 8.dp)
                    ) {
                        Text(
                            text = entry.status.name,
                            style = MaterialTheme.typography.titleSmall.copy(fontWeight = FontWeight.SemiBold, fontSize = 12.sp),
                            textAlign = TextAlign.Center,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 8.dp, vertical = 4.dp)
                        )
                    }
                    Text(text = "${entry.count}")
                }
            }
        }
    }
}