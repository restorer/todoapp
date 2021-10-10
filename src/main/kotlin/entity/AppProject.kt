package entity

data class AppProject(
    val id: Long,
    val colorIndex: Int,
    val name: String,
    val isShared: Boolean = false,
    val isFavorite: Boolean = false,
    val syncId: Long = 0,
    val isInbox: Boolean = false,
)
