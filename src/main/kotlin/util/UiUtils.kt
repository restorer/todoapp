package util

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.LocalScrollbarStyle
import androidx.compose.foundation.VerticalScrollbar
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollbarAdapter
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.BitmapPainter
import androidx.compose.ui.res.loadImageBitmap
import androidx.compose.ui.res.useResource
import androidx.compose.ui.unit.dp

@Suppress("FunctionName", "NOTHING_TO_INLINE")
inline fun BitmapResourcePainter(resourcePath: String) = BitmapPainter(useResource(resourcePath, ::loadImageBitmap))

@Suppress("FunctionName")
@Composable
@Preview
fun LazyColumnWithScrollbar(
    contentPadding: PaddingValues = PaddingValues(0.dp),
    content: LazyListScope.() -> Unit
) {
    val listState = rememberLazyListState()
    val scrollbarAdapter = rememberScrollbarAdapter(listState)

    Box {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(end = LocalScrollbarStyle.current.thickness),
            state = listState,
            contentPadding = contentPadding,
            content = content
        )

        VerticalScrollbar(scrollbarAdapter, Modifier.align(Alignment.CenterEnd))
    }
}
