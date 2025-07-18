package com.helldeadshot.navcast.ui.screens

data class NavCastUiState(
    val isLoading: Boolean = false,
    val error: String? = null,
    val origin: String = "",
    val destination: String = ""
)
