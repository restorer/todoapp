package util

inline fun <T, R> mapChangedIndexed(
    source: R,
    list: List<T>,
    itemTransformer: (Int, T) -> T?,
    changedTransformer: (List<T>) -> R
): R {
    list.forEachIndexed { index, item ->
        val transformedItem = itemTransformer(index, item)

        if (transformedItem != null) {
            return changedTransformer(list.mapIndexed { changeIndex, changeItem ->
                if (changeIndex == index) {
                    transformedItem
                } else {
                    itemTransformer(changeIndex, changeItem) ?: changeItem
                }
            })
        }
    }

    return source
}

inline fun <T> mapChangedIndexed(list: List<T>, itemTransformer: (Int, T) -> T?) =
    mapChangedIndexed(list, list, itemTransformer) { it }

inline fun <T, R> mapChanged(source: R, list: List<T>, itemTransformer: (T) -> T?, changedTransformer: (List<T>) -> R) =
    mapChangedIndexed(source, list, { _, item -> itemTransformer(item) }, changedTransformer)

inline fun <T> mapChanged(list: List<T>, itemTransformer: (T) -> T?) = mapChanged(list, list, itemTransformer) { it }
