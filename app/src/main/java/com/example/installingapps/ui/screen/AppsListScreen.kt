package com.example.installingapps.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.installingapps.R
import com.example.installingapps.data.AppInfo
import com.example.installingapps.ui.state.AppsListState
import com.example.installingapps.ui.viewmodel.AppsListViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppsListScreen(
    navController: NavController,
    viewModel: AppsListViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.installed_str),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                colors = TopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary,
                    scrolledContainerColor = MaterialTheme.colorScheme.primary,
                    navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    titleContentColor = MaterialTheme.colorScheme.onPrimary,
                    actionIconContentColor = MaterialTheme.colorScheme.onPrimary,
                    subtitleContentColor = MaterialTheme.colorScheme.onPrimary
                )
            )
        }

    ) { innerPadding ->
        Surface(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize()
        ) {
            when (val result = state) {
                AppsListState.Loading -> {
                    Loading()
                }

                is AppsListState.GetListAppInfo -> {
                    AppsList(result.data, navController)
                }
            }
        }
    }

}

@Composable
fun AppsList(
    list: List<AppInfo>,
    navController: NavController
) {

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
    ) {
        items(items = list, key = { it.uid }) { app ->
            AppCard(app, navController)
        }
    }
}

val reusableItemModifier = Modifier
    .fillMaxSize()
    .height(80.dp)
    .padding(10.dp, 5.dp)

@Composable
fun AppCard(
    app: AppInfo,
    navController: NavController,
) {
    Row(
        modifier =
            reusableItemModifier
                .clickable {
                    navController.navigate("details/${app.packageName}")
                },
        verticalAlignment = Alignment.CenterVertically
    ) {

        val iconModifier = Modifier
            .weight(1f)
            .fillMaxSize()

        if (app.icon != null) {
            Icon(
                bitmap = app.icon.asImageBitmap(),
                contentDescription = "App icon",
                modifier = iconModifier
            )
        } else {
            Image(
                imageVector = Icons.Default.Clear,
                contentDescription = "App icon",
                modifier = iconModifier
            )
        }


        Text(
            modifier = Modifier
                .padding(10.dp, 0.dp)
                .weight(4f),
            text = app.title,
            style = MaterialTheme.typography.bodyLarge
        )

    }

}

