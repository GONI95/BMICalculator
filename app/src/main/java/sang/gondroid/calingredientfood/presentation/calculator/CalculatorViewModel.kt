package sang.gondroid.calingredientfood.presentation.calculator

import android.view.View
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import android.widget.AdapterView
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import org.jetbrains.annotations.NotNull
import sang.gondroid.calingredientfood.domain.use_case.GetFoodNtrIrdntUseCase
import sang.gondroid.calingredientfood.presentation.util.SearchMode
import sang.gondroid.calingredientfood.presentation.util.DebugLog
import java.lang.Exception

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
    private fun search(@NotNull value: String) {
        when(selectSearchMode()) {
            SearchMode.FOOD -> {
                viewModelScope.launch {
                    try {
                        getFoodNtrIrdntUseCase.invoke(value).also {
                            if (it.isSuccessful)
                                DebugLog.d("반환값: ${it.body()}")
                            else {
                                DebugLog.d("반환값: ${it.code()}")
                                DebugLog.d("반환값: ${it.message()}")
                                DebugLog.d("반환값: ${it.errorBody()?.string()}")
                            }
                        }
                    } catch (e : Exception) {
                        DebugLog.d("반환값: ${e.message}")
                        DebugLog.d("반환값: ${e.cause}")
                    }
                }
            }
            SearchMode.DATE -> {

            }
        }
    }
}