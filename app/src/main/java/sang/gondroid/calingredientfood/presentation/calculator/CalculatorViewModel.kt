package sang.gondroid.calingredientfood.presentation.calculator

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import sang.gondroid.calingredientfood.presentation.util.UIState

internal class CalculatorViewModel : BaseViewModel() {

    /**
     * Gon [22.02.22] : 1. LiveData를 이용해 값이 변경되면 fragment_search.xml의 표현식을 통해 BindingAdapter 호출
     *                        UIState.Success 인 경우 BindingAdapter.submitList() 메서드가 호출됨
     */
    private val calculatorList = ArrayList<FoodNtrIrdntModel>()
    private val _calculatorUIStateLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.Init)
    val calculatorUIStateLiveData: LiveData<UIState>
        get() = _calculatorUIStateLiveData

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
}