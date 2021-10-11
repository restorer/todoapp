package data

data class AppDrawerState(
    val inboxProject: AppProject,
    val projects: List<AppProject>,
    val labels: List<AppLabel>,
    val isFavoritesOpened: Boolean = true,
    val isProjectsOpened: Boolean = true,
    val isLabelsOpened: Boolean = false,
)
