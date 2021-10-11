package ui

import Res
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material.DrawerValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.ModalDrawer
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import data.AppContentState
import data.AppDrawerState
import data.AppLabel
import data.AppProject
import data.AppState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import util.BitmapResourcePainter
import util.mapChanged

@OptIn(ExperimentalMaterialApi::class)
@Suppress("FunctionName")
@Composable
@Preview
fun App(
    appState: AppState,
    onAppStateChanged: (AppState) -> Unit,
) {
    fun onDrawerStateChanged(drawerState: AppDrawerState) {
        onAppStateChanged(appState.copy(drawer = drawerState))
    }

    fun onContentStateChanged(contentState: AppContentState) {
        onAppStateChanged(appState.copy(content = contentState))
    }

    fun onProjectClicked(project: AppProject) {
        onAppStateChanged(appState.copy(
            drawer = appState.drawer.copy(
                projects = mapChanged(appState.drawer.projects) {
                    if (!it.isInbox && it.id == project.id) {
                        it.copy(isFavorite = !it.isFavorite)
                    } else {
                        null
                    }
                }
            )
        ))
    }

    fun onLabelClicked(label: AppLabel) {
        onAppStateChanged(appState.copy(
            drawer = appState.drawer.copy(
                labels = mapChanged(appState.drawer.labels) {
                    if (it.id == label.id) {
                        it.copy(isFavorite = !it.isFavorite)
                    } else {
                        null
                    }
                }
            )
        ))
    }

    MaterialTheme {
        BoxWithConstraints {
            val isWideEnough = maxWidth > Res.sizes.coreWideEnough.dp
            val scope = rememberCoroutineScope()
            val drawerState = rememberDrawerState(DrawerValue.Closed)
            val drawerJob = remember { mutableStateOf<Job?>(null) }

            Column {
                TopAppBar(
                    title = {
                        Text(Res.strings.coreAppTitle)
                    },
                    navigationIcon = if (isWideEnough) {
                        null
                    } else {
                        {
                            IconButton(onClick = {
                                drawerJob.value?.cancel()

                                if (drawerState.targetValue == DrawerValue.Open) {
                                    drawerJob.value = scope.launch {
                                        drawerState.close()
                                    }
                                } else {
                                    drawerJob.value = scope.launch {
                                        drawerState.open()
                                    }
                                }
                            }) {
                                Image(
                                    painter = BitmapResourcePainter(
                                        if (drawerState.targetValue == DrawerValue.Open) {
                                            Res.bitmaps.coreNavigationBack
                                        } else {
                                            Res.bitmaps.coreNavigationMenu
                                        }
                                    ),
                                    contentDescription = Res.strings.coreDrawerToggle
                                )
                            }
                        }
                    }
                )

                if (isWideEnough) {
                    Row {
                        Column(
                            Modifier.width(Res.sizes.drawerWidth.dp)
                                .background(Color(Res.colors.drawerBackground))
                        ) {
                            AppDrawer(appState.drawer, ::onDrawerStateChanged, ::onProjectClicked, ::onLabelClicked)
                        }

                        AppContent(appState.content, ::onContentStateChanged)
                    }
                } else {
                    ModalDrawer(
                        drawerContent = {
                            AppDrawer(appState.drawer, ::onDrawerStateChanged, ::onProjectClicked, ::onLabelClicked)
                        },
                        drawerState = drawerState
                    ) {
                        AppContent(appState.content, ::onContentStateChanged)
                    }
                }
            }
        }
    }
}
