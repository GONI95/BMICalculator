package sang.gondroid.calingredientfood.presentation.base

import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel

/**
 * Gon [22.03.03] : Fragment 간 데이터 전달을 위해 Interface 정의
 */
interface FragmentListener {
    fun sendCalculatorItem(model: FoodNtrIrdntModel) : Boolean
}