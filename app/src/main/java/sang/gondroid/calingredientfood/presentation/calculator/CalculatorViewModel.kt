package sang.gondroid.calingredientfood.presentation.calculator

import android.view.View
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import android.widget.AdapterView
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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

    /**
     * Gon [22.01.25] : Spinner 선택된 아이템(SearchMode)을 이용해 set / get
     *                  key값을 이용해 개체를 저장/검색 가능한 Map인 SavedStateHandle을 이용해 상태(state) 관리
     */
    var currentSearchMode = state.get<SearchMode>(Constants.SEARCH_MODE_KEY) ?: SearchMode.FOOD
        private set(value) {
            state.set(Constants.SEARCH_MODE_KEY, value)
            field = value
        }

    /**
     * Gon [22.01.25] : LiveData를 이용해 값이 변경되면 BindingAdapter.submitList() 메서드가 호출됨
     *                  fragment_calculator.xml calculator_search_editText Hint 변경에 사용
     */
    val currentSearchModeLiveData: LiveData<SearchMode> = state.getLiveData(Constants.SEARCH_MODE_KEY, SearchMode.FOOD)

    /**
     * Gon [22.01.25] : 검색모드(SearchMode)를 담당하는 Spinner에서 Item 선택 시 호출
     *                  parent.getSelectedItem() : 선택한 Item
     */
    fun onSelectItem(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        currentSearchMode = parent.selectedItem as SearchMode
    }

    /**
     * Gon [22.02.04] : LiveData를 이용해 값이 변경되면 fragment_calculator.xml의 표현식을 통해 BindingAdapter.submitList() 메서드가 호출됨
     */
    val foodNtrIrdntModelListLiveData: LiveData<List<Model>> = state.getLiveData(Constants.FOOD_NTR_IRDNT_LIST_KEY)

    private fun setFoodNtrIrdntListSavedStateHandle(list: List<Model>) {
        state.set(Constants.FOOD_NTR_IRDNT_LIST_KEY, list)
    }

    /**
     * Gon [22.01.11] : onEditorEnterAction() bindingAdapter 메서드의 매개변수로 전달되는
     *                  반환값이 없는 하나의 인자를 갖는 고차함수
     */
    val searchFunc: (String) -> Unit = this::search

    /**
     * Gon [22.01.12] : onEditorEnterAction() bindingAdapter 메서드에서 호출되는 메서드
     *                  매개변수 : EditText에 입력한 값
     *
     *                  GetFoodNtrIrdntUseCase() : FoodNtrIrdntInfoService API에 매개변수에 해당하는 식품 영양성분 요청
     */
    @Suppress("UNCHECKED_CAST")
    private fun search(value: String) {
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

    private var calculatorList = listOf<Model>()

    /**
     * Gon [22.02.04] : LiveData를 이용해 값이 변경되면 fragment_calculator.xml의 표현식을 통해 BindingAdapter.submitList() 메서드가 호출됨
     */
    val calculatorModelListLiveData: LiveData<List<Model>> = state.getLiveData(Constants.CALCULATOR_LIST_KEY)

    private fun setCalculatorListSavedStateHandle(list: List<Model>) {
        state.set(Constants.CALCULATOR_LIST_KEY, list)
    }

    /**
     * Gon [22.02.04] : CalculatorFragment의 BaseRecyclerViewAdapter<FoodNtrIrdntModel> 구현부에서 호출하는
     *                  반환값이 없는 하나의 FoodNtrIrdntModel 인자를 갖는 고차함수
     */
    val addBtnClickFunc: (FoodNtrIrdntModel) -> Unit = this::addBtnClickFunc

    /**
     * Gon [22.02.04] : RecyclerView ItemView Button 클릭 시 setAdapterAndClickEvent() bindingAdapter 메서드의해 호출되는 메서드
     */
    @Suppress("UNCHECKED_CAST")
    private fun addBtnClickFunc(model: FoodNtrIrdntModel) {
        val newModel = model.copy(type = ViewType.CALCULATOR)
        calculatorList += newModel
        setCalculatorListSavedStateHandle(calculatorList as List<FoodNtrIrdntModel>)
    }
}