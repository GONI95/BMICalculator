package sang.gondroid.calingredientfood.presentation.widget.viewholder

import sang.gondroid.calingredientfood.databinding.LayoutFoodNtrIrdntItemBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.widget.listener.AdapterListener
import sang.gondroid.calingredientfood.presentation.widget.listener.FoodNtrIrdntListener

/**
 * Gon [22.01.20] : RecyclerView의 Adapter에 설정되지 않은 경우 Adapter를 설정
 *                  Listener를 통해 Click 메서드가 호출되면 CalculatorViewModel의 고차함수를 호출
 */
class FoodNtrIrdntViewHolder(
    private val binding: LayoutFoodNtrIrdntItemBinding
) : BaseViewHolder<FoodNtrIrdntModel>(binding) {

    override fun bindData(model: FoodNtrIrdntModel) {
        binding.item = model
    }

    override fun bindViews(model: FoodNtrIrdntModel, adapterListener: AdapterListener) {
        if (adapterListener is FoodNtrIrdntListener) {
            binding.root.setOnClickListener {
                adapterListener.onClickItem(model)
            }
            binding.foodNtrIrdntAddButton.setOnClickListener {
                adapterListener.onClickAddButton(model)
            }
        }
    }
}
