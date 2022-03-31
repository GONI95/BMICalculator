package sang.gondroid.calingredientfood.presentation.util

import android.view.LayoutInflater
import android.view.ViewGroup
import sang.gondroid.calingredientfood.databinding.LayoutCalculatorItemBinding
import sang.gondroid.calingredientfood.databinding.LayoutFoodNtrIrdntItemBinding
import sang.gondroid.calingredientfood.domain.model.Model
import sang.gondroid.calingredientfood.domain.util.ViewType
import sang.gondroid.calingredientfood.presentation.widget.viewholder.BaseViewHolder
import sang.gondroid.calingredientfood.presentation.widget.viewholder.CalculatorViewHolder
import sang.gondroid.calingredientfood.presentation.widget.viewholder.FoodNtrIrdntViewHolder

object BaseViewHolderMapper {

    /**
     * Gon [22.01.20] : Domain Model이 가지는 type의 값에 따라 View 개체를 생성하여 ViewHolder에 저장하고 ViewHolder 객체를 반환
     */
    @Suppress("UNCHECKED_CAST")
    fun <T : Model> viewHolderMapper(parent: ViewGroup, viewType: ViewType): BaseViewHolder<T> {
        val inflater = LayoutInflater.from(parent.context)
        return when(viewType) {
            ViewType.FOOD_NTR_IRDNT -> {
                FoodNtrIrdntViewHolder(
                    LayoutFoodNtrIrdntItemBinding.inflate(inflater, parent, false)
                )
            }
            ViewType.CALCULATOR, ViewType.MEAL_NTR_IRDNT -> {
                CalculatorViewHolder(
                    LayoutCalculatorItemBinding.inflate(inflater, parent, false)
                )
            }
        } as BaseViewHolder<T>
    }
}