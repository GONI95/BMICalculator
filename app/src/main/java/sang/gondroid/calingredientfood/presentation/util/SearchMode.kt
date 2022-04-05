package sang.gondroid.calingredientfood.presentation.util

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import sang.gondroid.calingredientfood.R

/**
 * Gon [22.01.10] : 검색모드와 연관된 상수를 정의하기 위한 Enum class
 */
enum class SearchMode(
    @DrawableRes val modelImage: Int,
    @StringRes val modelName: Int,
    @StringRes val modelDescription: Int
) {
    FOOD(R.drawable.ic_food, R.string.food, R.string.please_food_name),
    DATE(R.drawable.ic_folder, R.string.custom_food, R.string.please_custom_food_name);
}
