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
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Checkbox
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import data.AppContentState
import data.AppTask
import util.BitmapResourcePainter
import util.LazyColumnWithScrollbar

enum class AppContentToggleState {
    None,
    Opened,
    Closed
}

data class AppContentTask(val task: AppTask, val depth: Int, val toggleState: AppContentToggleState)

@Suppress("FunctionName")
@Composable
@Preview
fun AppContentItem(contentTask: AppContentTask) {
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
        if (contentTask.toggleState == AppContentToggleState.None) {
            Spacer(Modifier.size(Res.sizes.tasksToggleSpacing.dp))
        } else {
            IconButton(onClick = {}) {
                Image(
                    painter = BitmapResourcePainter(
                        if (contentTask.toggleState == AppContentToggleState.Opened) {
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
            checked = contentTask.task.isCompleted,
            onCheckedChange = {}
        )

        Spacer(Modifier.size(Res.sizes.tasksCheckSpacing.dp))
        Text(contentTask.task.content, fontWeight = FontWeight.SemiBold)

        if (contentTask.task.description.isNotEmpty()) {
            Text(contentTask.task.description)
        }
    }
}

internal fun flattenTasks(tasks: List<AppTask>, depth: Int = 0): List<AppContentTask> {
    val result = mutableListOf<AppContentTask>()

    for (task in tasks) {
        result.add(AppContentTask(task, depth, AppContentToggleState.None))
        result.addAll(flattenTasks(task.children, depth + 1))
    }

    return result
}

@Suppress("FunctionName")
@Composable
@Preview
fun AppContent(
    contentState: AppContentState,
    onContentStateChanged: (AppContentState) -> Unit,
) {
    val contentTasks = flattenTasks(contentState.tasks)

    LazyColumnWithScrollbar(
        contentPadding = PaddingValues(vertical = Res.sizes.tasksPaddingVertical.dp)
    ) {
        items(contentTasks) {
            AppContentItem(it)
        }
    }
}
