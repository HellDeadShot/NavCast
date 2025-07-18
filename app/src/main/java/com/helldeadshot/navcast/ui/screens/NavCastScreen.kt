package com.helldeadshot.navcast.ui.screens

import android.Manifest
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.helldeadshot.navcast.ui.components.ErrorSnackbar
import com.helldeadshot.navcast.ui.components.InputSection
import com.helldeadshot.navcast.ui.components.OSMMapSection
import com.helldeadshot.navcast.ui.components.PermissionRequestScreen

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun NavCastScreen(
    modifier: Modifier = Modifier,
    viewModel: NavCastViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val routeData by viewModel.routeData.collectAsState()
    val weatherData by viewModel.weatherData.collectAsState()

    val locationPermissions = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    Column(
        modifier = modifier.fillMaxSize()
    ) {
        // Input Section
        InputSection(
            origin = uiState.origin,
            destination = uiState.destination,
            onOriginChange = viewModel::updateOrigin,
            onDestinationChange = viewModel::updateDestination,
            onGetRoute = viewModel::getRoute,
            isLoading = uiState.isLoading
        )

        // OpenStreetMap Section
        OSMMapSection(
            routeData = routeData,
            weatherData = weatherData,
            modifier = Modifier.weight(1f)
        )

        // Error handling
        uiState.error?.let { error ->
            ErrorSnackbar(
                message = error,
                onDismiss = viewModel::clearError
            )
        }
    }
}
