package sang.gondroid.calingredientfood.presentation.meal

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.model.MealNtrIrdntModel
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.domain.use_case.InsertMealNtrIrdntUseCase
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import sang.gondroid.calingredientfood.presentation.util.UIState
import kotlin.collections.ArrayList

internal class MealViewModel(
    private val insertMealNtrIrdntUseCase: InsertMealNtrIrdntUseCase
) : BaseViewModel() {

    /**
     * Gon [22.02.22] : 1. LiveData를 이용해 값이 변경되면 fragment_search.xml의 표현식을 통해 BindingAdapter 호출
     *                        UIState.Success 인 경우 BindingAdapter.submitList() 메서드가 호출됨
     */
    private val calculatorList = ArrayList<FoodNtrIrdntModel>()
    private val _calculatorUIStateLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.Init)
    val calculatorUIStateLiveData: LiveData<UIState>
        get() = _calculatorUIStateLiveData

    val selectCalendarDay: MutableLiveData<CalendarDay> = MutableLiveData(CalendarDay.today())
    val selectPictureUri: MutableLiveData<Uri> = MutableLiveData()

    /**
     * Gon [22.02.26] : 매개변수로 넘어온 model과 동일한 FoodNtrIrdntModel을 calculatorList에 추가
     *                  동일한 descriptionKOR을 가진 FoodNtrIrdntModel이 존재하는 경우 false를 반환
     *                  LiveData를 이용해 값이 변경되면 fragment_calculator.xml의 표현식을 통해 BindingAdapter.submitList() 메서드가 호출됨
     */
    fun addCalculatorItem(model: FoodNtrIrdntModel): Boolean {
        val newModel = model.copy(type = ViewType.CALCULATOR)

        calculatorList.forEach {
            if (it.descriptionKOR == model.descriptionKOR)
                return false
        }

        calculatorList.add(newModel)
        _calculatorUIStateLiveData.postValue(UIState.Success(calculatorList.toList()))
        return true
    }

    /**
     * Gon [22.02.10] : 매개변수로 넘어온 model과 동일한 Model을 calculatorList에서 제거
     *                  LiveData를 이용해 값이 변경되면 fragment_calculator.xml의 표현식을 통해 BindingAdapter.submitList() 메서드가 호출됨
     */
    fun removeCalculatorItem(model: Model) {
        calculatorList.remove(model)
        _calculatorUIStateLiveData.postValue(UIState.Success(calculatorList.toList()))
    }

    /**
     * Gon [22.02.26] : 매개변수로 넘어온 servingCount, position을 이용해 calculatorList의 servingCount 값을 변경
     *                  LiveData를 이용해 값이 변경되면 fragment_calculator.xml의 표현식을 통해 BindingAdapter.submitList() 메서드가 호출됨
     */
    fun countUpdateCalculatorItem(servingCount: Int, position: Int) {
        calculatorList[position] = calculatorList[position].copy(servingCount= servingCount)
        _calculatorUIStateLiveData.postValue(UIState.Success(calculatorList.toList()))
    }

    val onMenuItemClick: (Int) -> Boolean = this::onMenuItemClick
    private fun onMenuItemClick(itemId: Int) : Boolean =
        when(itemId) {
            R.id.save_menu_item -> {
                insertMeal()
                true
            }
            else -> false
        }

    private fun insertMeal() = viewModelScope.launch {
        try {
            require(calculatorList.isNotEmpty())

            val mealNtrIrdntModel = createMealNtrIrdntModel()
            insertMealNtrIrdntUseCase.invoke(mealNtrIrdntModel)

        } catch (e: IllegalArgumentException) {
            _calculatorUIStateLiveData.postValue(UIState.Failure)
        }
    }

    private fun createMealNtrIrdntModel(): MealNtrIrdntModel {
        var totalCalorie = 0.0
        var totalCarbohydrate = 0.0
        var totalProtein = 0.0
        var totalFat = 0.0
        var totalSugar = 0.0
        var totalSalt = 0.0
        var totalCholesterol = 0.0
        var totalSaturatedFattyAcid = 0.0
        var totalTransFat = 0.0

        calculatorList.forEach {
            totalCalorie += it.calorie.times(it.servingCount)
            totalCarbohydrate += it.carbohydrate.times(it.servingCount)
            totalProtein += it.protein.times(it.servingCount)
            totalFat += it.fat.times(it.servingCount)
            totalSugar += it.sugar.times(it.servingCount)
            totalSalt += it.salt.times(it.servingCount)
            totalCholesterol += it.cholesterol.times(it.servingCount)
            totalSaturatedFattyAcid += it.saturatedFattyAcid.times(it.servingCount)
            totalTransFat += it.transFat.times(it.servingCount)
        }

        return MealNtrIrdntModel(
            hashCode().toLong(),
            ViewType.MEAL_NTR_IRDNT,
            selectPictureUri.value,
            selectCalendarDay.value.toString(),
            calculatorList,
            totalCalorie,
            totalCarbohydrate,
            totalProtein,
            totalFat,
            totalSugar,
            totalSalt,
            totalCholesterol,
            totalSaturatedFattyAcid,
            totalTransFat
        )
    }
}