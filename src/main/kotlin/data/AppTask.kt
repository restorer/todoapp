package data

data class AppTask(
    val id: Long? = null,
    val content: String,
    val description: String = "",
    val isCompleted: Boolean = false,
    val labels: List<AppLabel> = emptyList(),
    val priority: Int = 1,
    val children: List<AppTask> = emptyList(),
    val isChildrenOpened: Boolean = true,
)
