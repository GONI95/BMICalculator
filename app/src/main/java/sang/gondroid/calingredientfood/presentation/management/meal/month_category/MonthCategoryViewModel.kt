package sang.gondroid.calingredientfood.presentation.management.meal.month_category

import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.domain.use_case.GetMealNtrIrdntListForMonthUseCase
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import sang.gondroid.calingredientfood.presentation.util.MonthCategory

internal class MonthCategoryViewModel(
    private val monthCategory: MonthCategory,
    private val monthRange: Pair<String, String>,
    getMealNtrIrdntListForMonthUseCase: GetMealNtrIrdntListForMonthUseCase
) : BaseViewModel() {

    val flow = getMealNtrIrdntListForMonthUseCase
        .invoke(monthRange.first, monthRange.second)
        .cachedIn(viewModelScope)

    override fun fetchData(): Job = viewModelScope.launch {

        DebugLog.d("${monthRange.first}, ${monthRange.second}")
    }
}
