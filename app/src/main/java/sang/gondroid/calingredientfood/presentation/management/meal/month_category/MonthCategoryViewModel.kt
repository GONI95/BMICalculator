package sang.gondroid.calingredientfood.presentation.management.meal.month_category

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import sang.gondroid.calingredientfood.presentation.util.MonthCategory

internal class MonthCategoryViewModel(
    private val monthCategory: MonthCategory,
) : BaseViewModel() {

    private val _monthCategoryLiveData = MutableLiveData(monthCategory)
    val monthCategoryLiveData: LiveData<MonthCategory>
        get() = _monthCategoryLiveData
}
