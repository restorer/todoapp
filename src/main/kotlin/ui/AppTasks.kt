package ui

import Res
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Checkbox
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import data.AppState
import util.BitmapResourcePainter
import util.LazyColumnWithScrollbar

enum class AppContentToggleState {
    None,
    Opened,
    Closed
}

@Suppress("FunctionName")
@Composable
@Preview
fun AppContentItem(childrenState: AppContentToggleState) {
    Row(
        Modifier
            .fillMaxWidth()
            .heightIn(min = Res.sizes.tasksItemMinHeight.dp)
            .padding(
                horizontal = Res.sizes.tasksItemPaddingHorizontal.dp,
                vertical = Res.sizes.tasksItemPaddingVertical.dp
            ),
        verticalAlignment = Alignment.CenterVertically
    ) {
        if (childrenState == AppContentToggleState.None) {
            Spacer(Modifier.size(Res.sizes.tasksToggleSpacing.dp))
        } else {
            IconButton(onClick = {}) {
                Image(
                    painter = BitmapResourcePainter(
                        if (childrenState == AppContentToggleState.Opened) {
                            Res.bitmaps.coreToggleOpened
                        } else {
                            Res.bitmaps.coreToggleClosed
                        }
                    ),
                    contentDescription = null,
                )
            }
        }

        Checkbox(
            checked = false,
            onCheckedChange = {}
        )
        Spacer(Modifier.size(Res.sizes.tasksCheckSpacing.dp))
        Text("Мне - попробовать взять обновлённые сертификаты о прививке в 7й поликлинике")
    }
}

@Suppress("FunctionName")
@Composable
@Preview
fun AppContent(
    appState: AppState,
    onAppStateChanged: (AppState) -> Unit,
) {
    LazyColumnWithScrollbar(
        contentPadding = PaddingValues(vertical = Res.sizes.tasksPaddingVertical.dp)
    ) {
        item {
            AppContentItem(AppContentToggleState.None)
        }

        item {
            AppContentItem(AppContentToggleState.Opened)
        }

        item {
            AppContentItem(AppContentToggleState.Closed)
        }
    }
}
