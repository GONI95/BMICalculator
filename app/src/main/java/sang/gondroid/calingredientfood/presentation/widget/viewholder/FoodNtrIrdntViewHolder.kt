package sang.gondroid.calingredientfood.presentation.widget.viewholder

import sang.gondroid.calingredientfood.databinding.LayoutFoodNtrIrdntItemBinding
import sang.gondroid.calingredientfood.domain.model.FoodNtrIrdntModel
import sang.gondroid.calingredientfood.presentation.widget.listener.AdapterListener
import sang.gondroid.calingredientfood.presentation.widget.listener.FoodNtrIrdntListener

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
