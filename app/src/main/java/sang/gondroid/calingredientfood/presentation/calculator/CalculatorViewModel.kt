package sang.gondroid.calingredientfood.presentation.calculator

import android.view.View
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import android.widget.AdapterView
import sang.gondroid.calingredientfood.util.DebugLog
import org.jetbrains.annotations.NotNull
import sang.gondroid.calingredientfood.domain.util.SearchMode


class CalculatorViewModel : BaseViewModel() {
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
     * Gon [22.01.11] : onEditorEnterAction() bindingAdapter 메서드에서 호출되는 메서드
     *                  매개변수 : EditText에 입력한 값
     */
    private fun search(@NotNull value: String) {
        DebugLog.v(value)
        DebugLog.v(selectSearchMode().name)
    }
}