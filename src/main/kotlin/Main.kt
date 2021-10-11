import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import androidx.compose.ui.window.rememberWindowState
import data.AppDrawerState
import data.AppLabel
import data.AppProject
import data.AppState
import util.BitmapResourcePainter

// https://developer.todoist.com/guides/#get-in-touch

fun main() {
    return application {
        val appState = remember {
            mutableStateOf(
                AppState(
                    drawer = AppDrawerState(
                        inboxProject = AppProject(
                            id = 1515754658,
                            colorIndex = 48,
                            name = "Inbox",
                            isInbox = true,
                        ),
                        projects = listOf(
                            AppProject(
                                id = 1523301271,
                                colorIndex = 32,
                                name = "Покупки",
                                isShared = true,
                                syncId = 1615425,
                            ),
                            AppProject(
                                id = 2256322151,
                                colorIndex = 32,
                                name = "Покупки 2",
                                isShared = true,
                                syncId = 8305531,
                            ),
                            AppProject(
                                id = 2177043830,
                                colorIndex = 48,
                                name = "Евроопт",
                                isShared = true,
                                syncId = 3336999,
                            ),
                            AppProject(
                                id = 1523293150,
                                colorIndex = 39,
                                name = "1 - Срочные и важные",
                            ),
                            AppProject(
                                id = 1523293154,
                                colorIndex = 39,
                                name = "2 - Несрочные и важные",
                            ),
                            AppProject(
                                id = 1523293157,
                                colorIndex = 39,
                                name = "3 - Несрочные и неважные",
                            ),
                            AppProject(
                                id = 1530477124,
                                colorIndex = 39,
                                name = "Регулярные",
                            ),
                            AppProject(
                                id = 1523293040,
                                colorIndex = 36,
                                name = "Сфера / Работа",
                            ),
                            AppProject(
                                id = 1523293051,
                                colorIndex = 36,
                                name = "Сфера / Семья",
                            ),
                            AppProject(
                                id = 1523293093,
                                colorIndex = 36,
                                name = "Сфера / Дом",
                            ),
                            AppProject(
                                id = 1523293104,
                                colorIndex = 36,
                                name = "Сфера / Здоровье",
                            ),
                            AppProject(
                                id = 1523293105,
                                colorIndex = 36,
                                name = "Сфера / Развитие",
                            ),
                            AppProject(
                                id = 1523293818,
                                colorIndex = 36,
                                name = "Сфера / Разное",
                            ),
                            AppProject(
                                id = 2180668542,
                                colorIndex = 48,
                                name = "Черный список",
                                isShared = true,
                                syncId = 3443970,
                            ),
                            AppProject(
                                id = 2209270912,
                                colorIndex = 33,
                                name = "Дача",
                                isShared = true,
                                syncId = 6273908,
                            ),
                            AppProject(
                                id = 2243553744,
                                colorIndex = 47,
                                name = "Apps",
                            )
                        ),
                        labels = listOf(
                            AppLabel(
                                id = 1342645783,
                                name = "разное",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645785,
                                name = "здоровье",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645786,
                                name = "развитие",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645787,
                                name = "дом",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645789,
                                name = "семья",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645790,
                                name = "работа",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645794,
                                name = "жкх",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645795,
                                name = "loyalty",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645818,
                                name = "регулярные",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645821,
                                name = "bearshop",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645824,
                                name = "scop",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645834,
                                name = "bpc",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342645835,
                                name = "taxi",
                                colorIndex = 7,
                            ),
                            AppLabel(
                                id = 1342656428,
                                name = "logic",
                                colorIndex = 7,
                            )
                        )
                    )
                )
            )
        }

        val windowState = rememberWindowState(
            size = DpSize(
                width = Res.sizes.coreWindowDefaultWidth.dp,
                height = Res.sizes.coreWindowDefaultHeight.dp
            )
        )

        Window(
            onCloseRequest = ::exitApplication,
            state = windowState,
            title = Res.strings.coreAppTitle,
            icon = BitmapResourcePainter(Res.bitmaps.coreLauncher)
        ) {
            App(
                appState = appState.value,
                onAppStateChanged = { appState.value = it }
            )
        }
    }
}
