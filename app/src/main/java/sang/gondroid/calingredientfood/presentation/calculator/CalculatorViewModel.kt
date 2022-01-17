package sang.gondroid.calingredientfood.presentation.calculator

import android.view.View
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import android.widget.AdapterView
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import sang.gondroid.calingredientfood.data.util.TaskResult
import sang.gondroid.calingredientfood.domain.model.ItemModel
import sang.gondroid.calingredientfood.domain.use_case.GetFoodNtrIrdntUseCase
import sang.gondroid.calingredientfood.presentation.util.SearchMode
import sang.gondroid.calingredientfood.presentation.util.DebugLog

class CalculatorViewModel(
    private val getFoodNtrIrdntUseCase: GetFoodNtrIrdntUseCase
) : BaseViewModel() {
    /**
     * Gon [22.01.11] : onEditorEnterAction() bindingAdapter 메서드의 매개변수로 전달되는
     *                  반환값이 없는 하나의 인자를 갖는 고차함수
     */
    val searchFunc: (String) -> Unit = this::search

    /**
     * Gon [22.01.11] : 반환값을 가지는 인자를 갖지않는 고차함수
     *                  반환값 : Spinner에 선택된 Item과 대응되는 SearchMode
     */
    private lateinit var selectSearchMode : () -> SearchMode

    /**
     * Gon [22.01.11] : 검색모드(SearchMode)를 담당하는 Spinner에서 Item 선택 시 호출
     *                  parent.getSelectedItem() : 선택한 Item
     */
    fun onSelectItem(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        selectSearchMode = { parent.selectedItem as SearchMode }
    }

    /**
     * Gon [22.01.12] : onEditorEnterAction() bindingAdapter 메서드에서 호출되는 메서드
     *                  매개변수 : EditText에 입력한 값
     *
     *                  GetFoodNtrIrdntUseCase() : FoodNtrIrdntInfoService API에 매개변수에 해당하는 식품 영양성분 요청
     */
    private fun search(value: String) {
        when(selectSearchMode()) {
            SearchMode.FOOD -> {
                viewModelScope.launch {
                    val result = getFoodNtrIrdntUseCase.invoke(value)
                    when(result) {
                        is TaskResult.Success<*> -> {
                            (result.data as List<ItemModel>).forEach {
                                DebugLog.i(it.descriptionKOR)
                            }
                        }
                        is TaskResult.Fail -> {
                            DebugLog.d("실패")
                        }
                        is TaskResult.Exception -> {
                            DebugLog.d(result.throwable.message)
                        }
                    }
                }
            }
            SearchMode.DATE -> {

            }
        }
    }
}