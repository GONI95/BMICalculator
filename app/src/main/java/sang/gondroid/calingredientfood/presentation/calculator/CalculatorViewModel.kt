package sang.gondroid.calingredientfood.presentation.calculator

import android.view.View
import sang.gondroid.calingredientfood.presentation.base.BaseViewModel
import android.widget.AdapterView
import sang.gondroid.calingredientfood.util.DebugLog


class CalculatorViewModel : BaseViewModel() {
    /**
     * Gon [22.01.10] : 검색모드(SearchMode)를 담당하는 Spinner에서 Item 선택 시 호출
     *                  parent.getCount() : Item 갯수
     *                  parent.getSelectedItem() : 선택한 Item
     */
    fun onSelectItem(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        DebugLog.v("$pos")
        DebugLog.v("${parent.adapter.getItem(pos)}")
        DebugLog.v("${parent.adapter.count}")
        DebugLog.v("${parent.selectedItem}")
    }


}