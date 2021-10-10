package entity

data class AppLabel(
    val id: Long,
    val name: String,
    val colorIndex: Int,
    val isFavorite: Boolean = false
)
