package sang.gondroid.calingredientfood.presentation.calculator

import android.view.View
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.data.util.TaskResult
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.domain.use_case.GetFoodNtrIrdntUseCase
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.util.Constants
import sang.gondroid.calingredientfood.presentation.util.SearchMode
import sang.gondroid.calingredientfood.presentation.util.DebugLog

class CalculatorViewModel(
    private val state: SavedStateHandle,
    private val getFoodNtrIrdntUseCase: GetFoodNtrIrdntUseCase
) : BaseViewModel() {

    private val calculatorList = ArrayList<Model>()

    /**
     * Gon [22.01.25] : 1. Spinner 선택된 아이템(SearchMode)을 이용해 set / get
     *                  key값을 이용해 개체를 저장/검색 가능한 Map인 SavedStateHandle을 이용해 상태(state) 관리
     *
     * Gon [22.01.25] : 2. LiveData를 이용해 값이 변경되면 BindingAdapter.submitList() 메서드가 호출됨
     *                  fragment_calculator.xml calculator_search_editText Hint 변경에 사용
     *
     * Gon [22.02.04] : 3, 4. LiveData를 이용해 값이 변경되면 fragment_calculator.xml의 표현식을 통해 BindingAdapter.submitList() 메서드가 호출됨
     */
    private var currentSearchMode = state.get<SearchMode>(Constants.SEARCH_MODE_KEY) ?: SearchMode.FOOD
        private set(value) {
            state.set(Constants.SEARCH_MODE_KEY, value)
            field = value
        }
    val currentSearchModeLiveData: LiveData<SearchMode> = state.getLiveData(Constants.SEARCH_MODE_KEY, SearchMode.FOOD)
    val foodNtrIrdntModelListLiveData: LiveData<List<Model>> = state.getLiveData(Constants.FOOD_NTR_IRDNT_LIST_KEY)
    val calculatorModelListLiveData: LiveData<List<Model>> = state.getLiveData(Constants.CALCULATOR_LIST_KEY)

    private fun setFoodNtrIrdntListSavedStateHandle(list: List<Model>) {
        state.set(Constants.FOOD_NTR_IRDNT_LIST_KEY, list)
    }

    private fun setCalculatorListSavedStateHandle(list: List<Model>) {
        state.set(Constants.CALCULATOR_LIST_KEY, list)
    }

    /**
     * Gon [22.01.25] : 검색모드(SearchMode)를 담당하는 Spinner에서 Item 선택 시 호출
     *                  parent.getSelectedItem() : 선택한 Item
     */
    fun onSelectItem(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        currentSearchMode = parent.selectedItem as SearchMode
    }

    /**
     * Gon [22.01.11] : onEditorEnterAction() bindingAdapter 메서드의 매개변수로 전달되는
     *                  반환값이 없는 하나의 인자를 갖는 고차함수
     */
    val searchFunc: (String) -> Unit = this::searchFunc

    /**
     * Gon [22.01.12] : onEditorEnterAction() bindingAdapter 메서드에서 호출되는 메서드
     *                  매개변수 : EditText에 입력한 값
     *
     *                  GetFoodNtrIrdntUseCase() : FoodNtrIrdntInfoService API에 매개변수에 해당하는 식품 영양성분 요청
     */
    @Suppress("UNCHECKED_CAST")
    private fun searchFunc(value: String) {
        when(currentSearchMode) {
            SearchMode.FOOD -> {
                viewModelScope.launch {
                    when(val result = getFoodNtrIrdntUseCase.invoke(value)) {
                        is TaskResult.Success<*> -> {
                            setFoodNtrIrdntListSavedStateHandle(result.data as List<FoodNtrIrdntModel>)
                        }

                        is TaskResult.Fail ->
                            DebugLog.d("실패")

                        is TaskResult.Exception ->
                            DebugLog.d(result.throwable.message)
                    }
                }
            }
            SearchMode.DATE -> {

            }
        }
    }

    /**
     * Gon [22.02.04] : 매개변수로 넘어온 model과 동일한 Model을 calculatorList에 추가
     *                  LiveData를 이용해 값이 변경되면 fragment_calculator.xml의 표현식을 통해 BindingAdapter.submitList() 메서드가 호출됨
     */
    fun addCalculatorItem(model: FoodNtrIrdntModel) {
        val newModel = model.copy(type = ViewType.CALCULATOR)
        calculatorList.add(newModel)
        setCalculatorListSavedStateHandle(calculatorList.toList())
    }

    /**
     * Gon [22.02.10] : 매개변수로 넘어온 model과 동일한 Model을 calculatorList에서 제거
     *                  LiveData를 이용해 값이 변경되면 fragment_calculator.xml의 표현식을 통해 BindingAdapter.submitList() 메서드가 호출됨
     */
    fun removeCalculatorItem(model: Model) {
        calculatorList.remove(model)
        setCalculatorListSavedStateHandle(calculatorList.toList())
    }
}