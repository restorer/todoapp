package data

data class AppContentState(
    val tasks: List<AppTask>,
    val project: AppProject? = null,
    val label: AppLabel? = null,
)
