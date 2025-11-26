package com.arturo254.opentune.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ShortNavigationBarArrangement
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.arturo254.opentune.LocalPlayerAwareWindowInsets

@ExperimentalMaterial3Api
@Composable
fun SettingsPage(title: String, navController: NavController, scrollBehavior: TopAppBarScrollBehavior, modifier: Modifier = Modifier, horizontalAlignment: Alignment.Horizontal = Alignment.Start, verticalArrangement: Arrangement.Vertical = Arrangement.Top, items: @Composable () -> Unit) {
    Column(
        modifier = modifier
            .windowInsetsPadding(
                LocalPlayerAwareWindowInsets.current.only(
                    WindowInsetsSides.Horizontal + WindowInsetsSides.Bottom
                )
            )
            .verticalScroll(rememberScrollState()),
        verticalArrangement = verticalArrangement,
        horizontalAlignment = horizontalAlignment
    ){
        Spacer(
            Modifier.windowInsetsPadding(
                LocalPlayerAwareWindowInsets.current.only(WindowInsetsSides.Top)
            )
        )

        items()

        Spacer(
            Modifier.height(16.dp)
        )
    }
    SettingsTopAppBar(
        title,
        navController,
        scrollBehavior
    )
}