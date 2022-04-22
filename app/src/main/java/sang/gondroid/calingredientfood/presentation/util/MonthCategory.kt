package sang.gondroid.calingredientfood.presentation.util

import androidx.annotation.StringRes
import sang.gondroid.calingredientfood.R

enum class MonthCategory(
    @StringRes val stringId: Int
) {
    ALL(R.string.all),
    JANUARY(R.string.january),
    FEBRUARY(R.string.february),
    MARCH(R.string.march),
    APRIL(R.string.april),
    MAY(R.string.may),
    JUNE(R.string.june),
    JULY(R.string.july),
    AUGUST(R.string.august),
    SEPTEMBER(R.string.september),
    OCTOBER(R.string.october),
    NOVEMBER(R.string.november),
    DECEMBER(R.string.december)
}
