package sang.gondroid.calingredientfood.data.util

fun String.toDoubleOrZero() : Double {
    val value = this.toDoubleOrNull()

    return value ?: 0.0
}
