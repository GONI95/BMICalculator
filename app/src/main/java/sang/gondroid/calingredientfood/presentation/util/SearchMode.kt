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
    FOOD(R.drawable.ic_food_bank, R.string.food, R.string.please_word),
    DATE(R.drawable.ic_calendar_month, R.string.date, R.string.please_date);
}