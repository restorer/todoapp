import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import entity.AppLabel
import entity.AppProject
import entity.AppState

internal val COLOR_MAP = mapOf(
    30 to 0xffb8256f,
    31 to 0xffdb4035,
    32 to 0xffff9933,
    33 to 0xfffad000,
    34 to 0xffafb83b,
    35 to 0xff7ecc49,
    36 to 0xff299438,
    37 to 0xff6accbc,
    38 to 0xff158fad,
    39 to 0xff14aaf5,
    40 to 0xff96c3eb,
    41 to 0xff4073ff,
    42 to 0xff884dff,
    43 to 0xffaf38eb,
    44 to 0xffeb96eb,
    45 to 0xffe05194,
    46 to 0xffff8d85,
    47 to 0xff808080,
    48 to 0xffb8b8b8,
    49 to 0xffccac93,
)

@Suppress("FunctionName")
@Composable
@Preview
fun AppDrawerItem(
    iconResourcePath: String,
    text: String,
    iconTint: Long? = null,
    isSection: Boolean = false,
    onClick: () -> Unit = {},
) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(
                top = if (isSection) Res.sizes.drawerSectionPaddingTop.dp else 0.dp,
                bottom = if (isSection) Res.sizes.drawerSectionPaddingBottom.dp else 0.dp
            )
            .clickable(onClick = onClick)
            .heightIn(min = Res.sizes.drawerItemMinHeight.dp)
            .padding(
                horizontal = Res.sizes.drawerItemPaddingHorizontal.dp,
                vertical = Res.sizes.drawerItemPaddingVertical.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Image(
            painter = BitmapResourcePainter(iconResourcePath),
            contentDescription = null,
            colorFilter = if (iconTint == null) null else ColorFilter.tint(Color(iconTint))
        )
        Spacer(Modifier.size(Res.sizes.drawerIconSpacing.dp))
        Text(text, fontWeight = if (isSection) FontWeight.Bold else null)
    }
}

@Suppress("FunctionName")
@Composable
@Preview
fun AppDrawerItemProject(project: AppProject, onClick: () -> Unit) {
    if (project.isInbox) {
        AppDrawerItem(Res.bitmaps.drawerProjectInbox, Res.strings.drawerInbox, onClick = onClick)
    } else {
        AppDrawerItem(
            if (project.isShared) Res.bitmaps.drawerProjectShared else Res.bitmaps.drawerProjectRegular,
            project.name,
            COLOR_MAP.getOrDefault(project.colorIndex, Res.colors.drawerIconTintDefault),
            onClick = onClick
        )
    }
}

@Suppress("FunctionName")
@Composable
@Preview
fun AppDrawerItemLabel(label: AppLabel, onClick: () -> Unit) {
    AppDrawerItem(
        Res.bitmaps.drawerLabel,
        label.name,
        COLOR_MAP.getOrDefault(label.colorIndex, Res.colors.drawerIconTintDefault),
        onClick = onClick
    )
}

@Suppress("FunctionName")
@Composable
@Preview
fun AppDrawer(
    appState: AppState,
    onAppStateChanged: (AppState) -> Unit,
    onProjectClicked: (AppProject) -> Unit,
    onLabelClicked: (AppLabel) -> Unit,
) {
    LazyColumnWithScrollbar(
        contentPadding = PaddingValues(vertical = Res.sizes.drawerPaddingVertical.dp)
    ) {
        item(key = "P:${appState.inboxProject.id}") {
            AppDrawerItemProject(appState.inboxProject) {
                onProjectClicked(appState.inboxProject)
            }
        }

        val favoriteProjects = appState.projects.filter { it.isFavorite }
        val favoriteLabels = appState.labels.filter { it.isFavorite }

        if (favoriteProjects.isNotEmpty() || favoriteLabels.isNotEmpty()) {
            item(key = "S:F") {
                AppDrawerItem(
                    if (appState.isFavoritesOpened) Res.bitmaps.drawerToggleOpened else Res.bitmaps.drawerToggleClosed,
                    Res.strings.drawerFavorites,
                    isSection = true
                ) {
                    onAppStateChanged(appState.copy(isFavoritesOpened = !appState.isFavoritesOpened))
                }
            }

            if (appState.isFavoritesOpened) {
                items(favoriteProjects, key = { "FP:${it.id}" }) {
                    AppDrawerItemProject(it) {
                        onProjectClicked(it)
                    }
                }

                items(favoriteLabels, key = { "FL:${it.id}" }) {
                    AppDrawerItemLabel(it) {
                        onLabelClicked(it)
                    }
                }
            }
        }

        item(key = "S:P") {
            AppDrawerItem(
                if (appState.isProjectsOpened) Res.bitmaps.drawerToggleOpened else Res.bitmaps.drawerToggleClosed,
                Res.strings.drawerProjects,
                isSection = true
            ) {
                onAppStateChanged(appState.copy(isProjectsOpened = !appState.isProjectsOpened))
            }
        }

        if (appState.isProjectsOpened) {
            items(appState.projects, key = { "P:${it.id}" }) {
                AppDrawerItemProject(it) {
                    onProjectClicked(it)
                }
            }
        }

        item(key = "S:L") {
            AppDrawerItem(
                if (appState.isLabelsOpened) Res.bitmaps.drawerToggleOpened else Res.bitmaps.drawerToggleClosed,
                Res.strings.drawerLabels,
                isSection = true
            ) {
                onAppStateChanged(appState.copy(isLabelsOpened = !appState.isLabelsOpened))
            }
        }

        if (appState.isLabelsOpened) {
            items(appState.labels, key = { "L:${it.id}" }) {
                AppDrawerItemLabel(it) {
                    onLabelClicked(it)
                }
            }
        }
    }
}
