package sang.gondroid.calingredientfood.presentation.widget.listener

import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel

/**
 * Gon [22.02.10] : ViewHolder, RecyclerViewAdapter는 리스트를 표시하는 작업을 하고 Data의 상태를 제어하지 않고
 *                  분리하여 구현체에서 제어할 수 있도록 EventListener 역할을 하는 Interface를 선언
 *
 *                  ItemView 자체에 대한 이벤트를 제외하고 특정 ViewHolder에서만 발생하는 EventLisetener 역할을
 *                  하는 AdapterListener를 상속받는 Interface를 선언
 *
 *                  Button 클릭 시 호출하기 위한 추상 메서드 onClickRemoveButton() 선언
 */
interface CalculatorListener : AdapterListener {
    fun onClickRemoveButton(model: FoodNtrIrdntModel)
}