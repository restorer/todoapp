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
import entity.AppLabel
import entity.AppProject
import entity.AppState
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Suppress("FunctionName")
@Composable
@Preview
fun App(
    appState: AppState,
    onAppStateChanged: (AppState) -> Unit,
) {
    fun onProjectClicked(project: AppProject) {
        onAppStateChanged(appState.copy(
            projects = mapChanged(appState.projects) {
                if (!it.isInbox && it.id == project.id) {
                    it.copy(isFavorite = !it.isFavorite)
                } else {
                    null
                }
            }
        ))
    }

    fun onLabelClicked(label: AppLabel) {
        onAppStateChanged(appState.copy(
            labels = mapChanged(appState.labels) {
                if (it.id == label.id) {
                    it.copy(isFavorite = !it.isFavorite)
                } else {
                    null
                }
            }
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
                            AppDrawer(appState, onAppStateChanged, ::onProjectClicked, ::onLabelClicked)
                        }

                        AppContent()
                    }
                } else {
                    ModalDrawer(
                        drawerContent = {
                            AppDrawer(appState, onAppStateChanged, ::onProjectClicked, ::onLabelClicked)
                        },
                        drawerState = drawerState
                    ) {
                        AppContent()
                    }
                }
            }
        }
    }
}
