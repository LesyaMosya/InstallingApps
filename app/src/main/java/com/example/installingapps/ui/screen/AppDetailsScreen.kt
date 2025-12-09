package com.example.installingapps.ui.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import com.example.installingapps.R
import com.example.installingapps.data.AppInfo
import com.example.installingapps.ui.state.AppsDetailsEvent
import com.example.installingapps.ui.state.AppsDetailsState
import com.example.installingapps.ui.viewmodel.DetailsAppViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppDetailsScreen(
    navController: NavController,
    viewModel: DetailsAppViewModel = hiltViewModel()
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.information_str),
                        style = MaterialTheme.typography.titleMedium
                    )
                },
                navigationIcon = {
                    Image(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        modifier = Modifier
                            .size(40.dp)
                            .clickable {
                                navController.popBackStack()
                            }
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
                AppsDetailsState.Loading -> {
                    Loading()
                }

                is AppsDetailsState.GetAppInfo -> {
                    AppInfoCard(result.data!!) { viewModel.onEvent(AppsDetailsEvent.StartApp) }
                }
            }
        }
    }
}

@Composable
fun AppInfoCard(
    appInfo: AppInfo,
    onClick: () -> Unit
) {
    Column(
        Modifier
            .fillMaxWidth()
            .padding(20.dp, 20.dp)
    ) {
        val paddingModifier = Modifier.padding(0.dp, 10.dp)
        Text(
            text = appInfo.title,
            style = MaterialTheme.typography.titleSmall,
            modifier = paddingModifier
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = appInfo.packageName,
                style = MaterialTheme.typography.bodyMedium
            )

            val versionStr = stringResource(R.string.version_str)
            Text(
                text = versionStr + appInfo.versionName.toString(),
                style = MaterialTheme.typography.bodyMedium
            )
        }

        val spacerModifier = Modifier.height(40.dp)
        Spacer(modifier = spacerModifier)

        val checksumStr = stringResource(R.string.checksum_str)
        Text(
            text = checksumStr,
            style = MaterialTheme.typography.labelMedium,
            modifier = paddingModifier
        )
        Text(
            text = appInfo.checksum,
            style = MaterialTheme.typography.bodyMedium
        )

        Spacer(modifier = spacerModifier)

        Button(
            onClick = onClick,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(
                text = stringResource(R.string.launch_str),
                style = MaterialTheme.typography.displaySmall
            )
        }
    }

}

