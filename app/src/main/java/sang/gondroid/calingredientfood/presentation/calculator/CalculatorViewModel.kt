package sang.gondroid.calingredientfood.presentation.calculator

import android.view.View
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.R
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.domain.use_case.GetFoodNtrIrdntListUseCase
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import sang.gondroid.calingredientfood.presentation.util.SearchMode
import sang.gondroid.calingredientfood.presentation.util.UIState

internal class CalculatorViewModel(
    private val getFoodNtrIrdntUseCase: GetFoodNtrIrdntListUseCase
) : BaseViewModel() {

    /**
     * Gon [22.02.22] : 1. Spinner 선택된 아이템(SearchMode)을 이용해 관리되는 LiveData
     *                     fragment_calculator.xml calculator_search_editText Hint 변경에 사용
     *
     * Gon [22.02.22] : 2, 3. LiveData를 이용해 값이 변경되면 fragment_calculator.xml의 표현식을 통해 BindingAdapter 호출
     *                        UIState.Success 인 경우 BindingAdapter.submitList() 메서드가 호출됨
     */
    private val _currentSearchModeLiveData: MutableLiveData<SearchMode> = MutableLiveData(SearchMode.FOOD)
    val currentSearchModeLiveData: LiveData<SearchMode>
        get() = _currentSearchModeLiveData

    private val _foodNtrIrdnrUIStateLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.Init)
    val foodNtrIrdnrUIStateLiveData: LiveData<UIState>
        get() = _foodNtrIrdnrUIStateLiveData

    private val calculatorList = ArrayList<FoodNtrIrdntModel>()
    private val _calculatorUIStateLiveData: MutableLiveData<UIState> = MutableLiveData(UIState.Init)
    val calculatorUIStateLiveData: LiveData<UIState>
        get() = _calculatorUIStateLiveData

    /**
     * Gon [22.01.25] : 검색모드(SearchMode)를 담당하는 Spinner에서 Item 선택 시 호출
     *                  parent.getSelectedItem() : 선택한 Item
     */
    fun onSelectItem(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        _currentSearchModeLiveData.value = parent.selectedItem as SearchMode
    }

    /**
     * Gon [22.01.11] : onEditorEnterAction() bindingAdapter 메서드의 매개변수로 전달되는
     *                  반환값이 없는 하나의 인자를 갖는 고차함수
     */
    val searchFunc: (String) -> Unit = this::searchFunc

    /**
     * Gon [22.02.22] : onEditorEnterAction() bindingAdapter 메서드에서 호출되는 메서드
     *                  currentSearchModeLiveData 값에 따라 검색 메서드를 호출
     */
    private fun searchFunc(value: String) {
        val currentSearchMode = currentSearchModeLiveData.value

        if (currentSearchMode == SearchMode.FOOD) {
            searchFood(value)
        } else {
            searchDate(value)
        }
    }

    /**
     * Gon [22.02.22] : GetFoodNtrIrdntUseCase로 부터 반환받은 결과를 통해 UIState 업데이트
     *                  매개변수 : EditText에 입력한 값
     *
     *                  GetFoodNtrIrdntUseCase : FoodNtrIrdntInfoService API에 매개변수에 해당하는 식품 영양성분 요청
     */
    private fun searchFood(value : String) = viewModelScope.launch {
        _foodNtrIrdnrUIStateLiveData.postValue(UIState.Loading)

        val uiState = getFoodNtrIrdntUseCase.invoke(value)?.let {
            if (it.isNotEmpty()) {
                UIState.Success(it)
            } else {
                UIState.Empty(R.string.ui_state_empty, value)
            }
        } ?: UIState.Error(R.string.ui_state_exception)

        _foodNtrIrdnrUIStateLiveData.postValue(uiState)
    }

    private fun searchDate(value : String) = viewModelScope.launch {

    }

    /**
     * Gon [22.02.04] : 매개변수로 넘어온 model과 동일한 Model을 calculatorList에 추가
     *                  LiveData를 이용해 값이 변경되면 fragment_calculator.xml의 표현식을 통해 BindingAdapter.submitList() 메서드가 호출됨
     */
    fun addCalculatorItem(model: FoodNtrIrdntModel) {
        val newModel = model.copy(type = ViewType.CALCULATOR)
        calculatorList.add(newModel)
        _calculatorUIStateLiveData.postValue(UIState.Success(calculatorList.toList()))
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