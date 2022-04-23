package sang.gondroid.calingredientfood.presentation.management.meal.month_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import sang.gondroid.calingredientfood.presentation.util.MonthCategory

internal class MonthCategoryViewModel(
    private val monthCategory: MonthCategory,
    private val monthRange: Pair<String, String>
) : BaseViewModel() {

    private val _monthCategoryLiveData = MutableLiveData(monthCategory)
    val monthCategoryLiveData: LiveData<MonthCategory>
        get() = _monthCategoryLiveData

    override fun fetchData(): Job = viewModelScope.launch {

        DebugLog.d("${monthRange.first}, ${monthRange.second}")
    }
}
