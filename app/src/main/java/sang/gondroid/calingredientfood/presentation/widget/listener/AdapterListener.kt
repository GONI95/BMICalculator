package sang.gondroid.calingredientfood.presentation.widget.listener

import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel

/**
 * Gon [22.01.20] : ViewHolder, RecyclerViewAdapter는 리스트를 표시하는 작업을 하고 Data의 상태를 제어하지 않고
 *                  분리하여 구현체에서 제어할 수 있도록 EventListener 역할을 하는 Interface를 선언
 *
 *                  전체적으로 ItemView 클릭 시 호출하기위한 추상 메서드 onClickItem() 선언
 */
interface AdapterListener {
    fun onClickItem(model: FoodNtrIrdntModel)
}