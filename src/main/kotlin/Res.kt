import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

class ResStrings {
    val coreAppTitle = "To Do App"
    val coreDrawerToggle = "Toggle drawer"

    val drawerInbox = "Inbox"
    val drawerFavorites = "Favorites"
    val drawerProjects = "Projects"
    val drawerLabels = "Labels"
}

class ResBitmaps {
    val coreLauncher = "core__launcher.png"
    val coreNavigationMenu = "core__navigation_menu.png"
    val coreNavigationBack = "core__navigation_back.png"

    val drawerProjectInbox = "drawer__project_inbox.png"
    val drawerProjectRegular = "drawer__project_regular.png"
    val drawerProjectShared = "drawer__project_shared.png"
    val drawerLabel = "drawer__label.png"
    val drawerToggleOpened = "drawer__toggle_opened.png"
    val drawerToggleClosed = "drawer__toggle_closed.png"
}

class ResSizes {
    val coreWindowDefaultWidth = 1280
    val coreWindowDefaultHeight = 768
    val coreWideEnough = 1000

    val drawerWidth = 400
    val drawerPaddingVertical = 8
    val drawerItemMinHeight = 48
    val drawerItemPaddingHorizontal = 12
    val drawerItemPaddingVertical = 4
    val drawerIconSpacing = 8
    val drawerSectionPaddingTop = 24 - drawerItemPaddingVertical
    val drawerSectionPaddingBottom = 8 - drawerItemPaddingVertical
}

class ResColors {
    val drawerBackground = 0xffeceff1
    val drawerIconTintDefault = 0xff808080
}

internal val LocalResStrings = staticCompositionLocalOf { ResStrings() }
internal val LocalResBitmaps = staticCompositionLocalOf { ResBitmaps() }
internal val LocalResSizes = staticCompositionLocalOf { ResSizes() }
internal val LocalResColors = staticCompositionLocalOf { ResColors() }

object Res {
    val strings: ResStrings
        @Composable
        @ReadOnlyComposable
        get() = LocalResStrings.current

    val bitmaps: ResBitmaps
        @Composable
        @ReadOnlyComposable
        get() = LocalResBitmaps.current

    val sizes: ResSizes
        @Composable
        @ReadOnlyComposable
        get() = LocalResSizes.current

    val colors: ResColors
        @Composable
        @ReadOnlyComposable
        get() = LocalResColors.current
}
